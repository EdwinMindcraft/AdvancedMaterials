package mod.mindcraft.advancedmaterials.inventory.gui;

import com.google.common.collect.Lists;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import mod.mindcraft.advancedmaterials.inventory.container.ContainerNuclearReactor;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityNuclearReactor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
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
		int energy = (int) ((float)reactor.energy / reactor.getMaxEnergyStored(EnumFacing.DOWN) * 122);
		drawTexturedModalRect(j+6, k + 122 - energy + 6, 222, 122 - energy, 14, energy);
		Minecraft.getMinecraft().fontRendererObj.drawString("Nuclear Reactor", (int) (width / 2 - "Nuclear Reactor".length() * 2.75), k + 5, 0x666666);
		//System.out.println(reactor.hullHeat - reactor.prevTemp);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int j = width / 2 - xSize / 2;
		int k = height / 2 - ySize / 2;
		if (mouseX > j + 204 && mouseX < j + 210 && mouseY > k + 6 && mouseY < k + 128)
			drawHoveringText(Lists.newArrayList("§fHeat : §c" + (float)reactor.hullHeat / 10 + "°C", "§fExplosion at §c10000°C", (reactor.hullHeat - reactor.prevTemp > 0 ? "§c" : "§b") + (reactor.hullHeat - reactor.prevTemp) + " §fHeat per tick"), mouseX - j, mouseY - k);
		if (mouseX > j + 6 && mouseX < j + 20 && mouseY > k + 6 && mouseY < k + 128)
			drawHoveringText(Lists.newArrayList("§fEnergy : §4" + reactor.energy + "§f RF", "§4" + (reactor.energy - reactor.prevEnergy) + "§f RF/t"), mouseX - j, mouseY - k);
	}
}
