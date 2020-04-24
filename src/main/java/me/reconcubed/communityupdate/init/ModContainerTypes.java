package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.container.FishNetContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, CommunityUpdate.MODID);

    public static final RegistryObject<ContainerType<FishNetContainer>> FISH_NET = CONTAINER_TYPES.register("fish_net", () -> IForgeContainerType.create(FishNetContainer::new));

}
