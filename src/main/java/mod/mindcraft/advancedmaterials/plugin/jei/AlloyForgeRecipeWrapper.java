package mod.mindcraft.advancedmaterials.plugin.jei;

import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import mod.mindcraft.advancedmaterials.inventory.AlloyForgeRecipe;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class AlloyForgeRecipeWrapper implements IRecipeWrapper {
	
	AlloyForgeRecipe recipe;
	
	public AlloyForgeRecipeWrapper(AlloyForgeRecipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public List getInputs() {
		return recipe.getInput();
	}

	@Override
	public List getOutputs() {
		return Lists.newArrayList(recipe.getOutput());
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
		return ImmutableList.of();
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
			int mouseButton) {
		return false;
	}

}
