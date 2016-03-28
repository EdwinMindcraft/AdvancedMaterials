package mod.mindcraft.advancedmaterials.integration;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachine<K extends TileEntityMachine<? extends IMachineRecipe, ? extends IRecipeRegistry<? extends IMachineRecipe>>> extends Container {
	
	protected InventoryPlayer player;
	protected K machine;
	
	public ContainerMachine(InventoryPlayer player, K machine) {
		this.player = player;
		this.machine = machine;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < machine.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, machine.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else  {
            	boolean hasMerged = false;
            	for (int i = 0; i < machine.getInputSlots().length; i++) {
            		if (this.mergeItemStack(itemstack1, machine.getInputSlots()[i], machine.getInputSlots()[i] + 1, false)) {
            			hasMerged = true;
            			break;
            		}
            	}
            	if (!hasMerged)
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

}
