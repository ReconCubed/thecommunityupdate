package me.reconcubed.communityupdate.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class PebbleBagBaseItem extends Item {
    private BlockState block;

    public PebbleBagBaseItem(BlockState blockState, Properties properties) {
        super(properties);

        block = blockState;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote) {
            PlayerEntity playerEntity = context.getPlayer();
            if (playerEntity != null) {
                BlockPos blockPos = context.getPos();
                BlockState blockState = context.getWorld().getBlockState(blockPos);

                if (!blockState.isReplaceable(new BlockItemUseContext(context))) {
                    blockPos = blockPos.offset(context.getFace());
                }

                context.getWorld().setBlockState(blockPos, this.block);
                context.getItem().damageItem(1, playerEntity, (p) ->
                        p.sendBreakAnimation(context.getHand()));
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }
}
