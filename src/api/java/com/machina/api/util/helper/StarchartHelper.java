/**
 * This file is part of the Machina Minecraft (Java Edition) mod and is licensed
 * under the MIT license:
 *
 * MIT License
 *
 * Copyright (c) 2021 Machina Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * If you want to contribute please join https://discord.com/invite/x9Mj63m4QG.
 * More information can be found on Github: https://github.com/Cy4Shot/MACHINA
 */

package com.machina.api.util.helper;

import com.machina.api.planet.trait.PlanetTrait;
import com.machina.api.starchart.Starchart;
import com.machina.api.world.data.StarchartData;

import net.minecraft.world.World;

public class StarchartHelper {

	public static void addTraitToWorld(World level, PlanetTrait trait) {
		Starchart serverChart = StarchartData.getStarchartForServer(level.getServer());
		serverChart.getDimensionDataOrCreate(level.dimension().location()).ifPresent(data -> data.getTraits().addTrait(trait));
		setDirtyAndUpdate(level);
	}
	
	public static void removeTraitFromWorld(World level, PlanetTrait trait) {
		Starchart serverChart = StarchartData.getStarchartForServer(level.getServer());
		serverChart.getDimensionDataOrCreate(level.dimension().location()).ifPresent(data -> data.getTraits().removeTrait(trait));
		setDirtyAndUpdate(level);
	}
	
	public static void setDirtyAndUpdate(World level) {
		StarchartData.getDefaultInstance(level.getServer()).setDirty();
		StarchartData.getDefaultInstance(level.getServer()).syncClients();
	}

}