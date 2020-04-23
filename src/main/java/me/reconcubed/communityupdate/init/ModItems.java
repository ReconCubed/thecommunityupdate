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

    public static final RegistryObject<Item> MOSSY_COBBLESTONE_PEBBLES = ITEMS.register("mossy_cobblestone_pebbles", () -> new PebbleBagBaseItem(ModBlocks.MOSSY_COBBLESTONE_PATH.get().getDefaultState(), new Item.Properties()
            .group(ModItemGroups.COMMUNITY_UPDATE_ITEM_GROUP)
            .maxStackSize(1)
            .defaultMaxDamage(64)));

    public static final RegistryObject<Item> ANDESITE_PEBBLES = ITEMS.register("andesite_pebbles", () -> new PebbleBagBaseItem(ModBlocks.ANDESITE_PATH.get().getDefaultState(), new Item.Properties()
            .group(ModItemGroups.COMMUNITY_UPDATE_ITEM_GROUP)
            .maxStackSize(1)
            .defaultMaxDamage(64)));

    public static final RegistryObject<Item> GRANITE_PEBBLES = ITEMS.register("granite_pebbles", () -> new PebbleBagBaseItem(ModBlocks.GRANITE_PATH.get().getDefaultState(), new Item.Properties()
            .group(ModItemGroups.COMMUNITY_UPDATE_ITEM_GROUP)
            .maxStackSize(1)
            .defaultMaxDamage(64)));

    public static final RegistryObject<Item> DIORITE_PEBBLES = ITEMS.register("diorite_pebbles", () -> new PebbleBagBaseItem(ModBlocks.DIORITE_PATH.get().getDefaultState(), new Item.Properties()
            .group(ModItemGroups.COMMUNITY_UPDATE_ITEM_GROUP)
            .maxStackSize(1)
            .defaultMaxDamage(64)));

    public static final RegistryObject<Item> STONE_PEBBLES = ITEMS.register("stone_pebbles", () -> new PebbleBagBaseItem(ModBlocks.STONE_PATH.get().getDefaultState(), new Item.Properties()
            .group(ModItemGroups.COMMUNITY_UPDATE_ITEM_GROUP)
            .maxStackSize(1)
            .defaultMaxDamage(64)));

}
