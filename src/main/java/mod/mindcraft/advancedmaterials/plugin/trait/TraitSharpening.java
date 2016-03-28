package mod.mindcraft.advancedmaterials.plugin.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.traits.TraitProgressiveStats;

public class TraitSharpening extends TraitProgressiveStats {

	public TraitSharpening() {
		super("sharpening", EnumChatFormatting.WHITE);
	}
	
	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		if (!hasPool(rootCompound)) {
			ToolNBT tool = TagUtil.getToolStats(rootCompound);
			float attack = (tool.attack * 2) + (tool.attack / 2);
			float speed = (tool.speed * 2) + (tool.speed / 2);
			tool.attack /= 2;
			tool.speed /= 2;
			StatNBT data = new StatNBT();
			data.attack = attack;
			data.speed = speed;
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
		if (pool.attack > distributed.attack) {
			float dur = Math.max((pool.attack - distributed.attack) * 0.05F, 0.01F);
			if (dur > pool.attack - distributed.attack)
				dur = pool.attack - distributed.attack;
			distributed.attack += dur;
			data.attack += dur;
		}
		if (pool.speed > distributed.speed) {
			float dur = Math.max((pool.speed - distributed.speed) * 0.05F, 0.01F);
			if (dur > pool.speed - distributed.speed)
				dur = pool.speed - distributed.speed;
			distributed.speed += dur;
			data.speed += dur;
		}
		System.out.println("Attack Remining : " + (pool.attack - distributed.attack));
		System.out.println("Speed Remining : " + (pool.speed - distributed.speed));
		TagUtil.setToolTag(root, data.get());
		setBonus(root, distributed);
		return super.onToolHeal(tool, amount, newAmount, entity);
	}

}
