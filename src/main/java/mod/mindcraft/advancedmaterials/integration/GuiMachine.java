package mod.mindcraft.advancedmaterials.integration;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public abstract class GuiMachine<K extends TileEntityMachine<? extends IMachineRecipe, ? extends IRecipeRegistry<? extends IMachineRecipe>>> extends GuiContainer{
	
	protected InventoryPlayer player;
	protected K machine;
	
	public GuiMachine(Container cont, InventoryPlayer player, K machine) {
		super (cont);
		this.machine = machine;
		this.player = player;
	}
	
	public int getScaledProgress(int scale) {
		return (int) ((float)machine.progress / (float)machine.getMaxProgress() * (float)scale);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
	}

}
