package mod.mindcraft.advancedmaterials.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class BlockStateMapper extends StateMapperBase implements ItemMeshDefinition {

	private ModelResourceLocation location;

	public BlockStateMapper(ModelResourceLocation location) {
		this.location = location;
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		return location;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return location;
	}

}
