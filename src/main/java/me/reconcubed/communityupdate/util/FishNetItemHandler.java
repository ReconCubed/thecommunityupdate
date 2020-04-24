package me.reconcubed.communityupdate.util;

import me.reconcubed.communityupdate.tileentity.FishNetTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class FishNetItemHandler implements IItemHandler {
    public FishNetTileEntity fishNetTileEntity;

    public FishNetItemHandler(FishNetTileEntity tileEntity) {
        fishNetTileEntity = tileEntity;
    }

    @Override
    public int getSlots() {
        return FishNetInventory.SLOTS;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return fishNetTileEntity.getInventory().getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(getStackInSlot(slot).isEmpty()) {
            if(slot == 0 && stack.getItem() == Items.FISHING_ROD) {
                fishNetTileEntity.getInventory().getContents().set(slot, stack);
                fishNetTileEntity.markDirty();
                return ItemStack.EMPTY;
            }
            if(!simulate) {
                fishNetTileEntity.getInventory().getContents().set(slot, stack);
                fishNetTileEntity.markDirty();
                return ItemStack.EMPTY;
            }
        } else if(getStackInSlot(slot).isItemEqual(stack) &&
                (getStackInSlot(slot).getCount() + stack.getCount() <= getStackInSlot(slot).getMaxStackSize()) &&
                getStackInSlot(slot).isStackable()) {
            if(!simulate) {
                fishNetTileEntity.getInventory().getContents()
                        .set(slot, new ItemStack(stack.getItem(), stack.getCount() + getStackInSlot(slot).getCount()));
                fishNetTileEntity.markDirty();
            }
            return ItemStack.EMPTY;
        }
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack stack = getStackInSlot(slot);
        if (stack.isEmpty() ||
                slot < 1 || slot >= getSlots() ||
                amount < 1) {
            return ItemStack.EMPTY;
        } else if (amount >= stack.getCount()) {
            if(!simulate) {
                fishNetTileEntity.getInventory().setInventorySlotContents(slot, ItemStack.EMPTY);
            }
            return stack;
        } else {
            stack.shrink(amount);
            return stack.copy().split(amount);
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return getStackInSlot(slot).getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if(slot == 0) {
            return stack.getItem() == Items.FISHING_ROD;
        }
        return true;
    }
}
