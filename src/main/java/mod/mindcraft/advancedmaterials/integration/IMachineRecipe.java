package mod.mindcraft.advancedmaterials.integration;

import net.minecraft.inventory.IInventory;

import com.google.common.collect.ImmutableList;

public interface IMachineRecipe {
	
	public ImmutableList<Object> getOutputs();
	
	public ImmutableList<Object> getInputs();
	
	public boolean matches (IInventory inv);
}
