package com.machina.registration.init;

import com.machina.Machina;
import com.machina.api.util.MachinaRL;

import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			Machina.MOD_ID);

	public static final RegistryObject<SoundEvent> MUSIC = create("music");

	private static RegistryObject<SoundEvent> create(String loc) {
		return SOUNDS.register(loc, () -> SoundEvent.createVariableRangeEvent(new MachinaRL(loc)));
	}

	public static Music asMusic(RegistryObject<SoundEvent> reg) {
		return new Music(Holder.direct(reg.get()), 0, 0, true);
	}
}
