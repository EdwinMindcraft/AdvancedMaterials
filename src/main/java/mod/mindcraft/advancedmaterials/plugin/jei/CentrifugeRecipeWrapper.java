package mod.mindcraft.advancedmaterials.plugin.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import mod.mindcraft.advancedmaterials.integration.recipes.CentrifugeRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.ImmutableList;

public class CentrifugeRecipeWrapper implements IRecipeWrapper {
	
	public CentrifugeRecipe recipe;
	
	public CentrifugeRecipeWrapper(CentrifugeRecipe recipe) {
		this.recipe = recipe;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getInputs() {
		return ImmutableList.copyOf(recipe.getInputs());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getOutputs() {
		ArrayList<Object> outputs = new ArrayList<Object>();
		for (Object obj : recipe.getOutputs()) {
			if (obj instanceof ItemStack) {
				ItemStack is = ((ItemStack)obj).copy();
				NBTTagCompound tag = new NBTTagCompound();
				NBTTagCompound display = new NBTTagCompound();
				NBTTagList lore = new NBTTagList();
				lore.appendTag(new NBTTagString(EnumChatFormatting.RESET.toString() + EnumChatFormatting.BLUE.toString() + "Chance : " + is.stackSize + " %"));
				display.setTag("Lore", lore);
				tag.setTag("display", display);
				is.setTagCompound(tag);
				is.stackSize = (int) Math.max(Math.round((float)is.stackSize / 100F), 1);
				outputs.add(is);
			} else if (obj instanceof ArrayList) {
				ArrayList<ItemStack> subOutput = new ArrayList<ItemStack>();
				for (ItemStack stack : (ArrayList<ItemStack>)obj) {
					ItemStack is = stack.copy();
					NBTTagCompound tag = new NBTTagCompound();
					NBTTagCompound display = new NBTTagCompound();
					NBTTagList lore = new NBTTagList();
					lore.appendTag(new NBTTagString(EnumChatFormatting.RESET.toString() + EnumChatFormatting.BLUE.toString() + "Chance : " + is.stackSize + " %"));
					display.setTag("Lore", lore);
					tag.setTag("display", display);
					is.setTagCompound(tag);
					is.stackSize = (int) Math.max(Math.round((float)is.stackSize / 100F), 1);
					subOutput.add(is);
				}
				outputs.add(subOutput);
			}
		}
		return ImmutableList.copyOf(outputs);

	}

	@Override
	public List<FluidStack> getFluidInputs() {
		return ImmutableList.of();
	}

	@Override
	public List<FluidStack> getFluidOutputs() {
		return ImmutableList.of();
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight) {
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth,
			int recipeHeight, int mouseX, int mouseY) {
	}

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth,
			int recipeHeight) {
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return null;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
			int mouseButton) {
		return false;
	}

}
