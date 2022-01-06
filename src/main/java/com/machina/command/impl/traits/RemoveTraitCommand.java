/**
 * This file is part of the Machina Minecraft (Java Edition) mod and is licensed
 * under the MIT license:
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2021 Machina Team
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p>
 * If you want to contribute please join https://discord.com/invite/x9Mj63m4QG.
 * More information can be found on Github: https://github.com/Cy4Shot/MACHINA
 */

package com.machina.command.impl.traits;

import com.machina.api.command.argument.PlanetTraitArgument;
import com.machina.api.planet.trait.PlanetTrait;
import com.machina.api.starchart.Starchart;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class RemoveTraitCommand extends PlanetTraitsCommand {

    public RemoveTraitCommand(int permissionLevel, boolean enabled) {
        super(permissionLevel, enabled);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(Commands.argument("trait", PlanetTraitArgument.planetTrait()).executes(
                commandSource -> execute(commandSource, PlanetTraitArgument.getTrait(commandSource, "trait"))));
    }

    public int execute(CommandContext<CommandSource> context, PlanetTrait trait) {
        if (checkDimension(context)) {
            ServerWorld level = context.getSource().getLevel();
            Starchart starchart = Starchart.getStarchartForServer(context.getSource().getServer());
            if (starchart.getDataForLevel(level).getTraits().contains(trait)) {
                starchart.removeTrait(level, trait);
                context.getSource().sendSuccess(
                        new TranslationTextComponent("command.planet_traits.remove_trait.success"), true);
            } else {
                context.getSource()
                        .sendFailure(new TranslationTextComponent(
                                "command.planet_traits.remove_trait.not_existing",
                                trait.getRegistryName().toString()));
            }
        }
        return 0;
    }

    @Override
    public String getName() {
        return "remove_trait";
    }

}