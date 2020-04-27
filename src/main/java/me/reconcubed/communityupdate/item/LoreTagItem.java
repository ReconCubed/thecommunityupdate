package me.reconcubed.communityupdate.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import java.util.*;

public class LoreTagItem extends Item {

    public LoreTagItem(Properties properties) {
        super(properties);
    }

    public static boolean hasLore(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().getCompound("loreStorage").contains("lore");
    }

    public static ListNBT getLore(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundNBT tag = stack.getTag();

            if (hasLore(stack)) {
                return tag.getCompound("loreStorage").getList("lore", 8);
            }
        }
        return null;
    }

    public static void storeLore(ItemStack stack, String name) {
        List list = Arrays.asList(name.split("\\\\n"));

        ListNBT listNBT = new ListNBT();

        for (int i = 0; i < list.size(); i++) {
            listNBT.add(new StringNBT(list.get(i).toString()));
        }

        stack.getOrCreateChildTag("loreStorage").put("lore", listNBT);

    }

    public static void setLore(ItemStack stack, String name) {
        if (!stack.getTag().contains("display")){
            stack.getOrCreateChildTag("display");
        }
        storeLore(stack, name);
        ListNBT storage = getLore(stack);

        ListNBT lore = new ListNBT();


        for (int i  = 0; i < storage.size(); i++) {
            ITextComponent line = storage.get(i).toFormattedComponent().applyTextStyle(TextFormatting.GREEN);
            String serialiser = ITextComponent.Serializer.toJson(line).replaceAll("^\"|\"$", "");
            lore.add(new StringNBT(serialiser));
        }

        stack.getTag().getCompound("display").put("Lore", lore);

    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.hasTag()) {
            CompoundNBT nbt = stack.getChildTag("display");

            if (nbt != null && stack.getChildTag("display").contains("Name")) {
                setLore(stack, stack.getDisplayName().getString());
                stack.clearCustomName();
            }

        }
    }

}
