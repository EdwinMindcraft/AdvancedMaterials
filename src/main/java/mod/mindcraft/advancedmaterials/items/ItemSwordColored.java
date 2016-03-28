package mod.mindcraft.advancedmaterials.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;

public class ItemSwordColored extends ItemSword {
	
	public final int color;
	private final String mat;
	
	public ItemSwordColored(ToolMaterial material, int color) {
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
		return StatCollector.translateToLocal("item.sword.name").replace("%%mat", StatCollector.translateToLocal("advmat." + mat));
	}
}
