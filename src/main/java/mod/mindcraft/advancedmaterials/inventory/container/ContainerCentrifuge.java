package mod.mindcraft.advancedmaterials.inventory.container;

import mod.mindcraft.advancedmaterials.integration.ContainerMachine;
import mod.mindcraft.advancedmaterials.inventory.slot.SlotOutput;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCentrifuge;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCentrifuge extends ContainerMachine<TileEntityCentrifuge>{

	public ContainerCentrifuge(InventoryPlayer player, TileEntityCentrifuge machine) {
		super(player, machine);
		addSlotToContainer(new Slot(machine, 0, 42, 35));
		addSlotToContainer(new SlotOutput(machine, 1, 98, 25));
		addSlotToContainer(new SlotOutput(machine, 2, 98, 43));
		addSlotToContainer(new SlotOutput(machine, 3, 116, 25));
		addSlotToContainer(new SlotOutput(machine, 4, 116, 43));

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
