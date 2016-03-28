package mod.mindcraft.advancedmaterials.inventory.gui;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.integration.GuiMachine;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerCrystallizer;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCrystallizer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCrystallizer extends GuiMachine<TileEntityCrystallizer> {
	
	public GuiCrystallizer(InventoryPlayer player, TileEntityCrystallizer machine) {
		super(new ContainerCrystallizer(player, machine), player, machine);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		int j = width / 2 - xSize / 2;
		int k = height / 2 - ySize / 2;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/crystallizer.png"));
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		int upgrade = getScaledProgress(24);
		drawTexturedModalRect(j + 79, k + 34, xSize, 0, upgrade + 1, 17);
	}

}
