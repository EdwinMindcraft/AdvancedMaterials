package mod.mindcraft.advancedmaterials.items;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemPickaxeColored extends ItemPickaxe {

	final int color;
	private final String mat;
	
	public ItemPickaxeColored(ToolMaterial material, int color) {
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
		return StatCollector.translateToLocal("item.pickaxe.name").replace("%%mat", StatCollector.translateToLocal("advmat." + mat));
	}
}