/**
 * This file is part of the Machina Minecraft (Java Edition) mod and is licensed under the MIT license:
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
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
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

package com.cy4.machina.api.datagen.trait_pools;

import java.util.LinkedList;
import java.util.List;

import com.cy4.machina.api.planet.trait.PlanetTrait;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TraitPoolEntry {

	private final int weight;
	private List<PlanetTrait> values = new LinkedList<>();

	public TraitPoolEntry(int weight, PlanetTrait... values) {
		this.weight = weight;
		for (PlanetTrait value : values) {
			this.values.add(value);
		}
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("weight", weight);
		JsonArray array = new JsonArray();
		values.forEach(trait -> array.add(trait.getRegistryName().toString()));
		obj.add("values", array);
		return obj;
	}

}
