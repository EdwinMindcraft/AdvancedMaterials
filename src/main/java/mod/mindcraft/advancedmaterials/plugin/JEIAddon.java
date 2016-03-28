package mod.mindcraft.advancedmaterials.plugin;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mod.mindcraft.advancedmaterials.integration.registry.CentrifugeRegistry;
import mod.mindcraft.advancedmaterials.integration.registry.CrystallizerRegistry;
import mod.mindcraft.advancedmaterials.integration.registry.GrinderRegistry;
import mod.mindcraft.advancedmaterials.inventory.AlloyForgeRegistry;
import mod.mindcraft.advancedmaterials.plugin.jei.AlloyForgeRecipeCategory;
import mod.mindcraft.advancedmaterials.plugin.jei.AlloyForgeRecipeHandler;
import mod.mindcraft.advancedmaterials.plugin.jei.CentrifugeRecipeCategory;
import mod.mindcraft.advancedmaterials.plugin.jei.CentrifugeRecipeHandler;
import mod.mindcraft.advancedmaterials.plugin.jei.CrystallizerRecipeCategory;
import mod.mindcraft.advancedmaterials.plugin.jei.CrystallizerRecipeHandler;
import mod.mindcraft.advancedmaterials.plugin.jei.GrinderRecipeCategory;
import mod.mindcraft.advancedmaterials.plugin.jei.GrinderRecipeHandler;

@JEIPlugin
public class JEIAddon implements IModPlugin{
	
	private IJeiHelpers jeiHelpers;
	
	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
		this.jeiHelpers = jeiHelpers;
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry) {
	}

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipeHandlers(
				new AlloyForgeRecipeHandler(),
				new GrinderRecipeHandler(),
				new CentrifugeRecipeHandler(),
				new CrystallizerRecipeHandler());
		registry.addRecipeCategories(
				new AlloyForgeRecipeCategory(jeiHelpers.getGuiHelper()),
				new GrinderRecipeCategory(jeiHelpers.getGuiHelper()),
				new CentrifugeRecipeCategory(jeiHelpers.getGuiHelper()),
				new CrystallizerRecipeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipes(AlloyForgeRegistry.getRecipes());
		registry.addRecipes(new GrinderRegistry().getRecipes());
		registry.addRecipes(new CentrifugeRegistry().getRecipes());
		registry.addRecipes(new CrystallizerRegistry().getRecipes());
	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

}
