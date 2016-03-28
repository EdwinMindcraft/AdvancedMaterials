package mod.mindcraft.advancedmaterials.utils;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import mod.mindcraft.advancedmaterials.integration.recipes.CrystallizerRecipe;
import mod.mindcraft.advancedmaterials.integration.recipes.GrinderRecipe;
import mod.mindcraft.advancedmaterials.integration.registry.CrystallizerRegistry;
import mod.mindcraft.advancedmaterials.integration.registry.GrinderRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class MetalDefinition {

	public String oreDict;
	public int color;
	public boolean isGem = false;
	public boolean hasOre = false;
	public int tries = -1;
	public int veinSize = -1;
	public int miningLevel;
	
	public MetalDefinition(int color, String oreDict) {
		this.color = color;
		this.oreDict = oreDict;
	}
	
	public MetalDefinition setGem() {
		isGem = true;
		return this;
	}
	
	public MetalDefinition generateOre(int miningLevel) {
		hasOre = true;
		this.miningLevel = miningLevel;
		return this;
	}
	
	public MetalDefinition generateOre(int miningLevel, int tries, int veinSize) {
		this.tries = tries;
		this.veinSize = veinSize;
		return generateOre(miningLevel);
	}
	
	public void addToOreDict(String name) {
		if (isGem)
			OreDictionary.registerOre("gem" + name, new ItemStack (AdvancedMaterials.ingot, 1, MetalRegistry.getID(this)));			
		else
			OreDictionary.registerOre("ingot" + name, new ItemStack (AdvancedMaterials.ingot, 1, MetalRegistry.getID(this)));
		OreDictionary.registerOre("dust" + name, new ItemStack (AdvancedMaterials.dust, 1, MetalRegistry.getID(this)));
		OreDictionary.registerOre("tinyDust" + name, new ItemStack (AdvancedMaterials.tinyDust, 1, MetalRegistry.getID(this)));
		OreDictionary.registerOre("nugget" + name, new ItemStack (AdvancedMaterials.nugget, 1, MetalRegistry.getID(this)));
		OreDictionary.registerOre("impureDust" + name, new ItemStack (AdvancedMaterials.impureDust, 1, MetalRegistry.getID(this)));
		OreDictionary.registerOre("block" + name, new ItemStack(MetalRegistry.getBlockForID(MetalRegistry.getID(this)), 1, (int) (MetalRegistry.getID(this) - (Math.floor((float)MetalRegistry.getID(this) / 16F)))));
		if (hasOre) {
			int oreID = MetalRegistry.matOresMap().get(MetalRegistry.getID(this));
			OreDictionary.registerOre("ore" + name, new ItemStack(MetalRegistry.getOreForID(MetalRegistry.getID(this)), 1, oreID % 16));
		}
		addRecipes(name);
	}
	
	public void addRecipes(String name) {
		String pre = MetalRegistry.array().get(MetalRegistry.getID(this)).isGem ? "gem" : "ingot";
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(AdvancedMaterials.nugget, 9, MetalRegistry.getID(this)), pre + name));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(this)), new Object[]{
			"XXX",
			"XXX",
			"XXX",
			'X', "nugget" + name
		}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(AdvancedMaterials.tinyDust, 4, MetalRegistry.getID(this)), "dust" + name));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedMaterials.dust, 1, MetalRegistry.getID(this)), new Object[] {
				"XX",
				"XX",
				'X', "tinyDust" + name
		}));
		new GrinderRegistry().addRecipe(new GrinderRecipe(pre + name, "dust" + name + "*100"));
		if (hasOre)
			new GrinderRegistry().addRecipe(new GrinderRecipe("ore" + name, "impureDust" + name + "*200", "cobblestone*50"));	
		if (isGem) {
			new CrystallizerRegistry().addRecipe(new CrystallizerRecipe("dust" + name, "gem" + name + "*100"));
			new CrystallizerRegistry().addRecipe(new CrystallizerRecipe("impureDust" + name, "gem" + name + "*100"));
		} else {
			GameRegistry.addSmelting(new ItemStack(AdvancedMaterials.impureDust, 1, MetalRegistry.getID(this)), new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(this)), 0.7F);
			GameRegistry.addSmelting(new ItemStack(AdvancedMaterials.dust, 1, MetalRegistry.getID(this)), new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(this)), 1.0F);
		}
	}
}
