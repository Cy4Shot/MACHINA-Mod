package com.machina.api.starchart.obj;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.machina.api.util.ChemicalConstants.MolecularMass;
import com.machina.api.util.ChemicalConstants.TempRange;
import com.machina.registration.init.FluidInit;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public record Planet(String name, double a, // semi-major axis of the orbit (in AU)
		double e, // eccentricity of the orbit
		double where_in_orbit, // position along orbit (in radians)
		double mass, // mass (in Earth masses)
		boolean gas_giant, // true if the planet is gassy
		int orbit_zone, // the 'zone' of the planet
		double radius, // equatorial radius (in km)
		double density, // density (in g/cc)
		double orb_period, // length of the local year (days)
		double day, // length of the local day (hours)
		int resonant_period, // true if in resonant rotation
		int axial_tilt, // units of degrees
		double esc_velocity, // units of cm/sec
		double surf_accel, // units of cm/sec2
		double surf_grav, // units of Earth gravities
		double rms_velocity, // units of cm/sec
		double molec_weight, // smallest molecular weight retained
		double volatile_gas_inventory, double GH2, double GH2O, double GN2, double GO2, double GCO2, // gas retention
																										// percentages
		double surf_pressure, // units of millibars (mb)
		boolean greenhouse_effect, // runaway greenhouse effect?
		double boil_point, // the boiling point of water (Kelvin)
		double albedo, // albedo of the planet
		double surf_temp, // surface temperature in Kelvin
		double min_temp, double max_temp, // surface temperature ranges
		double avg_temp, // weighted average of iterations
		double hydrosphere, // fraction of surface covered
		double cloud_cover, // fraction of surface covered
		double ice_cover, // fraction of surface covered
		char plan_class, // general type classification
		double r_ecosphere, double resonance, double stell_mass_ratio, double age, double cloud_factor,
		double water_factor, double rock_factor, double airless_rock_factor, double ice_factor,
		double airless_ice_factor, int its, boolean temp_unstable, List<Moon> moons) {

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}

	public Vec3 calculateOrbitalCoordinates(double t) {
		double trueAnomaly = calculateTrueAnomaly(t);

		double x = a * (Math.cos(trueAnomaly) - e);
		double z = a * Math.sqrt(1 - e * e) * Math.sin(trueAnomaly);

		return new Vec3(x, 0, z);
	}

	private double calculateTrueAnomaly(double t) {
		// Orbital parameters
		double meanMotion = 2 * Math.PI / orb_period;
		double meanAnomaly = meanMotion * t + where_in_orbit;

		// Eccentricity
		double eccentricAnomaly = calculateEccentricAnomaly(meanAnomaly);

		// True anomaly
		return 2 * Math.atan(Math.sqrt((1 + e) / (1 - e)) * Math.tan(eccentricAnomaly / 2));
	}

	private double calculateEccentricAnomaly(double meanAnomaly) {
		double E = meanAnomaly;
		double tolerance = 1e-9;
		int maxIterations = 1000;
		int iterations = 0;

		do {
			double nextE = E - ((E - e * Math.sin(E) - meanAnomaly) / (1 - e * Math.cos(E)));
			if (Math.abs(nextE - E) < tolerance) {
				E = nextE;
				break;
			}
			E = nextE;
			iterations++;
		} while (iterations < maxIterations);

		return E;
	}

	public double calculateAphelionDistance() {
		return a * (1 + e);
	}

	// Extra Props

	public boolean hasGenLiquid() {
		return getDominantLiquidBody() != null;
	}

	public boolean isFluidFrozen() {
		Random r = new Random(name.hashCode());
		if (isWaterPossible() && fluidState(TempRange.WATER).equals(FluidTempState.SOLID) && r.nextBoolean()) {
			return true;
		}
		if (isAmmoniaPossible() && fluidState(TempRange.AMMONIA).equals(FluidTempState.SOLID) && r.nextBoolean()) {
			return true;
		}
		if (isMethanePossible() && fluidState(TempRange.METHANE).equals(FluidTempState.SOLID) && r.nextBoolean()) {
			return true;
		}

		return false;
	}

	public FluidState getDominantLiquidBody() {
		if (isWaterPossible() && !fluidState(TempRange.WATER).equals(FluidTempState.GAS)) {
			return Fluids.WATER.defaultFluidState();
		}
		if (isAmmoniaPossible() && !fluidState(TempRange.AMMONIA).equals(FluidTempState.GAS)) {
			return FluidInit.AMMONIA.fluid().defaultFluidState();
		}
		if (isMethanePossible() && !fluidState(TempRange.METHANE).equals(FluidTempState.GAS)) {
			return FluidInit.METHANE.fluid().defaultFluidState();
		}
		if (fluidState(TempRange.LAVA).equals(FluidTempState.LIQUID)) {
			return Fluids.LAVA.defaultFluidState();
		}

		return null;
	}

	public BlockState getDominantLiquidBodyBlock() {
		FluidState fluid = getDominantLiquidBody();
		return fluid == null ? Blocks.WATER.defaultBlockState() : fluid.createLegacyBlock();
	}

	public boolean isWaterPossible() {
		return molec_weight < MolecularMass.WATER;
	}

	public boolean isAmmoniaPossible() {
		return molec_weight < MolecularMass.AMMONIA;
	}

	public boolean isMethanePossible() {
		return molec_weight < MolecularMass.METHANE;
	}

	public enum FluidTempState {
		SOLID,
		LIQUID,
		GAS
	}

	public FluidTempState fluidState(TempRange range) {
		if (surf_temp < range.freeze) {
			return FluidTempState.SOLID;
		}
		if (surf_temp > range.boil) {
			return FluidTempState.GAS;
		}
		return FluidTempState.SOLID;
	}

	public boolean doesRain() {
		return hydrosphere > 0;
	}
}