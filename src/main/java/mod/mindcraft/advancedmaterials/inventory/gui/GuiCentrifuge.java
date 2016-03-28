package mod.mindcraft.advancedmaterials.inventory.gui;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.integration.GuiMachine;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerCentrifuge;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCentrifuge;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiCentrifuge extends GuiMachine<TileEntityCentrifuge> {

	TileEntityCentrifuge centrifuge;
	
	public GuiCentrifuge(InventoryPlayer player, TileEntityCentrifuge centrifuge) {
		super(new ContainerCentrifuge(player, centrifuge), player, centrifuge);
		this.centrifuge = centrifuge;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		int j = width / 2 - xSize / 2;
		int k = height / 2 - ySize / 2;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/centrifuge.png"));
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		int upgrade = getScaledProgress(24);
		drawTexturedModalRect(j + 65, k + 34, xSize, 0, upgrade + 1, 17);
		Minecraft.getMinecraft().fontRendererObj.drawString(centrifuge.getName(), j+10, k+10, 0x000000);
		GL11.glColor3f(1F, 1F, 1F);
	}
}
