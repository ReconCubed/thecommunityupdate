package me.reconcubed.communityupdate.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class TorchButtonBlock extends ButtonBase {
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, Block.makeCuboidShape(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, Block.makeCuboidShape(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

    public TorchButtonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader worldIn, BlockPos blockPos, ISelectionContext context) {
        return func_220289_j(blockState);
    }

    public static VoxelShape func_220289_j(BlockState blockState) {
        return SHAPES.get(blockState.get(HORIZONTAL_FACING));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos blockPos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            if (state.get(POWERED)) {
                return true;
            } else {
                worldIn.setBlockState(blockPos, state.with(POWERED, Boolean.valueOf(true)), 3);
                createRedstoneParticle(state, worldIn, blockPos, 1.0F);
                worldIn.playSound((PlayerEntity) null, blockPos, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
                this.updateNeighbors(state, worldIn, blockPos);
                worldIn.getPendingBlockTicks().scheduleTick(blockPos, this, this.tickRate(worldIn));
            }
        }
        return true;
    }

    private static void createRedstoneParticle(BlockState blockState, World worldIn, BlockPos blockPos, float alpha) {
        Direction direction = blockState.get(HORIZONTAL_FACING).getOpposite();
        Direction direction1 = getFacing(blockState).getOpposite();
        double d0 = blockPos.getX() + 0.5D + 0.1D * direction.getXOffset() + 0.2D * direction1.getXOffset();
        double d1 = blockPos.getY() + 0.5D + 0.1D * direction.getYOffset() + 0.2D * direction1.getYOffset();
        double d2 = blockPos.getZ() + 0.5D + 0.1D * direction.getZOffset() + 0.2D * direction1.getZOffset();
        worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, alpha), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    // Placement
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext useContext) {
        IWorldReader worldReader = useContext.getWorld();
        Direction[] nearestLookDir = useContext.getNearestLookingDirections();
        BlockPos blockPos = useContext.getPos();
        BlockState blockState = this.getDefaultState();

        for(Direction direction : nearestLookDir) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockState = blockState.with(HORIZONTAL_FACING, direction1);
                if (blockState.isValidPosition(worldReader, blockPos)) {
                    return blockState;
                }
            }
        }

        return null;
    }

    @Override
    public void onReplaced(BlockState blockState, World worldIn, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (!isMoving && blockState.getBlock() != newState.getBlock()) {
            if (blockState.get(POWERED)) {
                this.updateNeighbors(blockState, worldIn, blockPos);
            }

            super.onReplaced(blockState, worldIn, blockPos, newState, isMoving);
        }
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader worldIn, BlockPos blockPos) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        BlockPos blockpos = blockPos.offset(direction.getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.func_224755_d(worldIn, blockpos, direction);
    }

    // Animation
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World worldIn, BlockPos blockPos, Random rand) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        double d0 = blockPos.getX() + 0.5D;
        double d1 = blockPos.getY() + 0.7D;
        double d2 = blockPos.getZ() + 0.5D;

        Direction direction1 = direction.getOpposite();
        if(!blockState.get(POWERED))
        {
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * direction1.getXOffset(), d1 + 0.22D, d2 + 0.27D * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + 0.27D * direction1.getXOffset(), d1 + 0.22D, d2 + 0.27D * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
        }
        else
        {
            double k = 0.055D;
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + k * direction1.getXOffset(), d1 + 0.1D, d2 + k * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + k * direction1.getXOffset(), d1 + 0.1D, d2 + k * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, 1.0F), d0 + k * direction1.getXOffset(), d1 + 0.1D, d2 + k * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
        }

    }

}
