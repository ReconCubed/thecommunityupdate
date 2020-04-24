package me.reconcubed.communityupdate.util;

import me.reconcubed.communityupdate.tileentity.FishNetTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Optional;

public class FishNetInventory implements IInventory {
    public static final int SLOTS = 22;
    private NonNullList<ItemStack> contents = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
    private FishNetItemHandler fishNetItemHandler;

    public FishNetInventory(FishNetTileEntity tileEntity) {
        fishNetItemHandler = new FishNetItemHandler(tileEntity);
    }

    @Override
    public int getSizeInventory() {
        return SLOTS;
    }

    @Override
    public boolean isEmpty() {
        return contents.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return contents.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return fishNetItemHandler.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        Optional<ItemStack> stack = Optional.ofNullable(getStackInSlot(index));
        if (stack.isPresent() && !stack.get().isEmpty()) {
            setInventorySlotContents(index, ItemStack.EMPTY);
        }
        return stack.get();
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        contents.set(index, stack);
    }

    @Override
    public void markDirty() {
        fishNetItemHandler.fishNetTileEntity.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        contents.clear();
    }

    public FishNetItemHandler getItemHandler() {
        return fishNetItemHandler;
    }

    public NonNullList<ItemStack> getContents() {
        return contents;
    }

    public void setContents(NonNullList<ItemStack> contents) {
        this.contents = contents;
    }
}
