package mod.mindcraft.advancedmaterials.integration;

import java.util.ArrayList;
import java.util.HashMap;

import mod.mindcraft.advancedmaterials.blocks.BlockMaterial;
import mod.mindcraft.advancedmaterials.blocks.BlockMaterialOre;
import mod.mindcraft.advancedmaterials.items.ItemBlockMaterial;
import mod.mindcraft.advancedmaterials.utils.MetalDefinition;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class MetalRegistry {
	
	private MetalRegistry() {}
	
	private static final ArrayList<MetalDefinition> registry = new ArrayList<MetalDefinition>();
	private static final HashMap<MetalDefinition, ArrayList<String>> alias = new HashMap<MetalDefinition, ArrayList<String>>();
	private static final HashMap<Integer, Integer> matOreMap = new HashMap<Integer, Integer>();
	private static final ArrayList<Block> blocks = new ArrayList<Block>();
	private static final ArrayList<Block> ores = new ArrayList<Block>();
	
	public static void registerMetal(MetalDefinition def, String... otherOreDict) {
		int id = registry.size() * 1;
		registry.add(def);
		alias.put(def, Lists.newArrayList(otherOreDict));
		if (blocks.size() * 16 < registry.size()) {
			Block block = new BlockMaterial(blocks.size());
			GameRegistry.registerBlock(block, ItemBlockMaterial.class, "blockMat" + blocks.size());
			blocks.add(block);
		}
		if (def.hasOre) {
			matOreMap.put(id, matOreMap.size());
			if (ores.size() * 16 < matOreMap.size()) {
				Block block = new BlockMaterialOre(ores.size());
				GameRegistry.registerBlock(block, ItemBlockMaterial.class, "oreMat" + ores.size());
				ores.add(block);
			}
		}
	}
	
	public static ImmutableList<MetalDefinition> array() {
		return ImmutableList.copyOf(registry);
	}
	
	public static ImmutableList<Block> blocks() {
		return ImmutableList.copyOf(blocks);
	}
	
	public static ImmutableList<Block> ores() {
		return ImmutableList.copyOf(ores);
	}
	
	public static ImmutableMap<Integer, Integer> matOresMap() {
		return ImmutableMap.copyOf(matOreMap);
	}
	
	public static int getID(MetalDefinition def) {
		for (int i = 0; i < array().size(); i++) {
			if (def.oreDict.equals(array().get(i).oreDict))
				return i;
		}
		return -1;
	}
	
	public static MetalDefinition getFromName(String name) {
		for (MetalDefinition def : registry) {
			if (name.equalsIgnoreCase(def.oreDict))
				return def;
		}
		return null;
	}
	
	public static Block getBlockForID(int id) {
		for (int i = 0; i < array().size(); i++) {
			if (i == id)
				return blocks().get((int) Math.floor(((double)i / 16D)));
		}
		return blocks().get(0);
	}
	
	public static Block getOreForID(int id) {
		int oreID = matOreMap.get(id);
		return ores().get((int) Math.floor((float)oreID / 16F));
	}
	
	public static MetalDefinition getFromID(int id) {
		return array().get(id);
	}
	
	public static void oreDict() {
		registry.forEach(t -> t.addToOreDict(t.oreDict));
		alias.forEach((k,v)->v.forEach(t->k.addToOreDict(t)));
	}
}