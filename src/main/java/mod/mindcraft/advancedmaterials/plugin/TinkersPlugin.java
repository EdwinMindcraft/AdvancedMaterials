package mod.mindcraft.advancedmaterials.plugin;

import java.util.ArrayList;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.integration.MetalRegistry;
import mod.mindcraft.advancedmaterials.plugin.trait.TraitCoalFueled;
import mod.mindcraft.advancedmaterials.plugin.trait.TraitPolishing;
import mod.mindcraft.advancedmaterials.plugin.trait.TraitSharpening;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.tools.TinkerMaterials;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;

public class TinkersPlugin {
	public static final Material sapphire = new Material("sapphire", 0x0000ff);
	public static final Material ruby = new Material("ruby", 0xff0000);
	public static final Material amethyst = new Material("amethyst", 0xff00ff);
	public static final Material peridot = new Material("peridot", 0x99ff00);
	public static final Material garnet = new Material("garnet", 0xff5500);
	public static final Material crystal = new Material("crystal", 0xffffff);
	public static final Material magmatite = new Material("magmatite", 0xff7400);
	
	public static final Material titanium = new Material("titanium", 0xdab3ff);
	
	public static final ArrayList<MaterialIntegration> integrationList = new ArrayList<MaterialIntegration>();
	
	public static final AbstractTrait polishing = new TraitPolishing();
	public static final AbstractTrait sharpening = new TraitSharpening();
	public static final AbstractTrait coalfueled = new TraitCoalFueled(1);
	public static final AbstractTrait coalfueled2 = new TraitCoalFueled(2);
		
	@SideOnly(Side.CLIENT)
	public static void materialRenderInfo () {
		sapphire.setRenderInfo(new MaterialRenderInfo.Metal(0x0000ff, 0.25F, 0.25F, -0.05F));
		ruby.setRenderInfo(new MaterialRenderInfo.Metal(0xff0000, 0.25F, 0.25F, -0.05F));
		amethyst.setRenderInfo(new MaterialRenderInfo.Metal(0xff00ff, 0.25F, 0.25F, -0.05F));
		peridot.setRenderInfo(new MaterialRenderInfo.Metal(0x99ff00, 0.25F, 0.25F, -0.05F));
		garnet.setRenderInfo(new MaterialRenderInfo.Metal(0xff5500, 0.25F, 0.25F, -0.05F));
		crystal.setRenderInfo(new MaterialRenderInfo.Metal(0xffffff, 0.25F, 0.25F, -0.05F));
		magmatite.setRenderInfo(new MaterialRenderInfo.Metal(0xff7400, 0.25F, 0.25F, -0.05F));
		titanium.setRenderInfo(new MaterialRenderInfo.Metal(0xdab3ff, 0.25F, 0.25F, -0.05F));
	}
	
