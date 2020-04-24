package me.reconcubed.communityupdate.tileentity;

import me.reconcubed.communityupdate.container.FishNetContainer;
import me.reconcubed.communityupdate.init.ModTileEntityTypes;
import me.reconcubed.communityupdate.util.FishNetInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class FishNetTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private FishNetInventory inventory = new FishNetInventory(this);
    private LazyOptional inventoryHolder = LazyOptional.of(() -> inventory.getItemHandler());


    public FishNetTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public FishNetTileEntity() {
        this(ModTileEntityTypes.FISH_NET.get());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.communityupdate.fish_net");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FishNetContainer(id, playerInventory, this);
    }

    @Override
    public void tick() {

    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory.getContents());
        return compound;
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.setContents(NonNullList.withSize(this.getInventory().getSizeInventory(), ItemStack.EMPTY));
        ItemStackHelper.loadAllItems(compound, this.inventory.getContents());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
    {
        if(side == Direction.DOWN || side == Direction.UP) {
            return cap.orEmpty(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventoryHolder);
        } else {
            return LazyOptional.empty();
        }
    }

    public FishNetInventory getInventory() {
        return inventory;
    }

}
