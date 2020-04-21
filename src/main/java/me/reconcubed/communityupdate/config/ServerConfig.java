package me.reconcubed.communityupdate.config;

import me.reconcubed.communityupdate.CommunityUpdate;
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

    public static ForgeConfigSpec.IntValue minTickTime;
    public static ForgeConfigSpec.IntValue maxTickTime;
    public static ForgeConfigSpec.IntValue mobCap;
    public static ForgeConfigSpec.BooleanValue forceSpawn;
    public static ForgeConfigSpec.BooleanValue diesInSunlight;
    public static ForgeConfigSpec.BooleanValue naturallySpreads;
    public static ForgeConfigSpec.IntValue spawnRadius;
    public static ForgeConfigSpec.BooleanValue witherRose;

    ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("general");

        minTickTime = builder
                .comment("The minimum time between mob spawn on Hexed Earth in ticks.")
                .defineInRange("min tick time", 50, 1, Integer.MAX_VALUE);
        maxTickTime = builder
                .comment("The maximum time between mob spawn on Hexed Earth in ticks.")
                .defineInRange("max tick time", 250, 1, Integer.MAX_VALUE);
        mobCap = builder
                .comment("The maximum number of mobs before Hexed Earth will stop spawning.")
                .defineInRange("mob cap", 250, 1, Integer.MAX_VALUE);
        forceSpawn = builder
                .comment("Force Hexed Earth spawns to occur regardless of conditions such as light level and elevation")
                .define("force spawns", false);
        diesInSunlight = builder
                .comment("Does Hexed Earth die under sunlight?")
                .define("die in sunlight", true);
        naturallySpreads = builder
                .comment("Does Hexed Earth naturally spread?")
                .define("naturally spread", true);
        witherRose = builder
                .comment("Does the wither rose create Hexed Earth?")
                .define("wither rose", true);
        spawnRadius = builder
                .comment("The minimum distance Hexed Earth has to be away from players before it begins spawning mobs.")
                .defineInRange("spawn radius", 1, 1, Integer.MAX_VALUE);
// TODO Add chunk loader panic option that automatically disables all chunk loaders
        builder.pop();
    }
}


