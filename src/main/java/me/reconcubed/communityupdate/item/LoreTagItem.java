package me.reconcubed.communityupdate.item;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.*;
import java.util.stream.Stream;

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
            String serialiser = ITextComponent.Serializer.toJson(storage.get(i).toFormattedComponent());
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

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if(!context.getWorld().isRemote) {
            ITextComponent nbt = new TranslationTextComponent("NBT: ");

            if (context.getItem().hasTag()) {
                nbt.appendSibling(new TranslationTextComponent(context.getItem().getTag().toString()));
//                nbt.appendSibling((new TranslationTextComponent(context.getItem().getTooltip(context.getPlayer(), ITooltipFlag.TooltipFlags.NORMAL).toString())));
                if (context.getItem().getTag().contains("loreStorage")) {
                    nbt.appendSibling(getLore(context.getItem()).toFormattedComponent());
                }
            }
            context.getPlayer().sendMessage(nbt);
        }
        return ActionResultType.PASS;

    }

}
