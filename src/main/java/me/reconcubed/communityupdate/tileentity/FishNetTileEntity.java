package me.reconcubed.communityupdate.tileentity;

import me.reconcubed.communityupdate.block.FishNetBlock;
import me.reconcubed.communityupdate.container.FishNetContainer;
import me.reconcubed.communityupdate.init.ModTileEntityTypes;
import me.reconcubed.communityupdate.util.FishNetInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static me.reconcubed.communityupdate.config.ServerConfig.fishNetMaxTickTime;
import static me.reconcubed.communityupdate.config.ServerConfig.fishNetMinTickTime;

public class FishNetTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private FishNetInventory inventory = new FishNetInventory(this);
    private LazyOptional inventoryHolder = LazyOptional.of(() -> inventory.getItemHandler());
    private long tickCounter = 0;
    private long minTick;
    private long maxTick;
    private long waitTime;

    public FishNetTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);

        this.minTick = fishNetMinTickTime.get();
        this.maxTick = fishNetMaxTickTime.get();
        this.waitTime = 200;
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
        if (tickCounter >= this.waitTime) {
            tickCounter = 0;
            if (validateWaterAndRod()) {
                useFishingRod();
            }
        } else {
            tickCounter++;
        }
    }

    private boolean validateWaterAndRod() {
        if (!world.isRemote) {
            Iterable<BlockPos> waterCheckIterator = BlockPos.getAllInBoxMutable(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1), new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() + 1));

            for (BlockPos blockPos : waterCheckIterator) {
                Block block = world.getBlockState(blockPos).getBlock();
                // If not submerged, returns false
                if (world.getTileEntity(pos) != null && (block != Blocks.WATER && !(block instanceof FishNetBlock))) {
                    return false;
                }
            }
            return this.getInventory().getStackInSlot(0).getItem() == Items.FISHING_ROD;
        }
        return false;
    }

    private void useFishingRod() {
        ItemStack fishingRod = this.getInventory().getStackInSlot(0);
        LootContext.Builder lootContextBuilder = (new LootContext.Builder((ServerWorld) this.world))
                .withParameter(LootParameters.POSITION, new BlockPos(pos))
                .withParameter(LootParameters.TOOL, fishingRod)
                .withRandom(world.rand);

        LootTable lootTable = Objects.requireNonNull(this.world.getServer().getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING));

        List<ItemStack> list = lootTable.generate(lootContextBuilder.build(LootParameterSets.FISHING));

        inventory.getItemHandler().addListToInventory(list);

        if (fishingRod.getItem() == Items.FISHING_ROD) {
            fishingRod.attemptDamageItem(1, world.rand, null);
            markDirty();
        }

        int min = (int) this.minTick;
        int max = (int) this.maxTick;

        this.waitTime = (long) this.world.rand.nextInt(max - min + 1);

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
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (side == Direction.DOWN || side == Direction.UP) {
            return cap.orEmpty(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventoryHolder);
        } else {
            return LazyOptional.empty();
        }
    }

    public FishNetInventory getInventory() {
        return inventory;
    }

}
