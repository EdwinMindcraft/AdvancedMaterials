package mod.mindcraft.advancedmaterials.items;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemAxeColored extends ItemAxe {
	
	final int color;
	private final String mat;
	
	public ItemAxeColored(ToolMaterial material, int color) {
		super(material);
		this.color = color;
		mat = material.name().toLowerCase();
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (renderPass == 1)
			return color;
		return super.getColorFromItemStack(stack, renderPass);
		
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal("item.axe.name").replace("%%mat", StatCollector.translateToLocal("advmat." + mat));
	}

}
