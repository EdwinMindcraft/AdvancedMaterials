package mod.mindcraft.advancedmaterials.plugin.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.mindcraft.advancedmaterials.integration.recipes.CrystallizerRecipe;

public class CrystallizerRecipeHandler implements IRecipeHandler<CrystallizerRecipe> {

	@Override
	public Class<CrystallizerRecipe> getRecipeClass() {
		return CrystallizerRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return "advmat.crystallizerrecipe";
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CrystallizerRecipe recipe) {
		return new CrystallizerRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(CrystallizerRecipe recipe) {
		return true;
	}

}
