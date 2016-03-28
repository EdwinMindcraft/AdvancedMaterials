package mod.mindcraft.advancedmaterials.integration.registry;

import java.util.ArrayList;

import mod.mindcraft.advancedmaterials.integration.recipes.NuclearFusionRecipe;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.ImmutableList;

public class NuclearFusionRegistry {
	
	private static final ArrayList<NuclearFusionRecipe> recipes = new ArrayList<NuclearFusionRecipe>();
	
	public static void addRecipe(NuclearFusionRecipe recipe) {
		try {
			recipes.add(recipe);
		} catch (NullPointerException e) {}
	}

	public static NuclearFusionRecipe getRecipe(FluidStack... fluids) {
		for (NuclearFusionRecipe recipe : recipes)
			if (recipe.matches(fluids))
				return recipe;
		return null;
	}

	public static ImmutableList<NuclearFusionRecipe> getRecipes() {
		return ImmutableList.copyOf(recipes);
	}

}
