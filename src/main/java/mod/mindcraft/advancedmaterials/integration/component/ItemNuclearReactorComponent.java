package mod.mindcraft.advancedmaterials.integration.component;

import java.util.List;

import mod.mindcraft.advancedmaterials.AdvancedMaterials;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemNuclearReactorComponent extends Item {
	
	public final NuclearReactorComponent component;

	public ItemNuclearReactorComponent(NuclearReactorComponent component, String unlocalizedName) {
		this.component = component;
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(AdvancedMaterials.tabNuclearComponents);
		setMaxDamage(component.maxAbsorbedHeat == -1 ? component.duration : component.maxAbsorbedHeat);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (component.heat != 0)
			tooltip.add(StatCollector.translateToLocal("advmat.component.heat").replaceAll("%%v", "" + component.heat));
		if (component.cool != 0)
			tooltip.add(StatCollector.translateToLocal("advmat.component.cool").replaceAll("%%v", "" + component.cool));
		if (component.power != 0)
			tooltip.add(StatCollector.translateToLocal("advmat.component.power").replaceAll("%%v", "" + component.power));
		if (component.distrib != 0)
			tooltip.add(StatCollector.translateToLocal("advmat.component.distrib").replaceAll("%%v", "" + (component.distrib == -1 ? "Infinite" : component.distrib)));
		if (component.absorption != 0)
			tooltip.add(StatCollector.translateToLocal("advmat.component.absorption").replaceAll("%%v", "" + (component.absorption == -1 ? "Infinite" : component.absorption)));
		if (component.heatMul != 1f)
			tooltip.add(StatCollector.translateToLocal("advmat.component.heatMul").replaceAll("%%v", "" + component.heatMul));
		if (component.coolMul != 1f)
			tooltip.add(StatCollector.translateToLocal("advmat.component.coolMul").replaceAll("%%v", "" + component.coolMul));
		if (component.powerMul != 1f)
			tooltip.add(StatCollector.translateToLocal("advmat.component.powerMul").replaceAll("%%v", "" + component.powerMul));
		if (component.minTemperature != -1)
			tooltip.add(StatCollector.translateToLocal("advmat.component.minTemperature").replaceAll("%%v", "" + component.minTemperature));
		if (component.maxTemperature != -1)
			tooltip.add(StatCollector.translateToLocal("advmat.component.minTemperature").replaceAll("%%v", "" + component.maxTemperature));
		if (component.maxAbsorbedHeat != -1)
			tooltip.add(StatCollector.translateToLocal("advmat.component.maxAbsorbedHeat").replaceAll("%%v", "" + component.maxAbsorbedHeat));
		if (component.fromHull)
			tooltip.add(StatCollector.translateToLocal("advmat.component.fromHull"));
		if (component.duration != -1) {
			int hours = (int) Math.floor(component.duration / 20 / 3600);
			int minutes = (int) Math.floor(component.duration / 60 / 20);
			int seconds = (int) Math.floor(component.duration / 20);
			if (hours != 0) {
				minutes %= hours;
				seconds %= hours * 60;
			}
			if (minutes != 0) {
				seconds %= minutes;
			}
			tooltip.add(StatCollector.translateToLocal("advmat.component.duration").replaceAll("%%v", "" + component.duration).replaceAll("%%time", hours + " Hrs, " + minutes + " Min, " + seconds + " Secs"));
		}
		
		tooltip.add("");
		if (component.duration != -1 && component.power != 0) {
			if (component.duration * component.power > 0)
				tooltip.add(StatCollector.translateToLocal("advmat.component.produce").replaceAll("%%v", "" + component.duration * component.power));				
			if (component.duration * component.power < 0)
				tooltip.add(StatCollector.translateToLocal("advmat.component.cost").replaceAll("%%v", "" + component.duration * -component.power));
		}
		if (component.duration != -1 && component.heat != 0) {
			tooltip.add(StatCollector.translateToLocal("advmat.component.heatTotal").replaceAll("%%v", "" + component.duration * component.heat).replaceAll("%%time", "" + component.duration));
		}
	}
}
