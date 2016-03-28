package mod.mindcraft.advancedmaterials.worldgen;

import java.util.Random;

import mod.mindcraft.advancedmaterials.blocks.BlockMaterialOre;
import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import mod.mindcraft.advancedmaterials.utils.MetalDefinition;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (MetalDefinition metal : MetalRegistry.array()) {
			if (!metal.hasOre)
				continue;
			WorldGenMinable ore;
			if (metal.isGem)
				ore = new WorldGenMinable(MetalRegistry.getOreForID(MetalRegistry.getID(metal)).getDefaultState().withProperty(BlockMaterialOre.MAT, (MetalRegistry.matOresMap().get(MetalRegistry.getID(metal)) - ((BlockMaterialOre)MetalRegistry.getOreForID(MetalRegistry.getID(metal))).subId * 16)), (metal.veinSize > 0 ? metal.veinSize : 4));
			else
				ore = new WorldGenMinable(MetalRegistry.getOreForID(MetalRegistry.getID(metal)).getDefaultState().withProperty(BlockMaterialOre.MAT, (MetalRegistry.matOresMap().get(MetalRegistry.getID(metal)) - ((BlockMaterialOre)MetalRegistry.getOreForID(MetalRegistry.getID(metal))).subId * 16)), 8);
			for (int i = 0; i < (metal.tries > 0 ? metal.tries : (metal.isGem ? 4 : 16)); i++) {
				BlockPos pos = new BlockPos(random.nextInt(16) + chunkX * 16, random.nextInt(metal.isGem ? 16 : 64), random.nextInt(16) + chunkZ * 16);
				ore.generate(world, random, pos);
			}
		}
	}
	
}
