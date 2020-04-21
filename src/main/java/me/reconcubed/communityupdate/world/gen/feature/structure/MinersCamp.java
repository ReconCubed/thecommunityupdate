package me.reconcubed.communityupdate.world.gen.feature.structure;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.init.ModStructurePieceType;
import me.reconcubed.communityupdate.world.gen.feature.MinersCampConfig;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.*;

import java.util.Random;

public class MinersCamp {
    public static final ResourceLocation MINERS_CAMP = new ResourceLocation(CommunityUpdate.MODID, "miners_camp");

    public static class Piece extends TemplateStructurePiece
    {
        private static final ResourceLocation CHESTS_SURVIVAL_CAMP_CHEST = new ResourceLocation(CommunityUpdate.MODID, "chests/miners_camp");

        private Rotation rotation;
        private ResourceLocation templateLocation;

        public Piece(TemplateManager manager, BlockPos pos, Rotation rotation, MinersCampConfig config)
        {
            super(ModStructurePieceType.MINERS_CAMP, 0);
            this.rotation = rotation;
            this.templatePosition = pos;
            this.templateLocation = config.template;
            this.loadTemplate(manager);
        }

        public Piece(TemplateManager manager, CompoundNBT compound)
        {
            super(ModStructurePieceType.MINERS_CAMP, compound);
            this.rotation = Rotation.valueOf(compound.getString("Rot"));
            this.templateLocation = new ResourceLocation(compound.getString("Template"));
            this.loadTemplate(manager);
        }

        private void loadTemplate(TemplateManager manager)
        {
            Template template = manager.getTemplateDefaulted(this.templateLocation);
            PlacementSettings settings = (new PlacementSettings()).setRotation(this.rotation).setCenterOffset(new BlockPos(5, 0, 5)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK).addProcessor(JigsawReplacementStructureProcessor.INSTANCE);
            this.setup(template, this.templatePosition, settings);
        }

        @Override
        protected void readAdditional(CompoundNBT compound)
        {
            super.readAdditional(compound);
            compound.putString("Rot", this.rotation.name());
            compound.putString("Template", this.templateLocation.toString());
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb)
        {
            if("chest".equals(function))
            {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                TileEntity tileEntity = worldIn.getTileEntity(pos.down());
                if(tileEntity instanceof ChestTileEntity)
                {
                    ((ChestTileEntity) tileEntity).setLootTable(CHESTS_SURVIVAL_CAMP_CHEST, rand.nextLong());
                }
            }
        }

        @Override
        public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox bounds, ChunkPos chunkPos)
        {
            int posY = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, this.templatePosition.getX(), this.templatePosition.getZ()) - 1;
            this.templatePosition = new BlockPos(this.templatePosition.getX(), posY, this.templatePosition.getZ());

            switch(this.rotation) //Fixes the off center rotation
            {
                case CLOCKWISE_90:
                    this.templatePosition = this.templatePosition.add(-1, 0, 0);
                    break;
                case CLOCKWISE_180:
                    this.templatePosition = this.templatePosition.add(-1, 0, -1);
                    break;
                case COUNTERCLOCKWISE_90:
                    this.templatePosition = this.templatePosition.add(0, 0, -1);
                    break;
            }

            return super.addComponentParts(world, rand, bounds, chunkPos);
        }
    }

}
