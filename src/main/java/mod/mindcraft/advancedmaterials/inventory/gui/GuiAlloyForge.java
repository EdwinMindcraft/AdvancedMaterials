package mod.mindcraft.advancedmaterials.inventory.gui;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerAlloyForge;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiAlloyForge extends GuiContainer {
	
	private TileEntityForge forge;
	
	public GuiAlloyForge(InventoryPlayer player, TileEntityForge forge) {
		super(new ContainerAlloyForge(player, forge));
		xSize = 176;
		ySize = 230;
		this.forge = forge;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int j = width / 2 - xSize / 2;
		int k = height / 2 - ySize / 2;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(AdvancedMaterials.MODID, "textures/guis/alloyforge.png"));
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		//System.out.println(forge.getField(0));
		int upgrade = MathHelper.clamp_int((int)(((float)forge.getField(0) * 114F) / 200F), 0, 114);
		//System.out.println(114 - upgrade);
		drawTexturedModalRect(j + 149, k + 126 - upgrade, 178, 116 - upgrade, 16, 114);
	}

}
