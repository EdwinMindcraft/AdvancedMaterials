package mod.mindcraft.advancedmaterials.integration.registry;

import java.util.ArrayList;

import net.minecraft.inventory.IInventory;

import com.google.common.collect.ImmutableList;

import mod.mindcraft.advancedmaterials.integration.IRecipeRegistry;
import mod.mindcraft.advancedmaterials.integration.recipes.GrinderRecipe;

public class GrinderRegistry implements IRecipeRegistry<GrinderRecipe> {
	
	private static final ArrayList<GrinderRecipe> recipes = new ArrayList<GrinderRecipe>();
	
	@Override
	public void addRecipe(GrinderRecipe recipe) {
		try {
			recipes.add(recipe);
		} catch (NullPointerException e) {}
	}

	@Override
	public GrinderRecipe getRecipe(IInventory inv) {
		for (GrinderRecipe recipe : recipes)
			if (recipe.matches(inv))
				return recipe;
		return null;
	}

	@Override
	public ImmutableList<GrinderRecipe> getRecipes() {
		return ImmutableList.copyOf(recipes);
	}

}
