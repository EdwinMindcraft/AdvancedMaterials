package mod.mindcraft.advancedmaterials.blocks;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityGrinder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGrinder extends BlockContainer {
	
	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
	
	public BlockGrinder() {
		super(Material.rock, MapColor.ironColor);
		setHardness(3.0F);
		setResistance(5.0F);
		setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Z));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGrinder();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking())
			return false;
		if (worldIn.getTileEntity(pos) == null) return false;
		playerIn.openGui(AdvancedMaterials.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AXIS).equals(EnumFacing.Axis.Z) ? 0 : 1;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(AXIS, meta == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, AXIS);
	}
	
	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos,
			EnumFacing side) {
		return true;
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	@Override
	public boolean isFullBlock() {
		return false;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		return getDefaultState().withProperty(AXIS, placer.getHorizontalFacing().getAxis());
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public boolean isNormalCube() {
		return false;
	}
}
