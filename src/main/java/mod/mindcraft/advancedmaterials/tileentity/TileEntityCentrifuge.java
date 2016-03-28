package mod.mindcraft.advancedmaterials.tileentity;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import mod.mindcraft.advancedmaterials.integration.TileEntityMachine;
import mod.mindcraft.advancedmaterials.integration.recipes.CentrifugeRecipe;
import mod.mindcraft.advancedmaterials.integration.registry.CentrifugeRegistry;

public class TileEntityCentrifuge extends TileEntityMachine<CentrifugeRecipe, CentrifugeRegistry> {

	public TileEntityCentrifuge() {
		super(5);
	}

	@Override
	public int[] getInputSlots() {
		return new int[]{0};
	}

	@Override
	public int[] getOutputSlots() {
		return new int[] {1, 2, 3, 4};
	}

	@Override
	public int getMaxProgress() {
		return 200;
	}

	@Override
	public CentrifugeRegistry getRegistry() {
		return new CentrifugeRegistry();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0, 1, 2, 3, 4};
	}

	@Override
	public String getName() {
		return "Centrifuge";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName());
	}
	
}