package com.machina.api.util;

public class ChemicalConstants {

	public static class MolecularMass {

		public static final float WATER = 18.01528f;
		public static final float AMMONIA = 17.031f;
		public static final float METHANE = 16.04f;
	}

	public static class TempRange {

		public static final TempRange WATER = new TempRange(273.15F, 373.15F);
		public static final TempRange AMMONIA = new TempRange(195.4F, 239.81F);
		public static final TempRange METHANE = new TempRange(90.694F, 111.6F);

		public final float freeze;
		public final float boil;

		public TempRange(float freeze, float boil) {
			this.freeze = freeze;
			this.boil = boil;
		}
	}

}
