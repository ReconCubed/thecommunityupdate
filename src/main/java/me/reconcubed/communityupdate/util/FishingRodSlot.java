package me.reconcubed.communityupdate.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class FishingRodSlot extends Slot {
    public FishingRodSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == Items.FISHING_ROD;
    }
}
