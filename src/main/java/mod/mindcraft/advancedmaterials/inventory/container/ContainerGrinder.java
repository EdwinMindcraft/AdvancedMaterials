package mod.mindcraft.advancedmaterials.inventory.container;

import mod.mindcraft.advancedmaterials.integration.ContainerMachine;
import mod.mindcraft.advancedmaterials.inventory.slot.SlotOutput;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityGrinder;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerGrinder extends ContainerMachine<TileEntityGrinder>{

	public ContainerGrinder(InventoryPlayer player, TileEntityGrinder grinder) {
		super (player, grinder);
		addSlotToContainer(new Slot(grinder, 0, 56, 35));
		addSlotToContainer(new SlotOutput(grinder, 1, 112, 25));
		addSlotToContainer(new SlotOutput(grinder, 2, 112, 43));

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(player, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 - 19));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(player, i1, 8 + i1 * 18, 161 - 19));
        }
	}
	
}
