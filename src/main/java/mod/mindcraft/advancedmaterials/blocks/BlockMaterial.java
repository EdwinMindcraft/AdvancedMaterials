package mod.mindcraft.advancedmaterials.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMaterial extends Block {
	
	public final int subId;
	public static final PropertyInteger MAT = PropertyInteger.create("mat", 0, 15);
	
	public BlockMaterial(int subId) {
		super(Material.iron);
		this.subId = subId;
		setHardness(3.0F);
		setResistance(5.0F);
		setDefaultState(blockState.getBaseState().withProperty(MAT, 0));
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int i = subId * 16; i < Math.min(MetalRegistry.array().size(), (subId + 1) * 16); i++) {
			list.add(new ItemStack(itemIn, 1, i - subId * 16));
		}
	}
	
	public int getSubId() {
		return subId;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return Lists.newArrayList(new ItemStack(this, 1, state.getValue(MAT)));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(MAT);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(MAT, meta);
	}
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, MAT);
	}
	
	public int getMaterialID(IBlockState stack) {
		return subId * 16 + stack.getValue(MAT);
	}
	
	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		int meta = worldIn.getBlockState(pos).getValue(MAT);
		return MetalRegistry.array().get(subId * 16 + meta).color;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, world.getBlockState(pos).getValue(MAT));
	}
}
