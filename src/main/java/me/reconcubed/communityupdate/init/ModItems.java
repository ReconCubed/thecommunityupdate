package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.item.PebbleBagBaseItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, CommunityUpdate.MODID);

    public static final RegistryObject<Item> COBBLESTONE_PEBBLES = ITEMS.register("cobblestone_pebbles", () -> new PebbleBagBaseItem(ModBlocks.COBBLESTONE_PATH.get().getDefaultState(), new Item.Properties()
            .group(ModItemGroups.COMMUNITY_UPDATE_ITEM_GROUP)
            .maxStackSize(1)
            .defaultMaxDamage(64)));
}
