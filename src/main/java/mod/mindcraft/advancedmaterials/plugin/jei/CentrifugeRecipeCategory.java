package mod.mindcraft.advancedmaterials.plugin.jei;

import java.util.ArrayList;

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

public class CentrifugeRecipeCategory implements IRecipeCategory {
	
	IDrawableStatic background;

	public CentrifugeRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/jei/centrifuge.png"), 0, 0, 128, 18*2);
	}

	@Override
	public String getUid() {
		return "advmat.centrifuge";
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
		if (!(recipeWrapper instanceof CentrifugeRecipeWrapper))
			return;
		CentrifugeRecipeWrapper wrapper = (CentrifugeRecipeWrapper)recipeWrapper;
		recipeLayout.getItemStacks().init(0, true, 0, 10);
		recipeLayout.getItemStacks().init(1, false, 56, 0);
		recipeLayout.getItemStacks().init(2, false, 56, 18);
		recipeLayout.getItemStacks().init(3, false, 74, 0);
		recipeLayout.getItemStacks().init(4, false, 74, 18);
		recipeLayout.getItemStacks().set(0, recipeWrapper.getInputs());
		for (int i = 0; i < Math.min(4, wrapper.getOutputs().size()); i++) {
			Object obj = wrapper.getOutputs().get(i);
			if (obj instanceof ArrayList)
				recipeLayout.getItemStacks().set(i + 1, wrapper.getOutputs().size() > i ? (ArrayList<ItemStack>) obj : null);
			else if (obj instanceof ItemStack)
				recipeLayout.getItemStacks().set(i + 1, wrapper.getOutputs().size() > i ? (ItemStack) obj : null);
		}
	}

}
