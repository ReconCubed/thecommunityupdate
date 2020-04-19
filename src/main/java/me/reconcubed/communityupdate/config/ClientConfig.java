package me.reconcubed.communityupdate.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static ForgeConfigSpec.ConfigValue<String> color;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("client");
        color = builder
                .comment("The colour of Hexed Earth, #CC00FF is akin to the Cursed Earth mod from 1.6/1.7, #222222 is akin to newer Cursed Earth mods")
                .define("color", "#CC00FF", String.class::isInstance);
        builder.pop();
    }
}