package mod.mindcraft.advancedmaterials.inventory.gui;

import org.lwjgl.opengl.GL11;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.integration.GuiMachine;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerGrinder;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityGrinder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiGrinder extends GuiMachine<TileEntityGrinder> {
	
	
	public GuiGrinder(InventoryPlayer player, TileEntityGrinder grinder) {
		super(new ContainerGrinder(player, grinder), player, grinder);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		int j = width / 2 - xSize / 2;
		int k = height / 2 - ySize / 2;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/grinder.png"));
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		int upgrade = getScaledProgress(24);
		drawTexturedModalRect(j + 79, k + 34, xSize, 0, upgrade + 1, 17);
		Minecraft.getMinecraft().fontRendererObj.drawString(machine.getName(), j + 5, k + 5, 0x666666);
		GL11.glColor3f(1F, 1F, 1F);
	}

}
