package mod.mindcraft.advancedmaterials.plugin.jei;

import java.util.Collection;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GrinderRecipeCategory implements IRecipeCategory {
	IDrawableStatic background;
	
	public GrinderRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/jei/grinder.png"), 0, 0, 128, 36);
	}

	@Override
	public String getUid() {
		return "advmat.grinder";
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		if (!(recipeWrapper instanceof GrinderRecipeWrapper))
			return;
		GrinderRecipeWrapper wrapper = (GrinderRecipeWrapper)recipeWrapper;
		recipeLayout.getItemStacks().init(0, true, 0, 10);
		recipeLayout.getItemStacks().init(1, false, 56, 0);
		recipeLayout.getItemStacks().init(2, false, 56, 18);
		recipeLayout.getItemStacks().set(0, wrapper.getInputs());
		for (int i = 0; i < Math.min(2, wrapper.getOutputs().size()); i++) {
			if (wrapper.getOutputs().get(i) != null) {
				if (wrapper.getOutputs().get(i) instanceof Collection)
					recipeLayout.getItemStacks().set(1+i, (Collection)wrapper.getOutputs().get(i));
				else if (wrapper.getOutputs().get(i) instanceof ItemStack)
					recipeLayout.getItemStacks().set(1+i, (ItemStack)wrapper.getOutputs().get(i));
			}
		}
	}

}
