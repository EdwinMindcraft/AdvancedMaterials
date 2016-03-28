package mod.mindcraft.advancedmaterials.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;

public class BlockMetal extends Block {
	
	private final int color;
	private final String mat;
	
	public BlockMetal(int color, String mat) {
		super(Material.iron);
		this.color = color;
		setHardness(3.0F);
		setResistance(5.0F);
		this.mat = mat;
	}
	
	@Override
	public int getRenderColor(IBlockState state) {
		return color;
	}
	
	public String getMat() {
		return mat;
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
	public String getLocalizedName() {
		return StatCollector.translateToLocal("advmat." + mat) + StatCollector.translateToLocal("tile.block.name");
	}
}
