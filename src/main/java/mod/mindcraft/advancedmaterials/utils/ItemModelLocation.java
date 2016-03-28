package mod.mindcraft.advancedmaterials.utils;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class ItemModelLocation implements ItemMeshDefinition{
	
	private ModelResourceLocation loc;

	public ItemModelLocation(ModelResourceLocation loc) {
		this.loc = loc;
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
			return loc;
	}

}
