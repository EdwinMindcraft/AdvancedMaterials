package mod.mindcraft.advancedmaterials.integration.registry;

import java.util.ArrayList;

import mod.mindcraft.advancedmaterials.integration.component.ItemNuclearReactorComponent;
import mod.mindcraft.advancedmaterials.integration.component.ItemNuclearReactorFluidComponent;
import mod.mindcraft.advancedmaterials.integration.component.NuclearReactorComponent;
import mod.mindcraft.advancedmaterials.utils.ItemModelLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NuclearReactorComponentRegistry {
	private static final ArrayList<ItemNuclearReactorComponent> components = new ArrayList<ItemNuclearReactorComponent>();
	private static final ArrayList<ModelResourceLocation> textures = new ArrayList<ModelResourceLocation>();
	
	public static void defineComponent(NuclearReactorComponent component, String unlocalizedName, String registryName, ModelResourceLocation texture) {
		ItemNuclearReactorComponent item = new ItemNuclearReactorComponent(component, unlocalizedName);
		GameRegistry.registerItem(item, "component.nuclear." + registryName);
		ModelBakery.registerItemVariants(item, texture);
		components.add(item);
		textures.add(texture);
	}
	
	public static void defineFluidComponent (NuclearReactorComponent component, FluidStack fluid, String unlocalizedName, String registryName, ModelResourceLocation texture) {
		ItemNuclearReactorComponent item = new ItemNuclearReactorFluidComponent(component, unlocalizedName, fluid);
		GameRegistry.registerItem(item, "component.nuclear.fluid" + registryName);
		ModelBakery.registerItemVariants(item, texture);
		components.add(item);
		textures.add(texture);
	}
	
	public static void registerTextures() {
		for (int i = 0; i < components.size(); i++) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(components.get(i), new ItemModelLocation(textures.get(i)));
		}
	}
}
