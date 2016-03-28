package mod.mindcraft.advancedmaterials.integration.recipes;

import java.util.ArrayList;

import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class NuclearFusionRecipe {
	
	public FluidStack output;
	public FluidStack[] inputs;

	public NuclearFusionRecipe(FluidStack output, FluidStack... inputs) {
		if (inputs.length < 1)
			throw new IllegalArgumentException("You must set at least 1 reactive");
		this.output = output;
		this.inputs = inputs;
	}

	public FluidStack getOutput() {
		return output.copy();
	}

	public ImmutableList<FluidStack> getInputs() {
		return ImmutableList.copyOf(inputs);
	}

	public boolean matches(FluidStack... inputs) {
		if (inputs.length < 1)
			return false;
		int matches = 0;
		ArrayList<FluidStack> in = Lists.newArrayList(inputs);
		for (FluidStack input : this.inputs) {
			for (FluidStack check : in) {
				if (check.containsFluid(input)) {
					matches++;
					break;
				}
			}
		}
		return matches == this.inputs.length;
	}

}
