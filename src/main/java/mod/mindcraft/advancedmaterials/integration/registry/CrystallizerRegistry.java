package mod.mindcraft.advancedmaterials.integration.registry;

import java.util.ArrayList;

import mod.mindcraft.advancedmaterials.integration.IRecipeRegistry;
import mod.mindcraft.advancedmaterials.integration.recipes.CrystallizerRecipe;
import net.minecraft.inventory.IInventory;

import com.google.common.collect.ImmutableList;

public class CrystallizerRegistry implements IRecipeRegistry<CrystallizerRecipe> {
	
	private static final ArrayList<CrystallizerRecipe> recipes = new ArrayList<CrystallizerRecipe>();
	
	@Override
	public void addRecipe(CrystallizerRecipe recipe) {
		try {
			recipes.add(recipe);
		} catch (NullPointerException e) {}
	}

	@Override
	public CrystallizerRecipe getRecipe(IInventory inv) {
		for (CrystallizerRecipe recipe : recipes)
			if (recipe.matches(inv))
				return recipe;
		return null;
	}

	@Override
	public ImmutableList<CrystallizerRecipe> getRecipes() {
		return ImmutableList.copyOf(recipes);
	}

}
