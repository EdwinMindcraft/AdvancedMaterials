package mod.mindcraft.advancedmaterials.inventory.gui;

import org.lwjgl.opengl.GL11;

import slimeknights.tconstruct.library.client.RenderUtil;

import com.google.common.collect.Lists;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerNuclearReactor;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityNuclearReactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

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
		int energy = (int) ((float)reactor.energy / reactor.getMaxEnergyStored(EnumFacing.DOWN) * 122);
		drawTexturedModalRect(j+6, k + 122 - energy + 6, 222, 122 - energy, 14, energy);
		if (reactor.storedFluid != null) {
			GL11.glPushMatrix();
			int plasma = (int) ((float)reactor.storedFluid.amount / reactor.getCapacity() * 122);
			GL11.glTranslatef(196, 6 + 122 - plasma, 0);
			TextureAtlasSprite fluidSprite = mc.getTextureMapBlocks().getAtlasSprite(reactor.storedFluid.getFluid().getStill(reactor.storedFluid).toString());
			RenderUtil.setColorRGBA(reactor.storedFluid.getFluid().getColor(reactor.storedFluid));
		    Tessellator tessellator = Tessellator.getInstance();
		    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		    worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		    mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

			float u1 = fluidSprite.getMinU();
			float v1 = fluidSprite.getMinV();

			int height = plasma;
			int i = k;
			do {
				int renderHeight = Math.min(fluidSprite.getIconHeight(), height);
				height -= renderHeight;

				float v2 = fluidSprite.getInterpolatedV((renderHeight * 16F) / (float) fluidSprite.getIconHeight());
				int width = 6;
				int x2 = j;
				int width2 = width;
				do {
					int renderWidth = Math.min(fluidSprite.getIconWidth(), width2);
					width2 -= renderWidth;

					float u2 = fluidSprite.getInterpolatedU((16f * renderWidth)
							/ (float) fluidSprite.getIconWidth());

					worldrenderer.pos(x2, i, zLevel).tex(u1, v1).endVertex();
					worldrenderer.pos(x2, i + renderHeight, zLevel).tex(u1, v2)
							.endVertex();
					worldrenderer
							.pos(x2 + renderWidth, i + renderHeight, zLevel)
							.tex(u2, v2).endVertex();
					worldrenderer.pos(x2 + renderWidth, i, zLevel).tex(u2, v1)
							.endVertex();

					x2 += renderWidth;
				} while (width2 > 0);

				i += renderHeight;
			} while (height > 0);

		    tessellator.draw();	
		    GL11.glPopMatrix();
		}
		//System.out.println(reactor.hullHeat - reactor.prevTemp);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int j = width / 2 - xSize / 2;
		int k = height / 2 - ySize / 2;
		Minecraft.getMinecraft().fontRendererObj.drawString("Nuclear Reactor", (int) (ySize / 2 - "Nuclear Reactor".length() * 2.75), 5, 0x666666);
		if (mouseX > j + 204 && mouseX < j + 210 && mouseY > k + 6 && mouseY < k + 128)
			drawHoveringText(Lists.newArrayList("§fHeat : §c" + (float)reactor.hullHeat / 10 + "°C", "§fExplosion at §c10000°C", (reactor.hullHeat - reactor.prevTemp > 0 ? "§c" : "§b") + (float)(reactor.hullHeat - reactor.prevTemp) / 10 + " °C §fper tick"), mouseX - j, mouseY - k);
		if (mouseX > j + 196 && mouseX < j + 204 && mouseY > k + 6 && mouseY < k + 128) {
			if (reactor.storedFluid == null)
				drawHoveringText(Lists.newArrayList("§fEmpty"), mouseX - j, mouseY - k);
			else
				drawHoveringText(Lists.newArrayList("§f" + reactor.storedFluid.getLocalizedName(), "§r" + reactor.storedFluid.amount + " mB"), mouseX - j, mouseY - k);
		}
		if (mouseX > j + 6 && mouseX < j + 20 && mouseY > k + 6 && mouseY < k + 128)
			drawHoveringText(Lists.newArrayList("§fEnergy : §4" + reactor.energy + "§f RF", "§4" + (reactor.energy - reactor.prevEnergy) + "§f RF/t"), mouseX - j, mouseY - k);
	}
}
