package mod.mindcraft.advancedmaterials.inventory.container;

import mod.mindcraft.advancedmaterials.integration.ContainerMachine;
import mod.mindcraft.advancedmaterials.inventory.slot.SlotOutput;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityCrystallizer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCrystallizer extends ContainerMachine<TileEntityCrystallizer>{

	public ContainerCrystallizer(InventoryPlayer player, TileEntityCrystallizer machine) {
		super (player, machine);
		addSlotToContainer(new Slot(machine, 0, 56, 35));
		addSlotToContainer(new SlotOutput(machine, 1, 112, 35));

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
