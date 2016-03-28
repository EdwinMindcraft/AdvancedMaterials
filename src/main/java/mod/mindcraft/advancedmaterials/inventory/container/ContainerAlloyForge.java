package mod.mindcraft.advancedmaterials.inventory.container;

import mod.mindcraft.advancedmaterials.inventory.slot.SlotOutput;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAlloyForge extends Container {

	public ContainerAlloyForge(InventoryPlayer player, TileEntityForge forge) {
		addSlotToContainer(new Slot(forge, 0, 11, 10));
		addSlotToContainer(new Slot(forge, 1, 62, 10));
		addSlotToContainer(new Slot(forge, 2, 113, 10));
		addSlotToContainer(new Slot(forge, 3, 11, 60));
		addSlotToContainer(new Slot(forge, 4, 113, 60));
		addSlotToContainer(new Slot(forge, 5, 11, 110));
		addSlotToContainer(new Slot(forge, 6, 62, 110));
		addSlotToContainer(new Slot(forge, 7, 113, 110));
		addSlotToContainer(new SlotOutput(forge, 8, 62, 60));

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(player, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + 45));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(player, i1, 8 + i1 * 18, 161 + 45));
        }
	}
	
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 9)
            {
                if (!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 7, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
}
