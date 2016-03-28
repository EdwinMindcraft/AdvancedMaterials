package mod.mindcraft.advancedmaterials.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import mod.mindcraft.advancedmaterials.utils.MetalDefinition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;

public class BlockMaterialOre extends BlockMaterial {

	public BlockMaterialOre(int subId) {
		super(subId);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		int matId = -1;
		for (Entry<Integer, Integer> entry : MetalRegistry.matOresMap().entrySet()) {
			if (entry.getValue() == subId * 16 + state.getValue(MAT))
				matId = entry.getKey();
		}
		if (matId == -1)
			return super.getDrops(world, pos, state, fortune);
		MetalDefinition def = MetalRegistry.getFromID(matId);
		System.out.println(fortune);
		if (def != null && def.isGem)
			drops.add(new ItemStack(AdvancedMaterials.ingot, 1 + (fortune > 0 ? new Random().nextInt(fortune) : 0), matId));
		if (def != null && !def.isGem)
			drops.add(new ItemStack(this, 1, state.getValue(MAT)));
		return drops;
	}
	
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int i = subId * 16; i < Math.min(MetalRegistry.matOresMap().size(), (subId + 1) * 16); i++) {
			list.add(new ItemStack(itemIn, 1, i - subId * 16));
		}
	}
	
	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		int matId = -1;
//		if (worldIn.getBlockState(pos).getValue(MAT) == 11)
//			System.out.println(pos);
		for (Entry<Integer, Integer> entry : MetalRegistry.matOresMap().entrySet()) {
			if (entry.getValue() == subId * 16 + worldIn.getBlockState(pos).getValue(MAT))
				matId = entry.getKey();
		}
		if (matId == -1)
			return 0xffffff;
		return MetalRegistry.getFromID(matId).color;
	}
	
	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
	
	@Override
	public int getHarvestLevel(IBlockState state) {
		int matId = -1;
		for (Entry<Integer, Integer> entry : MetalRegistry.matOresMap().entrySet()) {
			if (entry.getValue() == subId * 16 + state.getValue(MAT))
				matId = entry.getKey();
		}
		return MetalRegistry.getFromID(matId).miningLevel;
	}
	
	@Override
	public String getHarvestTool(IBlockState state) {
		return "pickaxe";
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
		int matId = -1;
		for (Entry<Integer, Integer> entry : MetalRegistry.matOresMap().entrySet()) {
			if (entry.getValue() == subId * 16 + world.getBlockState(pos).getValue(MAT))
				matId = entry.getKey();
		}
		if (matId == -1)
			return super.getExpDrop(world, pos, fortune);
		MetalDefinition def = MetalRegistry.getFromID(matId);
		if (def != null && def.isGem)
			return (int) (1F * fortune);
		return super.getExpDrop(world, pos, fortune);
	}
}
