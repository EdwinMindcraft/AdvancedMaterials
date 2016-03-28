package mod.mindcraft.advancedmaterials.plugin.trait;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitCoalFueled extends AbstractTraitLeveled {

	public TraitCoalFueled(int levels) {
		super("coalfueled", 0x666666, 5, levels);
	}
	
	@Override
	public void blockHarvestDrops(ItemStack tool, HarvestDropsEvent event) {
		Random rand = new Random();
		ArrayList<ItemStack> newDrops = new ArrayList<ItemStack>();
		for (ItemStack stack : event.drops) {
			if (TileEntityFurnace.isItemFuel(stack)) {
				int stacksize = stack.stackSize * 1;
				for (int i = 0; i < stacksize; i++) {
					if (rand.nextFloat() < 0.05 * levels) {
						ToolHelper.healTool(tool, TileEntityFurnace.getItemBurnTime(stack) / 100 * levels, event.harvester);
						stack.stackSize--;
					}
				}
			}
			if (stack.stackSize > 0)
				newDrops.add(stack);
		}
		event.drops.clear();
		event.drops.addAll(newDrops);
		super.blockHarvestDrops(tool, event);
	}
}
