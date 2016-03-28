package mod.mindcraft.advancedmaterials.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemColored extends Item {
	
	final int color;
	private final String mat, suffix;
	
	public ItemColored(int color, String mat, String suffix) {
		this.color = color;
		this.mat = mat;
		this.suffix = suffix;
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return color;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal("item." + suffix + ".name").replaceAll("%%mat", StatCollector.translateToLocal("advmat." + mat));
	}
}
