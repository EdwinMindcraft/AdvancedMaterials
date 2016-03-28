package mod.mindcraft.advancedmaterials;

import java.util.ArrayList;
import java.util.logging.Logger;

import mod.mindcraft.advancedmaterials.blocks.BlockCentrifuge;
import mod.mindcraft.advancedmaterials.blocks.BlockCrystallizer;
import mod.mindcraft.advancedmaterials.blocks.BlockForge;
import mod.mindcraft.advancedmaterials.blocks.BlockGrinder;
import mod.mindcraft.advancedmaterials.blocks.BlockNuclearReactor;
import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import mod.mindcraft.advancedmaterials.integration.component.NuclearReactorComponent;
import mod.mindcraft.advancedmaterials.integration.recipes.CentrifugeRecipe;
import mod.mindcraft.advancedmaterials.integration.registry.CentrifugeRegistry;
import mod.mindcraft.advancedmaterials.integration.registry.NuclearReactorComponentRegistry;
import mod.mindcraft.advancedmaterials.items.ItemMaterials;
import mod.mindcraft.advancedmaterials.plugin.TinkersPlugin;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCentrifuge;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCrystallizer;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityForge;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityGrinder;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityNuclearReactor;
import mod.mindcraft.advancedmaterials.utils.BlockStateMapper;
import mod.mindcraft.advancedmaterials.utils.IngotGemModelLocation;
import mod.mindcraft.advancedmaterials.utils.ItemModelLocation;
import mod.mindcraft.advancedmaterials.utils.MetalDefinition;
import mod.mindcraft.advancedmaterials.worldgen.WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid=AdvancedMaterials.MODID, version="1.0", dependencies="after:tconstruct")
public class AdvancedMaterials {
	
	public static final String MODID = "madvancedmaterial";
	
	public static final ArrayList<String> fluidsBlocks = new ArrayList<String>();
	
