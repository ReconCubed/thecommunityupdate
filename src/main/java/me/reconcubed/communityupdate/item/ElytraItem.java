package me.reconcubed.communityupdate.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ElytraItem extends Item {
    public ElytraItem(Properties properties) {
        super(properties);
        this.addPropertyOverride(new ResourceLocation("broken"), (p_210312_0_, p_210312_1_, p_210312_2_) -> {
            return isUsable(p_210312_0_) ? 0.0F : 1.0F;
        });
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);

    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.PHANTOM_MEMBRANE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemStack);
        ItemStack itemStack1 = playerIn.getItemStackFromSlot(equipmentslottype);

        if (itemStack1.isEmpty()) {
            playerIn.setItemStackToSlot(EquipmentSlotType.CHEST, itemStack.copy());
            itemStack.setCount(0);
            return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
        } else {
            return new ActionResult<>(ActionResultType.FAIL, itemStack);
        }
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().contains("BlockEntityTag")) {
//            DyeColor dyeColor = DyeColor.getColor(stack);
//            return new TranslationTextComponent("item.communityupdate.elytra." + dyeColor);
            return new TranslationTextComponent("item.minecraft.elytra");

        } else {
            return new TranslationTextComponent("item.minecraft.elytra");
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        BannerItem.appendHoverTextFromTileEntityTag(stack, tooltip);
    }
}
