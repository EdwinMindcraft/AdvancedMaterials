package mod.mindcraft.advancedmaterials.utils;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.items.ItemArmorColored;
import mod.mindcraft.advancedmaterials.items.ItemAxeColored;
import mod.mindcraft.advancedmaterials.items.ItemHoeColored;
import mod.mindcraft.advancedmaterials.items.ItemPickaxeColored;
import mod.mindcraft.advancedmaterials.items.ItemSpadeColored;
import mod.mindcraft.advancedmaterials.items.ItemSwordColored;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ToolDefinition {
	
	public final Item sword;
	public final Item pickaxe;
	public final Item axe;
	public final Item shovel;
	public final Item hoe;
	public final Item helmet;
	public final Item chestplate;
	public final Item leggings;
	public final Item boots;
	
	
	public ToolDefinition(ToolMaterial material, ArmorMaterial armor, int color) {
		sword = new ItemSwordColored(material, color).setUnlocalizedName(material.name().toLowerCase() + ".sword").setCreativeTab(AdvancedMaterials.tabAdvMat);
		pickaxe = new ItemPickaxeColored(material, color).setUnlocalizedName(material.name().toLowerCase() + ".pickaxe").setCreativeTab(AdvancedMaterials.tabAdvMat);
		axe = new ItemAxeColored(material, color).setUnlocalizedName(material.name().toLowerCase() + ".axe").setCreativeTab(AdvancedMaterials.tabAdvMat);
		shovel = new ItemSpadeColored(material, color).setUnlocalizedName(material.name().toLowerCase() + ".shovel").setCreativeTab(AdvancedMaterials.tabAdvMat);
		hoe = new ItemHoeColored(material, color).setUnlocalizedName(material.name().toLowerCase() + ".hoe").setCreativeTab(AdvancedMaterials.tabAdvMat);
		helmet = new ItemArmorColored(armor, 1, 0, color).setUnlocalizedName(material.name().toLowerCase() + ".helmet").setCreativeTab(AdvancedMaterials.tabAdvMat);
		chestplate = new ItemArmorColored(armor, 1, 1, color).setUnlocalizedName(material.name().toLowerCase() + ".chestplate").setCreativeTab(AdvancedMaterials.tabAdvMat);
		leggings = new ItemArmorColored(armor, 2, 2, color).setUnlocalizedName(material.name().toLowerCase() + ".leggings").setCreativeTab(AdvancedMaterials.tabAdvMat);
		boots = new ItemArmorColored(armor, 1, 3, color).setUnlocalizedName(material.name().toLowerCase() + ".boots").setCreativeTab(AdvancedMaterials.tabAdvMat);
		
		GameRegistry.registerItem(sword, material.name().toLowerCase() + ".sword");
		GameRegistry.registerItem(pickaxe, material.name().toLowerCase() + ".pickaxe");
		GameRegistry.registerItem(axe, material.name().toLowerCase() + ".axe");
		GameRegistry.registerItem(shovel, material.name().toLowerCase() + ".shovel");
		GameRegistry.registerItem(hoe, material.name().toLowerCase() + ".hoe");
		GameRegistry.registerItem(helmet, material.name().toLowerCase() + ".helmet");
		GameRegistry.registerItem(chestplate, material.name().toLowerCase() + ".chestplate");
		GameRegistry.registerItem(leggings, material.name().toLowerCase() + ".leggings");
		GameRegistry.registerItem(boots, material.name().toLowerCase() + ".boots");
	}
	
	public void registerTextures() {
		ModelResourceLocation swordLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":sword", "inventory");
		ModelResourceLocation pickLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":pickaxe", "inventory");
		ModelResourceLocation axeLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":axe", "inventory");
		ModelResourceLocation spadeLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":shovel", "inventory");
		ModelResourceLocation hoeLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":hoe", "inventory");
		ModelResourceLocation helmLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":helmet", "inventory");
		ModelResourceLocation chestLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":chestplate", "inventory");
		ModelResourceLocation legsLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":leggings", "inventory");
		ModelResourceLocation bootsLoc = new ModelResourceLocation(AdvancedMaterials.MODID + ":boots", "inventory");
		
		ModelBakery.registerItemVariants(sword, swordLoc);
		ModelBakery.registerItemVariants(pickaxe, pickLoc);
		ModelBakery.registerItemVariants(axe, axeLoc);
		ModelBakery.registerItemVariants(shovel, spadeLoc);
		ModelBakery.registerItemVariants(hoe, hoeLoc);
		ModelBakery.registerItemVariants(helmet, helmLoc);
		ModelBakery.registerItemVariants(chestplate, chestLoc);
		ModelBakery.registerItemVariants(leggings, legsLoc);
		ModelBakery.registerItemVariants(boots, bootsLoc);
		
		RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
		renderer.getItemModelMesher().register(sword, 0, swordLoc);
		renderer.getItemModelMesher().register(pickaxe, 0, pickLoc);
		renderer.getItemModelMesher().register(axe, 0, axeLoc);
		renderer.getItemModelMesher().register(shovel, 0, spadeLoc);
		renderer.getItemModelMesher().register(hoe, 0, hoeLoc);
		renderer.getItemModelMesher().register(helmet, 0, helmLoc);
		renderer.getItemModelMesher().register(chestplate, 0, chestLoc);
		renderer.getItemModelMesher().register(leggings, 0, legsLoc);
		renderer.getItemModelMesher().register(boots, 0, bootsLoc);
	}
	
	public void registerRecipes(MetalDefinition definition) {
		if (definition.isGem) {
			GameRegistry.addRecipe(new ShapedOreRecipe(sword, new Object[] {
					"X",
					"X",
					"S",
					'X', "gem" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(pickaxe, new Object[] {
					"XXX",
					" S ",
					" S ",
					'X', "gem" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(axe, new Object[] {
					"XX",
					"SX",
					"S ",
					'X', "gem" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(shovel, new Object[] {
					"X",
					"S",
					"S",
					'X', "gem" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(hoe, new Object[] {
					"XX",
					" S",
					" S",
					'X', "gem" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(helmet, new Object[] {
					"XXX",
					"X X",
					'X', "gem" + definition.oreDict,
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(chestplate, new Object[] {
					"X X",
					"XXX",
					"XXX",
					'X', "gem" + definition.oreDict,
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(leggings, new Object[] {
					"XXX",
					"X X",
					"X X",
					'X', "gem" + definition.oreDict,
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(boots, new Object[] {
					"X X",
					"X X",
					'X', "gem" + definition.oreDict,
			}));
		}
		else {
			GameRegistry.addRecipe(new ShapedOreRecipe(sword, new Object[] {
					"X",
					"X",
					"S",
					'X', "ingot" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(pickaxe, new Object[] {
					"XXX",
					" S ",
					" S ",
					'X', "ingot" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(axe, new Object[] {
					"XX",
					"SX",
					"S ",
					'X', "ingot" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(axe, new Object[] {
					"XX",
					"XS",
					" S",
					'X', "ingot" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(shovel, new Object[] {
					"X",
					"S",
					"S",
					'X', "ingot" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(hoe, new Object[] {
					"XX",
					" S",
					" S",
					'X', "ingot" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(hoe, new Object[] {
					"XX",
					"S ",
					"S ",
					'X', "ingot" + definition.oreDict,
					'S', "stickWood"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(helmet, new Object[] {
					"XXX",
					"X X",
					'X', "ingot" + definition.oreDict,
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(chestplate, new Object[] {
					"X X",
					"XXX",
					"XXX",
					'X', "ingot" + definition.oreDict,
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(leggings, new Object[] {
					"XXX",
					"X X",
					"X X",
					'X', "ingot" + definition.oreDict,
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(boots, new Object[] {
					"X X",
					"X X",
					'X', "ingot" + definition.oreDict,
			}));
		}

	}
}
