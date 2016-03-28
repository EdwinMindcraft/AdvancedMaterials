package mod.mindcraft.advancedmaterials.plugin.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.mindcraft.advancedmaterials.integration.recipes.GrinderRecipe;

public class GrinderRecipeHandler implements IRecipeHandler<GrinderRecipe> {

	public GrinderRecipeHandler() {
	}

	@Override
	public Class<GrinderRecipe> getRecipeClass() {
		return GrinderRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return "advmat.grinder";
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(GrinderRecipe recipe) {
		return new GrinderRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(GrinderRecipe recipe) {
		return true;
	}

}
