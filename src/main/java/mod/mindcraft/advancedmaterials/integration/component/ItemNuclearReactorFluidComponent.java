package mod.mindcraft.advancedmaterials.integration.component;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

public class ItemNuclearReactorFluidComponent extends ItemNuclearReactorComponent {
	
	FluidStack fluid;
	
	public ItemNuclearReactorFluidComponent(NuclearReactorComponent component, String unlocalizedName, FluidStack stack) {
		super(component, unlocalizedName);
		this.fluid = stack;
		setMaxDamage(fluid.amount);
	}
	
	public FluidStack getFluid(ItemStack item) {
		return new FluidStack(fluid.getFluid(), fluid.amount - item.getItemDamage());
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		tooltip.add(StatCollector.translateToLocal("advmat.component.fluid").replaceAll("%%v", "" +getFluid(stack).amount).replaceAll("%%t", getFluid(stack).getLocalizedName()));
	}
}
