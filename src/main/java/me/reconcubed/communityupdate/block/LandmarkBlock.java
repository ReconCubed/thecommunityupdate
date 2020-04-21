package me.reconcubed.communityupdate.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class LandmarkBlock extends Block {
    public static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(2, 0, 2, 14, 2, 14),
            Block.makeCuboidShape(3, 2, 3, 13, 18, 13),
            Block.makeCuboidShape(1, 18, 1, 15, 21, 15),
            Block.makeCuboidShape(13.5, 21, 2.5, 13.5, 30, 13.5),
            Block.makeCuboidShape(2.5, 21, 2.5, 2.5, 30, 13.5),
            Block.makeCuboidShape(2.5, 21, 13.5, 13.5, 30, 13.5),
            Block.makeCuboidShape(2.5, 30, 2.5, 13.5, 30, 13.5),
            Block.makeCuboidShape(4, 21, 4, 12, 29, 12)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();

    public LandmarkBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!(placer instanceof PlayerEntity) || worldIn.isRemote) {
            return;
        }
        this.forceChunkLoad(pos, worldIn);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (worldIn.isRemote) {
            return;
        }
        this.removeChunkLoad(pos, worldIn);

    }

    private void forceChunkLoad(BlockPos pos, World worldIn) {
        forceLoadHandler(pos, "add", worldIn);
    }

    private void removeChunkLoad(BlockPos pos, World worldIn) {
        forceLoadHandler(pos, "remove", worldIn);
    }

    private void forceLoadHandler(BlockPos pos, String choice, World worldIn) {
        if (worldIn == null || worldIn.getServer() == null) {
            return;
        }
        CommandSource source = worldIn.getServer().getCommandSource().func_201003_a((ServerWorld) worldIn);
        int cmd = worldIn.getServer().getCommandManager().handleCommand(source, "forceload " + choice + " " + pos.getX() + " " + pos.getZ());
    }
}
