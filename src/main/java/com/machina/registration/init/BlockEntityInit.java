package com.machina.registration.init;

import java.util.function.Supplier;

import com.machina.Machina;
import com.machina.block.entity.MachinaHangingSignBlockEntity;
import com.machina.block.entity.MachinaSignBlockEntity;
import com.machina.block.entity.connector.EnergyCableBlockEntity;
import com.machina.block.entity.machine.BatteryBlockEntity;
import com.machina.block.entity.machine.CreativeBatteryBlockEntity;
import com.machina.block.entity.machine.FurnaceGeneratorBlockEntity;
import com.machina.block.entity.machine.GrinderBlockEntity;
import com.machina.block.entity.machine.MachineCaseBlockEntity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Machina.MOD_ID);

	//@formatter:off
	public static final RegistryObject<BlockEntityType<EnergyCableBlockEntity>> ENERGY_CABLE = register("energy_cable",
            EnergyCableBlockEntity::new, () -> BlockInit.ENERGY_CABLE.get());
	public static final RegistryObject<BlockEntityType<BatteryBlockEntity>> BATTERY = register("battery",
            BatteryBlockEntity::new, () -> BlockInit.BATTERY.get());
	public static final RegistryObject<BlockEntityType<CreativeBatteryBlockEntity>> CREATIVE_BATTERY = register("creaitve_battery",
            CreativeBatteryBlockEntity::new, () -> BlockInit.CREATIVE_BATTERY.get());
	public static final RegistryObject<BlockEntityType<MachineCaseBlockEntity>> MACHINE_CASE = register("machine_case",
            MachineCaseBlockEntity::new, () -> BlockInit.BASIC_MACHINE_CASE.get());
	public static final RegistryObject<BlockEntityType<FurnaceGeneratorBlockEntity>> FURNACE_GENERATOR = register("furnace_generator",
            FurnaceGeneratorBlockEntity::new, () -> BlockInit.FURNACE_GENERATOR.get());
	public static final RegistryObject<BlockEntityType<GrinderBlockEntity>> GRINDER = register("grinder",
            GrinderBlockEntity::new, () -> BlockInit.GRINDER.get());
	//@formatter:on

	public static final RegistryObject<BlockEntityType<MachinaSignBlockEntity>> SIGN = registerMany("sign",
			MachinaSignBlockEntity::new, () -> BlockInit.SIGNS.stream().map(RegistryObject::get).toArray(Block[]::new));
	public static final RegistryObject<BlockEntityType<MachinaHangingSignBlockEntity>> HANGING_SIGN = registerMany(
			"hanging_sign", MachinaHangingSignBlockEntity::new,
			() -> BlockInit.HANGING_SIGNS.stream().map(RegistryObject::get).toArray(Block[]::new));

	private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String n,
			BlockEntityType.BlockEntitySupplier<T> s, Supplier<Block> b) {
		return BLOCK_ENTITY_TYPES.register(n, () -> BlockEntityType.Builder.of(s, b.get()).build(null));
	}

	private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerMany(String n,
			BlockEntityType.BlockEntitySupplier<T> s, Supplier<Block[]> b) {
		return BLOCK_ENTITY_TYPES.register(n, () -> BlockEntityType.Builder.of(s, b.get()).build(null));
	}
}
