package me.reconcubed.communityupdate.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

/**
 * Idea from Mr. Crayfish.
 * I really wanted this in 1.14.4.
 */
public class MinersCampConfig implements IFeatureConfig {
    public final int chance;
    public final ResourceLocation template;

    public MinersCampConfig(int chance, ResourceLocation template)
    {
        this.chance = chance;
        this.template = template;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops)
    {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("chance"), ops.createInt(this.chance), ops.createString("template"), ops.createString(this.template.toString()))));
    }

    public static MinersCampConfig deserialize(Dynamic<?> dynamic)
    {
        int chance = dynamic.get("chance").asInt(2);
        ResourceLocation template = new ResourceLocation(dynamic.get("template").asString("minecraft:empty"));
        return new MinersCampConfig(chance, template);
    }
}
