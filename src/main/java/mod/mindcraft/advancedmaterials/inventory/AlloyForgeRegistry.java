package mod.mindcraft.advancedmaterials.inventory;

import java.util.ArrayList;

import net.minecraft.inventory.IInventory;

public class AlloyForgeRegistry {
	
	private static final ArrayList<AlloyForgeRecipe> recipes = new ArrayList<AlloyForgeRecipe>();
	
	public static void addAlloyForgeRecipe(AlloyForgeRecipe recipe) {
		recipes.add(recipe);
	}
	
	public static ArrayList<AlloyForgeRecipe> getRecipes() {
		return recipes;
	}
	
	public static AlloyForgeRecipe getMatchingRecipe(IInventory inv) {
		for (AlloyForgeRecipe recipe : recipes) {
			if (recipe.matches(inv))
				return recipe;
		}
		return null;
	}
	
}
