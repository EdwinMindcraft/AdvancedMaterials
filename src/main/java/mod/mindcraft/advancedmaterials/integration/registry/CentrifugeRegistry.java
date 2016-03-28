package mod.mindcraft.advancedmaterials.integration.registry;

import java.util.ArrayList;

import mod.mindcraft.advancedmaterials.integration.IRecipeRegistry;
import mod.mindcraft.advancedmaterials.integration.recipes.CentrifugeRecipe;
import net.minecraft.inventory.IInventory;

import com.google.common.collect.ImmutableList;

public class CentrifugeRegistry implements IRecipeRegistry<CentrifugeRecipe> {
	
	private static final ArrayList<CentrifugeRecipe> recipes = new ArrayList<CentrifugeRecipe>();
	
	@Override
	public void addRecipe(CentrifugeRecipe recipe) {
		try {
			recipes.add(recipe);
		} catch (NullPointerException e) {}
	}

	@Override
	public CentrifugeRecipe getRecipe(IInventory inv) {
		for (CentrifugeRecipe recipe : recipes)
			if (recipe.matches(inv))
				return recipe;
		return null;
	}

	@Override
	public ImmutableList<CentrifugeRecipe> getRecipes() {
		return ImmutableList.copyOf(recipes);
	}

}
