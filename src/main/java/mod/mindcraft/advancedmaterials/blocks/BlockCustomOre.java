package mod.mindcraft.advancedmaterials.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;

public class BlockCustomOre extends Block{
	
	private final int color;
	private final String mat;
	private ItemStack dropped;
		
	public String getMat() {
		return mat;
	}
		
	public BlockCustomOre(int color, ItemStack dropped, String mat) {
		super(Material.rock);
		this.color = color;
		setHardness(3.0F);
		setResistance(5.0F);
		this.dropped = dropped;
		this.mat = mat;
	}
	
	public BlockCustomOre(int color, String mat) {
		super(Material.rock);
		this.color = color;
		setHardness(3.0F);
		setResistance(5.0F);
		this.mat = mat;
	}
	
	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos,
			EnumFacing side) {
		return super.shouldSideBeRendered(worldIn, pos, side);
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	public boolean isNormalCube() {
		return false;
	}
	
	@Override
	public int getRenderColor(IBlockState state) {
		return color;
	}
	
	@Override
	public int getBlockColor() {
		return color;
	}
	
	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		return color;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return dropped != null ? 1 + (fortune > 0 ? random.nextInt(fortune) : 0) : 1;
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
		return dropped != null ? 1 : 0;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return dropped != null ? dropped.getItem() : Item.getItemFromBlock(this);
	}
	
	@Override
	public String getLocalizedName() {
		return StatCollector.translateToLocal("advmat." + mat) + StatCollector.translateToLocal("tile.ore.name");
	}
}
