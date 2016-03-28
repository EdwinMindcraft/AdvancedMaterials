package mod.mindcraft.advancedmaterials.tileentity;

import mod.mindcraft.advancedmaterials.integration.TileEntityMachine;
import mod.mindcraft.advancedmaterials.integration.recipes.GrinderRecipe;
import mod.mindcraft.advancedmaterials.integration.registry.GrinderRegistry;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

public class TileEntityGrinder extends TileEntityMachine<GrinderRecipe, GrinderRegistry> {

	public TileEntityGrinder() {
		super(3);
	}

	@Override
	public int[] getInputSlots() {
		return new int[]{0};
	}

	@Override
	public int[] getOutputSlots() {
		return new int[]{1, 2};
	}

	@Override
	public int getMaxProgress() {
		return 200;
	}

	@Override
	public GrinderRegistry getRegistry() {
		return new GrinderRegistry();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0, 1, 2};
	}

	@Override
	public String getName() {
		return "Grinder";
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
