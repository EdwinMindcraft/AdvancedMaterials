package mod.mindcraft.advancedmaterials.integration.component;

/**
 * Nuclear reactor components.<BR>
 * This is advanced be sure to know what you're doing if you plan on creating new components.<BR>
 * It would be nice of you not to create an everlasting fuel.
 * 
 * @author EdwinMindcraft
 */
public class NuclearReactorComponent {
	
	public final int heat, cool, power, absorption, distrib, minTemperature, maxTemperature, maxAbsorbedHeat, duration;
	public final float heatMul, coolMul, powerMul;
	
	/**
	 * Reactor Component
	 * 
	 * @param heat : heat generated per tick
	 * @param cool : cooling per tick
	 * @param power : energy per tick
	 * @param absorption : maximum Heat this component can absorb from surroundings per tick (-1 for infinite)
	 * @param distrib : maximum Heat this component can pass to his surroundings per tick (-1 for infinite)
	 * @param heatMul : heat multiplier for heat generating surroundings 
	 * @param coolMul : coolant multiplier
	 * @param powerMul : power multiplier for surroundings
	 * @param minTemperature : minimum temperature to use this component
	 * @param maxTemperature : maximum temperature to use this component
	 * @param maxAbsorbedHeat : maximum heat this component can absorb before breaking (-1 to disable)
	 * @param duration : maximum duration of the fuel (-1 to disable)
	 */
	public NuclearReactorComponent(int heat, int cool, int power, int absorption, int distrib, float heatMul, float coolMul, float powerMul, int minTemperature, int maxTemperature, int maxAbsorbedHeat, int duration) {
		if (duration != -1 && maxAbsorbedHeat != -1)
			throw new IllegalArgumentException("Duration and Max Absorbed Heat cannot be both used at the same time");
		if (duration == -1 && maxAbsorbedHeat == -1)
			throw new IllegalArgumentException("Duration and Max Absorbed Heat cannot be both null");		
		this.heat = heat;
		this.cool = cool;
		this.power = power;
		this.heatMul = heatMul;
		this.coolMul = coolMul;
		this.powerMul = powerMul;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.maxAbsorbedHeat = maxAbsorbedHeat;
		this.duration = duration;
		this.absorption = absorption;
		this.distrib = distrib;
	}
	
	/**
	 * For fuel registration
	 * 
	 * @param heat : heat generated per tick
	 * @param power : energy per tick
	 * @param chainHeatMultiplier : heat multiplication for surroundings
	 * @param chainPowerMultiplier : power multiplication for surroundings
	 * @param duration : maximum duration of the fuel (-1 to disable)
	 */
	public static NuclearReactorComponent createFuel(int heat, int power, float chainHeatMultiplier, float chainPowerMultiplier, int duration) {
		return new NuclearReactorComponent(heat, 0, power, 0, -1, chainHeatMultiplier, 1f, chainPowerMultiplier, -1, -1, -1, duration);
	}
	
	/**
	 * For coolant registration
	 * 
	 * @param cool : cooling per tick
	 * @param absorption : maximum Heat this component can absorb from surroundings per tick (-1 for infinite)
	 * @param maxAbsorbedHeat : maximum heat this component can absorb before breaking (-1 to disable)
	 */
	public static NuclearReactorComponent createCoolant(int cool, int absorption, int maxAbsorbedHeat) {
		return new NuclearReactorComponent(0, cool, 0, absorption, 0, 1f, 1f, 1f, -1, -1, maxAbsorbedHeat, -1);
	}
	
	/**
	 * For heat dispatcher registration
	 * 
	 * @param absorption : maximum Heat this component can absorb from surroundings per tick (-1 for infinite)
	 * @param distrib : maximum Heat this component can pass to his surroundings per tick (-1 for infinite)
	 * @param maxAbsorbedHeat : maximum heat this component can absorb before breaking (-1 to disable)
	 */
	public static NuclearReactorComponent createHeatDispatcher(int absorption, int distrib, int maxAbsorbedHeat) {
		return new NuclearReactorComponent(0, 0, 0, absorption, distrib, 1f, 1f, 1f, -1, -1, maxAbsorbedHeat, -1);
	}
}
