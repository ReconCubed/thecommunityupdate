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

    public static ForgeConfigSpec.BooleanValue mobsAlwaysDropXp;

    public static ForgeConfigSpec.LongValue fishNetMinTickTime;
    public static ForgeConfigSpec.LongValue fishNetMaxTickTime;

    ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("general");

        minTickTime = builder
                .comment("The minimum time between mob spawn on Vexed Earth in ticks.")
                .defineInRange("min tick time", 50, 1, Integer.MAX_VALUE);
        maxTickTime = builder
                .comment("The maximum time between mob spawn on Vexed Earth in ticks.")
                .defineInRange("max tick time", 250, 1, Integer.MAX_VALUE);
        mobCap = builder
                .comment("The maximum number of mobs before Vexed Earth will stop spawning.")
                .defineInRange("mob cap", 250, 1, Integer.MAX_VALUE);
        forceSpawn = builder
                .comment("Force Vexed Earth spawns to occur regardless of conditions such as light level and elevation.")
                .define("force spawns", false);
        diesInSunlight = builder
                .comment("Does Vexed Earth die under sunlight?")
                .define("die in sunlight", true);
        naturallySpreads = builder
                .comment("Does Vexed Earth naturally spread?")
                .define("naturally spread", true);
        witherRose = builder
                .comment("Does the wither rose create Vexed Earth?")
                .define("wither rose", true);
        spawnRadius = builder
                .comment("The minimum distance Vexed Earth has to be away from players before it begins spawning mobs.")
                .defineInRange("spawn radius", 1, 1, Integer.MAX_VALUE);

        mobsAlwaysDropXp = builder
                .comment("Do mobs drop XP and other rare items regardless if a player kills them or not?")
                .define("mobs always drop xp", true);

        fishNetMinTickTime = builder
                .comment("The minimum time in ticks between the fishing net attempting to fish.")
                .defineInRange("fish net min tick time", 450, 450, Long.MAX_VALUE);

        fishNetMaxTickTime = builder
                .comment("The maximum time in ticks between the fishing net attempting to fish.")
                .defineInRange("fish net max tick time", 800, 750, Long.MAX_VALUE);


// TODO Add chunk loader panic option that automatically disables all chunk loaders
        builder.pop();
    }
}


