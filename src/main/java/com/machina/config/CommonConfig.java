package com.machina.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
	public static final ForgeConfigSpec COMMON_SPEC;

	static {
		ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		setupConfig(configBuilder);
		COMMON_SPEC = configBuilder.build();
	}
	
	public static ForgeConfigSpec.IntValue furnaceGeneratorCapacity;
	public static ForgeConfigSpec.IntValue furnaceGeneratorRate;
	public static ForgeConfigSpec.IntValue furnaceGeneratorTransferRate;

	private static void setupConfig(ForgeConfigSpec.Builder builder) {
		builder.push("machines");
		
		builder.push("furnace_generator");
		builder.comment("FurnaceGenerator settings");
		furnaceGeneratorCapacity = builder.defineInRange("furnace_generator_capacity", 1_000_000, 1, 999_999_999);
		furnaceGeneratorRate = builder.defineInRange("furnace_generator_rate", 60, 1, 9999);
		furnaceGeneratorTransferRate = builder.defineInRange("furnace_generator_transfer_rate", 1_000, 1, 999_999);
		builder.pop();
		
		builder.pop();
	}
}