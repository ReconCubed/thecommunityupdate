package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.tileentity.FishNetTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, CommunityUpdate.MODID);

    public static final RegistryObject<TileEntityType<FishNetTileEntity>> FISH_NET = TILE_ENTITY_TYPES.register("fish_net", () ->
            TileEntityType.Builder.create(FishNetTileEntity::new, ModBlocks.FISH_NET.get())
                    .build(null)
    );
}
