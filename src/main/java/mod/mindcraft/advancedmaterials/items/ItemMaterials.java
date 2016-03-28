package mod.mindcraft.advancedmaterials.items;

import java.util.List;

import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class ItemMaterials extends Item {
	
	public final String type;
	
	public ItemMaterials(String type) {
		this.type = type;
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (type.equals("ingot") && MetalRegistry.array().get(MathHelper.clamp_int(stack.getItemDamage(), 0, MetalRegistry.array().size() - 1)).isGem)
			return StatCollector.translateToLocal("item.gem.name").replaceAll("%%mat", StatCollector.translateToLocal("advmat." + MetalRegistry.array().get(MathHelper.clamp_int(stack.getItemDamage(), 0, MetalRegistry.array().size() - 1)).oreDict.toLowerCase()));			
		return StatCollector.translateToLocal("item." + type + ".name").replaceAll("%%mat", StatCollector.translateToLocal("advmat." + MetalRegistry.array().get(MathHelper.clamp_int(stack.getItemDamage(), 0, MetalRegistry.array().size() - 1)).oreDict.toLowerCase()));
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0; i < MetalRegistry.array().size(); i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (renderPass == 1)
			return 0xffffff;
		return MetalRegistry.array().get(MathHelper.clamp_int(stack.getItemDamage(), 0, MetalRegistry.array().size() - 1)).color;
	}
}
