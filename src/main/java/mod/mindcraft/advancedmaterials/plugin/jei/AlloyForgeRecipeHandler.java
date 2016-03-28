package mod.mindcraft.advancedmaterials.plugin.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.mindcraft.advancedmaterials.inventory.AlloyForgeRecipe;

public class AlloyForgeRecipeHandler implements IRecipeHandler<AlloyForgeRecipe> {

	@Override
	public Class<AlloyForgeRecipe> getRecipeClass() {
		return AlloyForgeRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return "advmat.alloyrecipe";
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(AlloyForgeRecipe recipe) {
		return new AlloyForgeRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(AlloyForgeRecipe recipe) {
		return true;
	}

}
