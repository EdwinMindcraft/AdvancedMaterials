package mod.mindcraft.advancedmaterials.items;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemArmorColored extends ItemArmor {
	
	final int color;
	final String mat;
	
	public ItemArmorColored(ArmorMaterial material, int renderIndex, int armorType, int color) {
		super(material, renderIndex, armorType);
		this.color = color;
		this.mat = material.name().toLowerCase();
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return color;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String armor = armorType == 0 ? "helmet" : (armorType == 1 ? "chestplate" : (armorType == 2 ? "leggings" : (armorType == 3 ? "boots" : armorType + "")));
		return StatCollector.translateToLocal("item.armor." + armor + ".name").replaceAll("%%mat", StatCollector.translateToLocal("advmat." + mat));
	}

}
