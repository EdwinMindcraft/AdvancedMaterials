package mod.mindcraft.advancedmaterials.items;

import java.util.Map.Entry;

import mod.mindcraft.advancedmaterials.blocks.BlockMaterial;
import mod.mindcraft.advancedmaterials.blocks.BlockMaterialOre;
import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBlockMaterial extends ItemBlock{
	
	int subId;
	
	public ItemBlockMaterial(Block block) {
		super(block);
		subId = ((BlockMaterial)block).getSubId();
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		return super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState.withProperty(BlockMaterial.MAT, stack.getItemDamage()));
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (block instanceof BlockMaterialOre) {
			int matId = -1;
			for (Entry<Integer, Integer> entry : MetalRegistry.matOresMap().entrySet()) {
				if (entry.getValue() == subId * 16 + stack.getMetadata())
					matId = entry.getKey();
			}
			if (matId == -1)
				return 0xffffff;
			return MetalRegistry.getFromID(matId).color;
		}
		return MetalRegistry.array().get(MathHelper.clamp_int(stack.getItemDamage() + subId*16, 0, MetalRegistry.array().size() - 1)).color;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (block instanceof BlockMaterialOre) {
			int matId = -1;
			for (Entry<Integer, Integer> entry : MetalRegistry.matOresMap().entrySet()) {
				if (entry.getValue() == subId * 16 + stack.getMetadata())
					matId = entry.getKey();
			}
			return StatCollector.translateToLocal("tile.advmat.ore.name").replaceAll("%%mat", StatCollector.translateToLocal("advmat." + MetalRegistry.getFromID(matId).oreDict.toLowerCase()));
		}
		return StatCollector.translateToLocal("tile.advmat.block.name").replaceAll("%%mat", StatCollector.translateToLocal("advmat." + MetalRegistry.getFromID(stack.getItemDamage() + subId * 16).oreDict.toLowerCase()));
	}
}
