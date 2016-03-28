package mod.mindcraft.advancedmaterials.inventory.gui;

import com.google.common.collect.Lists;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerNuclearReactor;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityNuclearReactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiNuclearReactor extends GuiContainer {
	
	public TileEntityNuclearReactor reactor;

	public GuiNuclearReactor(InventoryPlayer inventory, TileEntityNuclearReactor tileEntity) {
		super(new ContainerNuclearReactor(inventory, tileEntity));
		ySize += 56;
		xSize += 40;
		this.reactor = tileEntity;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int j = width / 2 - xSize / 2;
		int k = height / 2 - ySize / 2;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/nuclearreactor.png"));
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		int val = (int) ((float)reactor.hullHeat / 100000F * 122F);
		drawTexturedModalRect(j+204, k + 122 - val + 6, 216, 122 - val, 6, val);
		drawTexturedModalRect(j+204, k + 122 - val + 6, 222, 122 - val, 6, val);
		Minecraft.getMinecraft().fontRendererObj.drawString("Nuclear Reactor", (int) (width / 2 - "Nuclear Reactor".length() * 2.75), k + 5, 0x666666);
		if (mouseX > j + 204 && mouseX < j + 210)
			drawHoveringText(Lists.newArrayList("§fHeat : §c" + (float)reactor.hullHeat / 10 + "°C", "§fExplosion at §c10000°C"), mouseX, mouseY);
	}
}
