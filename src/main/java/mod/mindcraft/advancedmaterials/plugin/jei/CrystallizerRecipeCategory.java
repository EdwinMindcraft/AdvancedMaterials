package mod.mindcraft.advancedmaterials.plugin.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class CrystallizerRecipeCategory implements IRecipeCategory {
	
	IDrawableStatic background;
	
	public CrystallizerRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/jei/crystallizer.png"), 0, 0, 128, 18);
		
	}

	@Override
	public String getUid() {
		return "advmat.crystallizerrecipe";
	}

	@Override
	public String getTitle() {
		return StatCollector.translateToLocal(getUid());
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		
	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		if (!(recipeWrapper instanceof CrystallizerRecipeWrapper))
			return;
		CrystallizerRecipeWrapper wrapper = (CrystallizerRecipeWrapper)recipeWrapper;
		recipeLayout.getItemStacks().init(0, true, 0, 0);
		recipeLayout.getItemStacks().init(1, false, 56, 0);
		recipeLayout.getItemStacks().set(0, wrapper.getInputs());
		recipeLayout.getItemStacks().set(1, wrapper.getOutputs());
	}

}
