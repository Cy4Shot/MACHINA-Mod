package com.machina.compat.jei;

import com.machina.api.util.MachinaRL;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class MachinaJei implements IModPlugin {
	
	public static final ResourceLocation UID = new MachinaRL("machina");

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

}