	public static CreativeTabs tabAdvMat = new CreativeTabs("tabAdvMat") {
		
		@Override
		public Item getTabIconItem() {
			return AdvancedMaterials.ingot;
		}
	};
	public static CreativeTabs tabNuclearComponents = new CreativeTabs("tabNuclearComponents") {
		
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(AdvancedMaterials.nuclearReactor);
		}
	};
	
	//Machines
	public static final Block forge = new BlockForge().setHardness(5.0F).setResistance(5F).setUnlocalizedName("advmat.alloyforge").setCreativeTab(tabAdvMat);
	public static final Block grinder = new BlockGrinder().setHardness(5.0F).setResistance(5F).setUnlocalizedName("advmat.grinder").setCreativeTab(tabAdvMat);
	public static final Block centrifuge = new BlockCentrifuge().setHardness(5.0F).setResistance(5F).setUnlocalizedName("advmat.centrifuge").setCreativeTab(tabAdvMat);
	public static final Block crystallizer = new BlockCrystallizer().setHardness(5.0F).setResistance(5F).setUnlocalizedName("advmat.crystallizer").setCreativeTab(tabAdvMat);
	public static final Block nuclearReactor = new BlockNuclearReactor().setHardness(5F).setResistance(5F).setUnlocalizedName("advmat.nuclearreactor").setCreativeTab(tabAdvMat);
	
	public static final Item ingot = new ItemMaterials("ingot").setUnlocalizedName("advmat.ingot").setCreativeTab(tabAdvMat);
	public static final Item nugget = new ItemMaterials("nugget").setUnlocalizedName("advmat.nugget").setCreativeTab(tabAdvMat);
	public static final Item dust = new ItemMaterials("dust").setUnlocalizedName("advmat.dust").setCreativeTab(tabAdvMat);
	public static final Item tinyDust = new ItemMaterials("tinyDust").setUnlocalizedName("advmat.tinyDust").setCreativeTab(tabAdvMat);
	public static final Item impureDust = new ItemMaterials("impureDust").setUnlocalizedName("advmat.impureDust").setCreativeTab(tabAdvMat);

	//Parts
	public static final Item crystalTube = new Item().setUnlocalizedName("crystalTube").setCreativeTab(tabAdvMat);
		
	@SidedProxy(clientSide="mod.mindcraft.advancedmaterials.ClientProxy", serverSide="mod.mindcraft.advancedmaterials.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance(MODID)
	public static AdvancedMaterials instance;
	
	static {
		if (!FluidRegistry.isUniversalBucketEnabled())
			FluidRegistry.enableUniversalBucket();
		if (Loader.isModLoaded("tconstruct"))
			new TinkersPlugin();
	}
	
	@EventHandler
	public void preInit (FMLPreInitializationEvent e) {
		GameRegistry.registerBlock(forge, "alloyforge");
		GameRegistry.registerBlock(grinder, "grinder");
		GameRegistry.registerBlock(centrifuge, "centrifuge");
		GameRegistry.registerBlock(crystallizer, "crystallizer");
		GameRegistry.registerBlock(nuclearReactor, "nuclear.reactor");
		GameRegistry.registerItem(crystalTube, "part.crystalTube");
		
		GameRegistry.registerItem(ingot, "ingot");
		GameRegistry.registerItem(nugget, "nugget");
		GameRegistry.registerItem(dust, "dust");
		GameRegistry.registerItem(tinyDust, "tinyDust");
		GameRegistry.registerItem(impureDust, "impureDust");
		
		GameRegistry.registerTileEntity(TileEntityForge.class, "TileEntityAlloyForge");
		GameRegistry.registerTileEntity(TileEntityGrinder.class, "TileEntityGrinder");
		GameRegistry.registerTileEntity(TileEntityCentrifuge.class, "TileEntityCentrifuge");
		GameRegistry.registerTileEntity(TileEntityCrystallizer.class, "TileEntityCrystallizer");
		GameRegistry.registerTileEntity(TileEntityNuclearReactor.class, "TileEntityNuclearReactor");
		
		MetalRegistry.registerMetal(new MetalDefinition(0x8e4500, "Copper").generateOre(1));
		MetalRegistry.registerMetal(new MetalDefinition(0xccccff, "Tin").generateOre(1));
		MetalRegistry.registerMetal(new MetalDefinition(0xeeeeee, "Aluminum").generateOre(1), "Aluminium");
		MetalRegistry.registerMetal(new MetalDefinition(0xff0000, "Ruby").setGem().generateOre(2));
		MetalRegistry.registerMetal(new MetalDefinition(0x0000ff, "Sapphire").setGem().generateOre(2));
		MetalRegistry.registerMetal(new MetalDefinition(0xff00ff, "Amethyst").setGem().generateOre(2));
		MetalRegistry.registerMetal(new MetalDefinition(0x99ff00, "Peridot").setGem().generateOre(2));
		MetalRegistry.registerMetal(new MetalDefinition(0xff5500, "Garnet").setGem().generateOre(2));
		MetalRegistry.registerMetal(new MetalDefinition(0x333333, "Silicium").setGem().generateOre(2));
		MetalRegistry.registerMetal(new MetalDefinition(0xc15e00, "Bronze"));
		MetalRegistry.registerMetal(new MetalDefinition(0xffffff, "Crystal").setGem());
		MetalRegistry.registerMetal(new MetalDefinition(0xbbbbbb, "AdvancedIron"));
		MetalRegistry.registerMetal(new MetalDefinition(0xff7400, "Magmatite").setGem());
		MetalRegistry.registerMetal(new MetalDefinition(0xffe3ff, "Chrome"), "Chromium");
		MetalRegistry.registerMetal(new MetalDefinition(0xdab3ff, "Titanium"));
		MetalRegistry.registerMetal(new MetalDefinition(0x666666, "Silicon").setGem());
		MetalRegistry.registerMetal(new MetalDefinition(0x3500ad, "Lead").generateOre(1));
		MetalRegistry.registerMetal(new MetalDefinition(0xeeeeff, "Silver").generateOre(2));
		MetalRegistry.registerMetal(new MetalDefinition(0x999999, "Steel"));
		MetalRegistry.registerMetal(new MetalDefinition(0xcccccc, "Platinum").generateOre(3, 4, 1), "Platinium");
		MetalRegistry.registerMetal(new MetalDefinition(0x5e9100, "Uranium").generateOre(3, 4, 1));
		
		NuclearReactorComponentRegistry.defineComponent(NuclearReactorComponent.createFuel(5, 20, 2.25F, 2F, 36000), "uraniumCell", "cell.uranium", new ModelResourceLocation(MODID + ":uraniumCell", "inventory"));
		NuclearReactorComponentRegistry.defineComponent(NuclearReactorComponent.createFuel(22, 160, 5.0625F, 4F, 36000), "uraniumCellDual", "cell.uranium.dual", new ModelResourceLocation(MODID + ":uraniumCellDual", "inventory"));
		NuclearReactorComponentRegistry.defineComponent(NuclearReactorComponent.createFuel(202, 640, 25.62890625F, 16F, 36000), "uraniumCellQuad", "cell.uranium.quad", new ModelResourceLocation(MODID + ":uraniumCellQuad", "inventory"));
		NuclearReactorComponentRegistry.defineComponent(NuclearReactorComponent.createCoolant(10, 20, 8000), "coolantCell", "cell.coolant", new ModelResourceLocation(MODID + ":coolantCell", "inventory"));
		NuclearReactorComponentRegistry.defineComponent(NuclearReactorComponent.createCoolant(30, 60, 16000), "coolantCellTriple", "cell.coolant.triple", new ModelResourceLocation(MODID + ":coolantCellTriple", "inventory"));
		NuclearReactorComponentRegistry.defineComponent(NuclearReactorComponent.createCoolant(60, 120, 32000), "coolantCellHexa", "cell.coolant.hexa", new ModelResourceLocation(MODID + ":coolantCellHexa", "inventory"));
			
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 1);
		
		for (String fluid : fluidsBlocks) {
				Fluid f = FluidRegistry.getFluid(fluid);
				Item item = Item.getItemFromBlock(f.getBlock());
				BlockStateMapper state = new BlockStateMapper(new ModelResourceLocation(AdvancedMaterials.MODID + ":fluid", f.getName()));
				ModelBakery.registerItemVariants(item);
				ModelLoader.setCustomMeshDefinition(item, state);
				ModelLoader.setCustomStateMapper(f.getBlock(), state);
		}
		
		for (Block block : MetalRegistry.blocks()) {
			Item item = GameRegistry.findItem(block.getRegistryName().split(":")[0], block.getRegistryName().split(":")[1]);
			ModelLoader.setCustomStateMapper(block, new IngotGemModelLocation());
			ModelLoader.setCustomMeshDefinition(item, new IngotGemModelLocation());
		}
		
		for (Block ore : MetalRegistry.ores()) {
			Item item = GameRegistry.findItem(ore.getRegistryName().split(":")[0], ore.getRegistryName().split(":")[1]);
			ModelLoader.setCustomStateMapper(ore, new IngotGemModelLocation());
			ModelLoader.setCustomMeshDefinition(item, new IngotGemModelLocation());
		}
	}
	
	public static Logger getLogger() {
		return Logger.getLogger("Advanced Materials");
	}
	
	@EventHandler
	public void init (FMLInitializationEvent e) {
		MetalRegistry.oreDict();
		if (e.getSide().equals(Side.CLIENT)) {
			NuclearReactorComponentRegistry.registerTextures();
			ModelBakery.registerItemVariants(ingot, new ModelResourceLocation(AdvancedMaterials.MODID + ":ingot", "inventory"), new ModelResourceLocation(AdvancedMaterials.MODID + ":gem", "inventory"));
			
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ingot, new IngotGemModelLocation());
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(dust, new ItemModelLocation(new ModelResourceLocation(MODID + ":dust", "inventory")));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(nugget, new ItemModelLocation(new ModelResourceLocation(MODID + ":nugget", "inventory")));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(tinyDust, new ItemModelLocation(new ModelResourceLocation(MODID + ":tinyDust", "inventory")));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(impureDust, new ItemModelLocation(new ModelResourceLocation(MODID + ":impureDust", "inventory")));

			for (Block block : MetalRegistry.blocks()) {
				Item item = GameRegistry.findItem(block.getRegistryName().split(":")[0], block.getRegistryName().split(":")[1]);
				ModelBakery.registerItemVariants(item, new ModelResourceLocation(AdvancedMaterials.MODID + ":block", "inventory"), new ModelResourceLocation(AdvancedMaterials.MODID + ":blockGem", "inventory"));
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new IngotGemModelLocation());
			}
			
			for (Block ore : MetalRegistry.ores()) {
				Item item = GameRegistry.findItem(ore.getRegistryName().split(":")[0], ore.getRegistryName().split(":")[1]);
				ModelBakery.registerItemVariants(item, new ModelResourceLocation(AdvancedMaterials.MODID + ":ore", "inventory"), new ModelResourceLocation(AdvancedMaterials.MODID + ":oreGem", "inventory"));
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new IngotGemModelLocation());
			}
			
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GameRegistry.findItem(MODID, "alloyforge"), 0, new ModelResourceLocation(MODID + ":alloyforge", "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GameRegistry.findItem(MODID, "grinder"), 0, new ModelResourceLocation(MODID + ":grinder", "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GameRegistry.findItem(MODID, "centrifuge"), 0, new ModelResourceLocation(MODID + ":centrifuge", "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GameRegistry.findItem(MODID, "crystallizer"), 0, new ModelResourceLocation(MODID + ":crystallizer", "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(crystalTube, 0, new ModelResourceLocation(MODID + ":crystalTube", "inventory"));
		}
		
		if (Loader.isModLoaded("tconstruct")) {
			TinkersPlugin.defineMaterials();
		}
		
//		AlloyForgeRegistry.addAlloyForgeRecipe(new AlloyForgeRecipe(new ItemStack(bronzeDef.dust, 8), "dustTin", "dustCopper", "dustCopper", "dustCopper"));
//		AlloyForgeRegistry.addAlloyForgeRecipe(new AlloyForgeRecipe(new ItemStack(crystalDef.dust, 2), "dustRuby", "dustSapphire", "dustAmethyst", "dustPeridot", "dustGarnet"));
//		AlloyForgeRegistry.addAlloyForgeRecipe(new AlloyForgeRecipe(new ItemStack(advancedIronDef.dust), "dustCrystal", "dustIron", "dustIron", "dustIron"));
//		AlloyForgeRegistry.addAlloyForgeRecipe(new AlloyForgeRecipe(new ItemStack(magmatiteDef.ingot), "gemDiamond", Items.lava_bucket));
//		AlloyForgeRegistry.addAlloyForgeRecipe(new AlloyForgeRecipe(new ItemStack(siliconDef.dust), "dustCoal", "dustSilicium"));
//		
		new CentrifugeRegistry().addRecipe(new CentrifugeRecipe(new ItemStack(Items.redstone, 9), "dustRuby*150", "tinyDustChrome*10"));
		new CentrifugeRegistry().addRecipe(new CentrifugeRecipe(new ItemStack(Items.dye, 9, 4), "dustSapphire*150", "tinyDustTitanium*10"));
		new CentrifugeRegistry().addRecipe(new CentrifugeRecipe("dustRuby*4", "tinyDustChrome*250", "tinyDustCrystal*10"));
		new CentrifugeRegistry().addRecipe(new CentrifugeRecipe("dustSapphire*4", "tinyDustTitanium*250", "tinyDustCrystal*10"));
		
		new CentrifugeRegistry().addRecipe(new CentrifugeRecipe("sand", "dustSilicium*75"));
//		
//		GameRegistry.addRecipe(new ShapedOreRecipe(magmatiteDef.ingot, new Object[] {
//				"LLL",
//				"LDL",
//				"LLL",
//				'L', Items.lava_bucket,
//				'D', "gemDiamond"
//		}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(bronzeDef.dust, 4), "dustTin", "dustCopper", "dustCopper", "dustCopper"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(crystalDef.dust, "dustRuby", "dustSapphire", "dustAmethyst", "dustPeridot", "dustGarnet"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(diamondDef.dust, new Object[] {
//				"LSL",
//				"SDS",
//				"LSL",
//				'L', Items.water_bucket,
//				'D', "gemDiamond",
//				'S', "sand"
//		}));
//		GameRegistry.addRecipe(new ShapedOreRecipe(diamondDef.dust, new Object[] {
//				"SLS",
//				"LDL",
//				"SLS",
//				'L', Items.water_bucket,
//				'D', "gemDiamond",
//				'S', "sand"
//		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(forge, new Object[] {
				"IGI",
				"SMS",
				"S S",
				'I', "dustCrystal",
				'G', "ingotGold",
				'S', "stone",
				'M', "gemMagmatite"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(grinder, new Object[] {
				"IRI",
				"BDB",
				"SSS",
				'I', "ingotIron",
				'R', "dustRedstone",
				'B', "blockIron",
				'D', "dustDiamond",
				'S', "stone"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crystalTube, 16), new Object[] {
			"C C",
			"C C",
			" C ",
			'C', "gemCrystal"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(crystallizer, new Object[] {
			"I I",
			"CWC",
			"CCC",
			'I', "ingotIron",
			'C', Blocks.clay,
			'W', Items.water_bucket
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(centrifuge, new Object[] {
			"GSG",
			"AIA",
			"OOO",
			'G', "blockGlass",
			'S', "gemSilicon",
			'A', "ingotGold",
			'I', "blockIron",
			'O', Blocks.obsidian
		}));
	}
	
	@EventHandler
	public void postInit (FMLPostInitializationEvent e) {
		if (Loader.isModLoaded("tconstruct"))
			TinkersPlugin.materialRenderInfo();
	}
	
}
