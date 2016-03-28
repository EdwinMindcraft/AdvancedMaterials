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

public class AlloyForgeRecipeCategory implements IRecipeCategory {
	
	IDrawableStatic background;
	
	public AlloyForgeRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/jei/alloyforge.png"), 0, 0, 128, 18*2);
		
	}

	@Override
	public String getUid() {
		return "advmat.alloyrecipe";
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
		if (!(recipeWrapper instanceof AlloyForgeRecipeWrapper))
			return;
		AlloyForgeRecipeWrapper wrapper = (AlloyForgeRecipeWrapper)recipeWrapper;
		recipeLayout.getItemStacks().init(0, true, 0, 0);
		recipeLayout.getItemStacks().init(1, true, 18, 0);
		recipeLayout.getItemStacks().init(2, true, 18 * 2, 0);
		recipeLayout.getItemStacks().init(3, true, 18 * 3, 0);
		recipeLayout.getItemStacks().init(4, true, 0, 18);
		recipeLayout.getItemStacks().init(5, true, 18, 18);
		recipeLayout.getItemStacks().init(6, true, 18 * 2, 18);
		recipeLayout.getItemStacks().init(7, true, 18 * 3, 18);
		recipeLayout.getItemStacks().init(8, false, 18 * 6, 9);
		for (int i = 0; i < wrapper.getInputs().size(); i++) {
			Object o = wrapper.getInputs().get(i);
			if (o instanceof ItemStack)
				recipeLayout.getItemStacks().set(i, (ItemStack)o);
			if (o instanceof Collection)
				recipeLayout.getItemStacks().set(i, (Collection<ItemStack>)o);
		}
		recipeLayout.getItemStacks().set(8, wrapper.getOutputs());
	}

}