	public static void defineMaterials () {
		sapphire.setCraftable(true);
		sapphire.setRepresentativeItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Sapphire"))));
		sapphire.addItem("gemSapphire", 1, Material.VALUE_Ingot);
		sapphire.addTrait(polishing);
		
		ruby.setCraftable(true);
		ruby.setRepresentativeItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Ruby"))));
		ruby.addItem("gemRuby", 1, Material.VALUE_Ingot);
		ruby.addTrait(polishing);
		
		amethyst.setCraftable(true);
		amethyst.setRepresentativeItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Amethyst"))));
		amethyst.addItem("gemAmethyst", 1, Material.VALUE_Ingot);
		amethyst.addTrait(polishing);
		
		peridot.setCraftable(true);
		peridot.setRepresentativeItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Peridot"))));
		peridot.addItem("gemPeridot", 1, Material.VALUE_Ingot);
		peridot.addTrait(polishing);
		
		garnet.setCraftable(true);
		garnet.setRepresentativeItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Garnet"))));
		garnet.addItem("gemGarnet", 1, Material.VALUE_Ingot);
		garnet.addTrait(polishing);
		
		magmatite.setCraftable(true);
		magmatite.setRepresentativeItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Magmatite"))));
		magmatite.addItem("gemMagmatite", 1, Material.VALUE_Ingot);
		magmatite.addTrait(coalfueled);
		magmatite.addTrait(coalfueled2, HeadMaterialStats.TYPE);
		magmatite.addTrait(TinkerMaterials.superheat, HeadMaterialStats.TYPE);
		
		titanium.setCastable(true);
		titanium.setFluid(FluidRegistry.getFluid("molten.titanium"));
		titanium.setRepresentativeItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Titanium"))));
		titanium.addItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Titanium"))), 1, Material.VALUE_Ingot);
		titanium.addTrait(sharpening);
		
		crystal.setCraftable(true);
		crystal.setRepresentativeItem(new ItemStack(AdvancedMaterials.ingot, 1, MetalRegistry.getID(MetalRegistry.getFromName("Crystal"))));
		crystal.addItem("gemCrystal", 1, Material.VALUE_Ingot);
		crystal.addTrait(sharpening);
		
		integrate("gemRuby", ruby, null, "Ruby");
		integrate("gemSapphire", sapphire, null, "Sapphire");
		integrate("gemAmethyst", amethyst, null, "Amethyst");
		integrate("gemPeridot", peridot, null, "Peridot");
		integrate("gemGarnet", garnet, null, "Garnet");
		integrate("gemMagmatite", magmatite, null, "Magmatite");
		integrate("gemCrystal", crystal, null, "Crystal");
		integrate("ingotTitanium", titanium, FluidRegistry.getFluid("molten.titanium"), "Titanium");
		
		TinkerRegistry.addMaterialStats(sapphire, new HeadMaterialStats(450, 7.4F, 5.4F, 3), new ExtraMaterialStats(-150), new HandleMaterialStats(1.25F, -100));
		TinkerRegistry.addMaterialStats(ruby, new HeadMaterialStats(450, 5.4F, 7.4F, 3), new ExtraMaterialStats(150), new HandleMaterialStats(0.75F, 100));
		TinkerRegistry.addMaterialStats(amethyst, new HeadMaterialStats(450, 6.4F, 6.4F, 3), new ExtraMaterialStats(75), new HandleMaterialStats(1F, 50));
		TinkerRegistry.addMaterialStats(peridot, new HeadMaterialStats(650, 5.4F, 5.4F, 3), new ExtraMaterialStats(300), new HandleMaterialStats(0.8F, 250));
		TinkerRegistry.addMaterialStats(garnet, new HeadMaterialStats(550, 5.9F, 5.9F, 3), new ExtraMaterialStats(100), new HandleMaterialStats(1.1F, -100));
		TinkerRegistry.addMaterialStats(magmatite, new HeadMaterialStats(650, 3.41F, 9.5F, 3), new ExtraMaterialStats(100), new HandleMaterialStats(1F, 50));
		TinkerRegistry.addMaterialStats(crystal, new HeadMaterialStats(750, 8.4F, 9.5F, 4), new ExtraMaterialStats(300), new HandleMaterialStats(0.90F, 200));
		TinkerRegistry.addMaterialStats(titanium, new HeadMaterialStats(850, 8.95F, 7.3F, 4), new ExtraMaterialStats(350), new HandleMaterialStats(0.85F, 250));
		
		UnmodifiableIterator<MaterialIntegration> iter = ImmutableList.copyOf(integrationList).iterator();
		while (iter.hasNext()) {
			MaterialIntegration integration = iter.next();
			integration.integrate();
			integration.integrateRecipes();
			
		}
	}
	
	private static void integrate(String oreRequirement, Material material, Fluid fluid, String oreSuffix) {
		integrationList.add(new MaterialIntegration(oreRequirement, material, fluid, oreSuffix));
	}
}
