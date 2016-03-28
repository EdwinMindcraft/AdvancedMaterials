package mod.mindcraft.advancedmaterials.plugin.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.mindcraft.advancedmaterials.integration.recipes.CentrifugeRecipe;

public class CentrifugeRecipeHandler implements IRecipeHandler<CentrifugeRecipe> {

	public CentrifugeRecipeHandler() {
	}

	@Override
	public Class<CentrifugeRecipe> getRecipeClass() {
		return CentrifugeRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return "advmat.centrifuge";
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CentrifugeRecipe recipe) {
		return new CentrifugeRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(CentrifugeRecipe recipe) {
		return true;
	}

}
