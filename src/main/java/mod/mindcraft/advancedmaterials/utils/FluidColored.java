package mod.mindcraft.advancedmaterials.utils;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidColored extends Fluid {
	
	private int color;
	
	public FluidColored(String fluidName, int color) {
		super(fluidName, new ResourceLocation(AdvancedMaterials.MODID, "blocks/metal_still"), new ResourceLocation(AdvancedMaterials.MODID, "blocks/metal_flow"));
		this.color = color;
	}
	
	@Override
	public ResourceLocation getStill() {
		return still;
	}
	
	
	@Override
	public int getColor() {
		return color + (0xff << 24);
	}
	
	

}
