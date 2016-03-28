package mod.mindcraft.advancedmaterials.inventory.container;

import mod.mindcraft.advancedmaterials.inventory.slot.SlotComponent;
import mod.mindcraft.advancedmaterials.tileentity.TileEntityNuclearReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNuclearReactor extends Container {

	public ContainerNuclearReactor(InventoryPlayer player, TileEntityNuclearReactor reactor) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new SlotComponent(reactor, i * 9 + j, 28+j*18, 18+i*18));
			}
		}
		
        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(player, j1 + l * 9 + 9, 28 + j1 * 18, 103 + 56 + l * 18 - 19));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(player, i1, 28 + i1 * 18, 161 + 56 - 19));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 54) {
				if (!this.mergeItemStack(itemstack1, 54,
						this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				if (!this.mergeItemStack(itemstack1, 0, 54, false))
					return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}
	
	
}
