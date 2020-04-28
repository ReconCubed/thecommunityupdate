package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.item.ElytraItem;
import me.reconcubed.communityupdate.item.PebbleBagBaseItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OverrideItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> ELYTRA = ITEMS.register("elytra", () -> new ElytraItem(new Item.Properties()
            .group(ItemGroup.TRANSPORTATION)
            .maxStackSize(1)
            .rarity(Rarity.UNCOMMON)
            .maxDamage(432)));

}
