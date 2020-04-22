package me.reconcubed.communityupdate.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.stream.Stream;

public class WishingWellBlock extends Block {

    public static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(0, 2, 14, 2, 16, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
            Block.makeCuboidShape(0, 2, 2, 2, 16, 14),
            Block.makeCuboidShape(14, 2, 2, 16, 16, 14),
            Block.makeCuboidShape(2, 2, 14, 14, 16, 16),
            Block.makeCuboidShape(2, 2, 0, 14, 16, 2),
            Block.makeCuboidShape(14, 2, 0, 16, 16, 2),
            Block.makeCuboidShape(14, 2, 14, 16, 16, 16),
            Block.makeCuboidShape(0, 2, 0, 2, 16, 2)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public WishingWellBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(worldIn.isRemote) return;
        if (entityIn instanceof ItemEntity) {
            ItemStack itemStack = ((ItemEntity) entityIn).getItem();
            Item item = itemStack.getItem();
            UUID thrower = ((ItemEntity) entityIn).getThrowerId();
            if (thrower != null) {
                PlayerEntity player = worldIn.getPlayerByUuid(thrower);

                if (item == Items.GOLD_NUGGET) {
                    this.addLuck(player, entityIn, 2000, 1, worldIn, pos);
                } else if (item == Items.GOLD_INGOT) {
                    this.addLuck(player, entityIn, 3500, 2, worldIn, pos);
                } else if (item == Items.GOLD_BLOCK) {
                    this.addLuck(player, entityIn, 10000, 2, worldIn, pos);
                }
            }
        }
    }

    private void addLuck(PlayerEntity player, Entity entityIn, int duration, int amplifier, World worldIn, BlockPos pos) {
        assert player != null;
        player.addPotionEffect(new EffectInstance(Effects.LUCK, duration, amplifier, false, false));
        worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 0.3F, 0.6F);
        entityIn.remove();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if (itemstack.isEmpty()) {
            return true;
        } else {
            Item item = itemstack.getItem();

            if (item == Items.BUCKET) {
                if (!worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, new ItemStack(Items.WATER_BUCKET));
                        } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                            player.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                        }
                    }
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return true;
        }
    }
}
