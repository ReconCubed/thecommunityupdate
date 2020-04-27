package me.reconcubed.communityupdate.recipe;

import me.reconcubed.communityupdate.init.ModRecipes;
import me.reconcubed.communityupdate.init.OverrideItems;
import me.reconcubed.communityupdate.item.ElytraItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ElytraRecipe extends SpecialRecipe {
    private final ResourceLocation id;

//    public static final IRecipeSerializer<ElytraRecipe> SERIALIZER = new SpecialRecipeSerializer<>(ElytraRecipe::new);

    public ElytraRecipe(ResourceLocation idIn) {
        super(idIn);
        this.id = idIn;
    }

//    public static class Decoration implements IRecipe {
//        public Decoration() {}

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack1 = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemStack2 = inv.getStackInSlot(i);

            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof BannerItem) {
                    if (!itemStack1.isEmpty()) {
                        return false;
                    }
                    itemStack1 = itemStack2;
                } else {
                    if (itemStack2.getItem() != OverrideItems.ELYTRA.get()) {
                        return false;
                    }

                    if (itemStack2.isEmpty()) {
                        return false;
                    }

                    itemStack = itemStack2;

                }
            }
        }
        if (!itemStack.isEmpty() && !itemStack1.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack1 = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemStack2 = inv.getStackInSlot(i);

            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof BannerItem) {
                    itemStack = itemStack2;
                } else if (itemStack2.getItem() == OverrideItems.ELYTRA.get()) {
                    itemStack1 = itemStack2.copy();
                }
            }
        }

        if (itemStack1.isEmpty()) {
            return itemStack1;
        } else {
            CompoundNBT compoundnbt = itemStack.getChildTag("BlockEntityTag");
            CompoundNBT compoundnbt1 = compoundnbt == null ? new CompoundNBT() : compoundnbt.copy();
            compoundnbt1.putInt("Base", ((BannerItem) itemStack.getItem()).getColor().getId());
            itemStack1.setTagInfo("BlockEntityTag", compoundnbt1);
            return itemStack1;
        }
    }


    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId () {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.ELYTRA_DECORATION.get();
    }

//    }


}
