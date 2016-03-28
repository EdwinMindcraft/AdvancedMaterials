package mod.mindcraft.advancedmaterials.tileentity;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import mod.mindcraft.advancedmaterials.integration.TileEntityMachine;
import mod.mindcraft.advancedmaterials.integration.recipes.CrystallizerRecipe;
import mod.mindcraft.advancedmaterials.integration.registry.CrystallizerRegistry;

public class TileEntityCrystallizer extends TileEntityMachine<CrystallizerRecipe, CrystallizerRegistry> {

	public TileEntityCrystallizer() {
		super(2);
	}

	@Override
	public int[] getInputSlots() {
		return new int[]{0};
	}

	@Override
	public int[] getOutputSlots() {
		return new int[]{1};
	}

	@Override
	public int getMaxProgress() {
		return 1200;
	}

	@Override
	public CrystallizerRegistry getRegistry() {
		return new CrystallizerRegistry();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0, 1};
	}

	@Override
	public String getName() {
		return "Crystallizer";
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
