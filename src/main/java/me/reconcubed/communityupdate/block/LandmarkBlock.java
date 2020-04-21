package me.reconcubed.communityupdate.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

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
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

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

}
