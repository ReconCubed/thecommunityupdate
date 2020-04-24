package me.reconcubed.communityupdate.block;


import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;

public class FishNetBlock extends Block {
    public FishNetBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }


}
