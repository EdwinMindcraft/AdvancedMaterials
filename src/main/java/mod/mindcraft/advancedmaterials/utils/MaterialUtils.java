package mod.mindcraft.advancedmaterials.utils;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MaterialUtils {
	public static ToolDefinition defineTools(String name, String armorTexture, int durability, int[] reductionAmounts, int enchantability, int harvestLevel, float efficiency, float damage, int color) {
		ArmorMaterial armor = EnumHelper.addArmorMaterial(name, AdvancedMaterials.MODID + ":" + armorTexture, durability, reductionAmounts, enchantability);
		ToolMaterial tool = EnumHelper.addToolMaterial(name, harvestLevel, durability, efficiency, damage, enchantability);
		return new ToolDefinition(tool, armor, color);
	}
}
