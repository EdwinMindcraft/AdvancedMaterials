package mod.mindcraft.advancedmaterials.utils;

import java.util.Map.Entry;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.blocks.BlockMaterial;
import mod.mindcraft.advancedmaterials.blocks.BlockMaterialOre;
import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import mod.mindcraft.advancedmaterials.items.ItemMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class IngotGemModelLocation extends StateMapperBase implements ItemMeshDefinition{
	
	public IngotGemModelLocation() {
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		if (stack.getItem() instanceof ItemMaterials) {
			if (MetalRegistry.array().get(MathHelper.clamp_int(stack.getItemDamage(), 0, MetalRegistry.array().size() - 1)).isGem)
				return new ModelResourceLocation(AdvancedMaterials.MODID + ":gem", "inventory");
			return new ModelResourceLocation(AdvancedMaterials.MODID + ":ingot", "inventory");
		} else if (Block.getBlockFromItem(stack.getItem()) instanceof BlockMaterialOre) {
			int matId = -1;
			for (Entry<Integer, Integer> entry : MetalRegistry.matOresMap().entrySet()) {
				if (entry.getValue() == (((BlockMaterialOre)Block.getBlockFromItem(stack.getItem())).subId * 16 + stack.getItemDamage()))
					matId = entry.getKey();
			}
			if (matId != -1 && MetalRegistry.array().get(matId).isGem)
				return new ModelResourceLocation(AdvancedMaterials.MODID + ":oreGem", "inventory");
			return new ModelResourceLocation(AdvancedMaterials.MODID + ":ore", "inventory");		
		} else {
			if (MetalRegistry.array().get(MathHelper.clamp_int(stack.getItemDamage() + ((BlockMaterial)Block.getBlockFromItem(stack.getItem())).subId * 16, 0, MetalRegistry.array().size() - 1)).isGem)
				return new ModelResourceLocation(AdvancedMaterials.MODID + ":blockGem", "inventory");
			return new ModelResourceLocation(AdvancedMaterials.MODID + ":block", "inventory");		
		}
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		if (state.getBlock() instanceof BlockMaterialOre) {
			int matId = -1;
			for (Entry<Integer, Integer> entry : MetalRegistry.matOresMap().entrySet()) {
				if (entry.getValue() == ((BlockMaterialOre)state.getBlock()).subId * 16 + state.getValue(BlockMaterial.MAT))
					matId = entry.getKey();
			}
			if (matId != -1 && MetalRegistry.array().get(matId).isGem)
				return new ModelResourceLocation(AdvancedMaterials.MODID + ":oreGem", "normal");
			return new ModelResourceLocation(AdvancedMaterials.MODID + ":ore", "normal");			
		}
		if (MetalRegistry.array().get(MathHelper.clamp_int(((BlockMaterial)state.getBlock()).getMaterialID(state), 0, MetalRegistry.array().size() - 1)).isGem)
			return new ModelResourceLocation(AdvancedMaterials.MODID + ":blockGem", "normal");
		return new ModelResourceLocation(AdvancedMaterials.MODID + ":block", "normal");
	}

}
