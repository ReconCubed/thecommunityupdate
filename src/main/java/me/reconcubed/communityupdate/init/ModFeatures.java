package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.world.gen.feature.MinersCampConfig;
import me.reconcubed.communityupdate.world.gen.feature.MinersCampStructure;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, CommunityUpdate.MODID);

    public static final RegistryObject<MinersCampStructure> MINERS_CAMP = FEATURES.register("miners_camp", () -> new MinersCampStructure(MinersCampConfig::deserialize));
//    public static final RegistryObject<HugeOreStructure> HUGE_ORE = REGISTER.register("huge_ore", () -> new HugeOreStructure(HugeOreFeatureConfig::deserialize));

}
