package me.reconcubed.communityupdate.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import me.reconcubed.communityupdate.init.ModFeatures;
import me.reconcubed.communityupdate.world.gen.feature.structure.MinersCamp;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

public class MinersCampStructure extends Structure<MinersCampConfig> {

    public MinersCampStructure(Function<Dynamic<?>, ? extends MinersCampConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    @Nullable
    @Override
    public BlockPos findNearest(World worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, BlockPos pos, int radius, boolean p_211405_5_) {
        return super.findNearest(worldIn, chunkGenerator, pos, radius, p_211405_5_);
    }

    @Override
    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
        for(Biome biome : chunkGen.getBiomeProvider().getBiomesInSquare(chunkPosX * 16 + 9, chunkPosZ * 16 + 9, 32)) {
            if(chunkGen.hasStructure(biome, this))
            {
                ((SharedSeedRandom) rand).setLargeFeatureSeedWithSalt(chunkGen.getSeed(), chunkPosX, chunkPosZ, 0xF00D);
                MinersCampConfig config = chunkGen.getStructureConfig(biome, this);
                return config != null && rand.nextInt(config.chance) == 0;
            }
        }

        return false;
    }

    @Override
    public IStartFactory getStartFactory() {
        return MinersCampStart::new;
    }

    @Override
    public String getStructureName() {
        return this.getRegistryName().toString();
    }

    @Override
    public int getSize() {
        return 2;
    }
    public static class MinersCampStart extends StructureStart
    {
        public MinersCampStart(Structure<?> structureIn, int chunkX, int chunkZ, Biome biomeIn, MutableBoundingBox boundsIn, int referenceIn, long seed) {
            super(structureIn, chunkX, chunkZ, biomeIn, boundsIn, referenceIn, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager manager, int chunkX, int chunkZ, Biome biome)
        {
            int posX = chunkX << 4;
            int posZ = chunkZ << 4;
            int height1 = generator.func_222532_b(posX + 3, posZ + 3, Heightmap.Type.OCEAN_FLOOR_WG);
            int height2 = generator.func_222532_b(posX + 13, posZ + 3, Heightmap.Type.OCEAN_FLOOR_WG);
            int height3 = generator.func_222532_b(posX + 3, posZ + 13, Heightmap.Type.OCEAN_FLOOR_WG);
            int height4 = generator.func_222532_b(posX + 13, posZ + 13, Heightmap.Type.OCEAN_FLOOR_WG);
            if(height1 == height2 && height1 == height3 && height1 == height4 && height1 >= generator.getSeaLevel())
            {
                BlockPos pos = new BlockPos(posX + 3, 90, posZ + 3);
                Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
                MinersCampConfig config = generator.getStructureConfig(biome, ModFeatures.MINERS_CAMP.get());
                if(config != null)
                {
                    this.components.add(new MinersCamp.Piece(manager, pos, rotation, config));
                    this.recalculateStructureSize();
                }
            }
        }

//        @Override
//        public BlockPos getPos()
//        {
//            return new BlockPos((this.getChunkPosX() << 4) + 3, 0, (this.getChunkPosZ() << 4) + 3);
//        }
//
//        @Override
//        public void generateStructure(IWorld worldIn, Random rand, MutableBoundingBox structurebb, ChunkPos pos) {
//            super.generateStructure(worldIn, rand, structurebb, pos);
//        }

    }

}
