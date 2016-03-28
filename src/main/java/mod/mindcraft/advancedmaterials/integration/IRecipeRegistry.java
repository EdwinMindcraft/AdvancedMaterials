package mod.mindcraft.advancedmaterials.integration;

import com.google.common.collect.ImmutableList;

import net.minecraft.inventory.IInventory;

public interface IRecipeRegistry<T extends IMachineRecipe>{
	
	public abstract void addRecipe(T recipe);
	
	public abstract T getRecipe(IInventory inv);
	
	public abstract ImmutableList<T> getRecipes();
}
