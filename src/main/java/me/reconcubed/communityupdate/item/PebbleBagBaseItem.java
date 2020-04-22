package me.reconcubed.communityupdate.item;

import me.reconcubed.communityupdate.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class PebbleBagBaseItem extends Item {
    public PebbleBagBaseItem(Properties properties) {
        super(properties);
    }

//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        if (!worldIn.isRemote) {
//            BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) playerIn.pick(3.0D, 0.0F, false);
//            BlockPos blockPos = rayTraceResult.getPos().offset(rayTraceResult.getFace());
//            playerIn.sendMessage(new TranslationTextComponent(blockPos.toString()));
//
//        }
//        return super.onItemRightClick(worldIn, playerIn, handIn);
//    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote) {
            PlayerEntity playerEntity = context.getPlayer();

            if (playerEntity != null) {
                BlockPos blockPos = context.getPos().offset(context.getFace());
                playerEntity.sendMessage(new TranslationTextComponent(blockPos.toString()));
                BlockState blockState = context.getWorld().getBlockState(blockPos);

                if (!blockState.isReplaceable((BlockItemUseContext) context)) {
                    blockPos = blockPos.offset(context.getFace());
                }

                context.getWorld().setBlockState(blockPos, ModBlocks.COBBLESTONE_PATH.get().getDefaultState());

            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }
}
