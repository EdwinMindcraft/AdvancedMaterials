package mod.mindcraft.advancedmaterials.inventory.slot;

import mod.mindcraft.advancedmaterials.integration.component.ItemNuclearReactorComponent;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotComponent extends Slot {

	public SlotComponent(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemNuclearReactorComponent;
	}
}
