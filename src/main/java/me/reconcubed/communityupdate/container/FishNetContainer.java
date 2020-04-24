package me.reconcubed.communityupdate.container;

import me.reconcubed.communityupdate.init.ModBlocks;
import me.reconcubed.communityupdate.init.ModContainerTypes;
import me.reconcubed.communityupdate.init.ModTileEntityTypes;
import me.reconcubed.communityupdate.tileentity.FishNetTileEntity;
import me.reconcubed.communityupdate.util.FishingRodSlot;
import me.reconcubed.communityupdate.util.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class FishNetContainer extends Container {
    private final FishNetTileEntity tileEntity;
    private final IInventory inventory;
//    private final IWorldPosCallable canInteractWithCallable;
    private final PlayerInventory playerInventory;

    public FishNetContainer(int windowId, PlayerInventory playerInventory, FishNetTileEntity tileEntity) {
        super(ModContainerTypes.FISH_NET.get(), windowId);
        this.tileEntity = tileEntity;
        this.playerInventory = playerInventory;
        this.inventory = tileEntity.getInventory();
//        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        int slotNum = 0;

        // Fishing Rod Slot
        this.addSlot(new FishingRodSlot(inventory, slotNum++, 12, 35));

        // Fishing Net Inventory
        int startX = 39;
        int startY = 17;

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 7; ++column) {
                this.addSlot(new OutputSlot(inventory,
                        slotNum++,
                        startX + (column * 18),
                        startY + (row * 18)
                ));
            }
        }


        // Player Inventory
        int playerStartY = 85;

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory,
                        9 + (row * 9),
                        8 + (column * 18),
                        playerStartY + (row * 18)
                ));
            }
        }

        // Hotbar
        int hotbarStartY = 143;

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory,
                    column,
                    8 + (column * 18),
                    hotbarStartY
            ));
        }

    }
    private static FishNetTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory can not be null.");
        Objects.requireNonNull(data, "data can not be null.");

        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());

        if (tileAtPos instanceof FishNetTileEntity) {
            return (FishNetTileEntity) tileAtPos;
        }

        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }


    public FishNetContainer(int windowId, PlayerInventory playerInventory, PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
//        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlocks.FISH_NET.get());
        return this.inventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (slot.getStack() == ItemStack.EMPTY) {
            return ItemStack.EMPTY;
        }

        ItemStack item = slot.getStack().copy();

        if (slot.inventory == playerIn.inventory) {
            ItemStack fishingRodItem = slot.getStack();
            Slot fishingRodSlot = this.inventorySlots.get(0);

            if (isFishingRod(fishingRodItem) && !fishingRodSlot.getHasStack()) {
                fishingRodSlot.putStack(fishingRodItem);
                slot.putStack(ItemStack.EMPTY);
                return item;
            }
            return ItemStack.EMPTY;
        } else {
            if(playerInventory.addItemStackToInventory(item)) {
                slot.putStack(ItemStack.EMPTY);
                return item;
            } else {
                return ItemStack.EMPTY;
            }
        }

    }

    private boolean isFishingRod(ItemStack itemStack) {
        Item fishingRodItem = Items.FISHING_ROD;
        return itemStack.getItem() == fishingRodItem;
    }

}
