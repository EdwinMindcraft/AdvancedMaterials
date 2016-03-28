package mod.mindcraft.advancedmaterials.items;

import mod.mindcraft.advancedmaterials.blocks.BlockCustomOre;
import mod.mindcraft.advancedmaterials.blocks.BlockMetal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockColored extends ItemBlock {
	
	int color = 0xffffff;
	private String mat, suffix;

	public ItemBlockColored(Block block) {
		super(block);
		if (block instanceof BlockCustomOre) {
			color = ((BlockCustomOre)block).getBlockColor();
			this.suffix = "ore";
			mat = ((BlockCustomOre)block).getMat();
		}
		if (block instanceof BlockMetal) {
			this.suffix = "block";
			mat = ((BlockMetal)block).getMat();
			color = ((BlockMetal)block).getBlockColor();
		}
		
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return color;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal("tile." + suffix + ".name").replace("%%mat", StatCollector.translateToLocal("advmat." + mat));
	}

}
