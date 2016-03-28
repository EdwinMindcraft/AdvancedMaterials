package mod.mindcraft.advancedmaterials.integration;

public interface IMachine<T extends IMachineRecipe, V extends IRecipeRegistry<T>> {
	
	public int[] getInputSlots();
	
	public int[] getOutputSlots();
	
	public int getMaxProgress();
	
	public V getRegistry();
}
