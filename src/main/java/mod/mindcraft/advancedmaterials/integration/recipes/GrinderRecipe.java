package mod.mindcraft.advancedmaterials.integration.recipes;

import java.util.ArrayList;
import java.util.List;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.integration.IMachineRecipe;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.ImmutableList;

public class GrinderRecipe implements IMachineRecipe {
	
	private Object input;
	private ArrayList<Object> outputs;
	/**
	 * 
	 * @param input : can be : {@link String}, {@link Item}, {@link ItemStack}, {@link Block}
	 * @param outputs : up to 2 elements, can be : {@link String}, {@link Item}, {@link ItemStack}, {@link Block}
	 */
	public GrinderRecipe(Object input, Object... outputs) {
		if (input == null) {
			AdvancedMaterials.getLogger().severe("A recipe has a null input, you can thank checks. This recipe wont be added BTW");
			throw new NullPointerException("Just stopping registration");
		}
		if (outputs.length > 2) {
			AdvancedMaterials.getLogger().severe("Please just read : max 2 outputs... (" + input.toString() + ")");
			throw new NullPointerException("Just stopping registration");
		}
		this.outputs = new ArrayList<Object>();
		if (input instanceof Item)
			this.input = new ItemStack((Item)input);
		else if (input instanceof Block)
			this.input = new ItemStack((Block)input);
		else if (input instanceof ItemStack)
			this.input = (ItemStack)input;
		else if (input instanceof String){
			int stackSize;
			String out = (String) input;
			if (out.split("\\*").length == 2) {
				stackSize = Integer.valueOf(out.split("\\*")[1]);
				List<ItemStack> ores = OreDictionary.getOres(out.split("\\*")[0]);
				ArrayList<Object> newStacks = new ArrayList<Object>();
				ores.forEach(t->{
					ItemStack is = t.copy();
					is.stackSize = stackSize;
					newStacks.add(is);
				});
				this.input = newStacks;
			} else {
				stackSize = 1;
				List<ItemStack> ores = OreDictionary.getOres(out);
				ArrayList<Object> newStacks = new ArrayList<Object>();
				ores.forEach(t->{
					ItemStack is = t.copy();
					is.stackSize = stackSize;
					newStacks.add(is);
				});
				this.input = newStacks;
			}
			
		}
		else {
			AdvancedMaterials.getLogger().severe("Unhandled Type for " + input.toString());
			throw new NullPointerException("Just stopping registration");
		}
		
		for (Object output : outputs) {
			if (output instanceof Item)
				this.outputs.add(new ItemStack((Item)output));
			else if (output instanceof Block)
				this.outputs.add(new ItemStack((Block)output));
			else if (output instanceof ItemStack)
				this.outputs.add((ItemStack)output);
			else if (output instanceof String) {
				int stackSize;
				String out = (String) output;
				if (out.split("\\*").length == 2) {
					stackSize = Integer.valueOf(out.split("\\*")[1]);
					List<ItemStack> ores = OreDictionary.getOres(out.split("\\*")[0]);
					ArrayList<ItemStack> newStacks = new ArrayList<ItemStack>();
					ores.forEach(t->{
						ItemStack is = t.copy();
						is.stackSize = stackSize;
						newStacks.add(is);
					});
					this.outputs.add(newStacks);
				} else {
					stackSize = 1;
					List<ItemStack> ores = OreDictionary.getOres(out);
					ArrayList<ItemStack> newStacks = new ArrayList<ItemStack>();
					ores.forEach(t->{
						ItemStack is = t.copy();
						is.stackSize = stackSize;
						newStacks.add(is);
					});
					this.outputs.add(newStacks);
				}
				
			}
			else {
				AdvancedMaterials.getLogger().severe("Unhandled Type for " + output.toString());
				throw new NullPointerException("Just stopping registration");
			}
		}
	}
	
	@Override
	public ImmutableList<Object> getOutputs() {
		return ImmutableList.copyOf(outputs);
	}

	@Override
	public ImmutableList<Object> getInputs() {
		return ImmutableList.of(input);
	}

	@Override
	public boolean matches(IInventory inv) {
		if (inv.getStackInSlot(0) == null)
			return false;
		if (input instanceof ItemStack)
			return OreDictionary.itemMatches(inv.getStackInSlot(0), (ItemStack)input, false);
		if (input instanceof ArrayList) {
			boolean hasMatch = false;
			for (ItemStack stack : ((ArrayList<ItemStack>)input))
				if (OreDictionary.itemMatches(inv.getStackInSlot(0), stack, false))
					hasMatch = true;
			return  hasMatch;
		}
		return false;
	}

}
