package mod.mindcraft.advancedmaterials.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AlloyForgeRecipe {
	
	private ItemStack output;
	private ArrayList<Object> input;
	
	public AlloyForgeRecipe(ItemStack output, Object... inputs) {
		this.output = output.copy();
		input = new ArrayList<Object>();
        for (Object in : inputs)
        {
            if (in instanceof ItemStack)
            {
                input.add(((ItemStack)in).copy());
            }
            else if (in instanceof Item)
            {
                input.add(new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
                input.add(new ItemStack((Block)in));
            }
            else if (in instanceof String)
            {
                input.add(OreDictionary.getOres((String)in));
            }
            else
            {
                String ret = "Invalid alloy forge recipe: ";
                for (Object tmp :  inputs)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
	}
	
	public ArrayList<Object> getInput() {
		return input;
	}
	
	public ItemStack getOutput() {
		return output.copy();
	}
	
	public boolean matches(IInventory inv) {
        ArrayList<Object> required = new ArrayList<Object>(input);

        for (int x = 0; x < inv.getSizeInventory(); x++)
        {
            ItemStack slot = inv.getStackInSlot(x);

            if (slot != null)
            {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

                while (req.hasNext())
                {
                    boolean match = false;
                    Object next = req.next();
                    //System.out.println(next.toString());

                    if (next instanceof ItemStack)
                    {
                        match = OreDictionary.itemMatches((ItemStack)next, slot, false);
                    }
                    else if (next instanceof List)
                    {
                        Iterator<ItemStack> itr = ((List<ItemStack>)next).iterator();
                        while (itr.hasNext() && !match)
                        {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                	//System.out.println(slot.toString());
                    return false;
                }
            }
        }
        //System.out.println(required.isEmpty());
        return required.isEmpty();
	}

}
