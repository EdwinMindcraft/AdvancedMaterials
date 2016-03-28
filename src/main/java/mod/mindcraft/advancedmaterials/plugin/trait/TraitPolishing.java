package mod.mindcraft.advancedmaterials.plugin.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.traits.TraitProgressiveStats;

public class TraitPolishing extends TraitProgressiveStats {

	public TraitPolishing() {
		super("polishing", EnumChatFormatting.AQUA);
	}
	
	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		if (!hasPool(rootCompound)) {
			ToolNBT tool = TagUtil.getToolStats(rootCompound);
			int durablility = (tool.durability * 2) + (tool.durability / 2);
			tool.durability /= 2;
			StatNBT data = new StatNBT();
			data.durability = durablility;
			TagUtil.setToolTag(rootCompound, tool.get());
			setPool(rootCompound, data);
		}
		super.applyEffect(rootCompound, modifierTag);
	}
	
	@Override
	public int onToolHeal(ItemStack tool, int amount, int newAmount, EntityLivingBase entity) {
		NBTTagCompound root = TagUtil.getTagSafe(tool);
		StatNBT pool = getPool(root);
		StatNBT distributed = getBonus(root);
		ToolNBT data = TagUtil.getToolStats(tool);
		System.out.println(pool.durability - distributed.durability);
		if (pool.durability > distributed.durability) {
			int dur = (int) Math.max((pool.durability - distributed.durability) * 0.05F, 1);
			distributed.durability += dur;
			data.durability += dur;
		}
		TagUtil.setToolTag(root, data.get());
		setBonus(root, distributed);
		return super.onToolHeal(tool, amount, newAmount, entity);
	}
}
