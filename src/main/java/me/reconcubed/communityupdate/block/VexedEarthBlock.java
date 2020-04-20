package me.reconcubed.communityupdate.block;

import me.reconcubed.communityupdate.CommunityUpdate;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.reconcubed.communityupdate.config.ServerConfig.*;

/**
 * @author Tfarcenim
 * Original Repo License: UNLICENSE
 * Permits redistribution & modification for private & commercial use.
 */
public class VexedEarthBlock extends GrassBlock {
    public VexedEarthBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        world.getPendingBlockTicks().scheduleTick(pos, state.getBlock(), world.rand.nextInt(maxTickTime.get() - minTickTime.get() + 1));
    }

    @Override
    public boolean onBlockActivated(BlockState p_220051_1_, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult p_220051_6_) {
        if (player.getHeldItemMainhand().isEmpty() && player.isSneaking() && !world.isRemote && hand == Hand.MAIN_HAND) {

            ServerChunkProvider s = (ServerChunkProvider) world.getChunkProvider();

            List<SpawnDetail> spawnInfo = new ArrayList<>();

            BlockPos up = pos.up();

            List<Biome.SpawnListEntry> entries = s.getChunkGenerator().getPossibleCreatures(EntityClassification.MONSTER, up);
            // nothing can spawn, occurs in places such as mushroom biomes
            if (entries.size() == 0) {
                player.sendMessage(new TranslationTextComponent("text.communityupdate.nospawns"));
                return true;
            } else {
                for (Biome.SpawnListEntry entry : entries) {
                    spawnInfo.add(new SpawnDetail(entry));
                }
                ITextComponent names1 = new TranslationTextComponent("Names: ");
                for (SpawnDetail detail : spawnInfo) {
                    names1.appendSibling(new TranslationTextComponent(detail.displayName)).appendSibling(new StringTextComponent(", "));
                }
                player.sendMessage(names1);
            }
            return true;
        }
        return false;
    }

    public static class SpawnDetail {

        private String displayName;

        //    private boolean lightEnabled = true;
        public SpawnDetail(Biome.SpawnListEntry entry) {
            displayName = entry.entityType.getTranslationKey().replace("Entity", "");
        }
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.isRemote) {
            if (!world.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (isInDaylight(world, pos) && diesInSunlight.get()) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            } else {
                if (world.getLight(pos.up()) <= 7 && naturallySpreads.get() && world.getBlockState(pos.up()).isAir(null,null)) {
                    BlockState blockstate = this.getDefaultState();
                    for (int i = 0; i < 4; ++i) {
                        BlockPos pos1 = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                        if (world.getBlockState(pos1).getBlock().isIn(CommunityUpdate.spreadable) && world.getBlockState(pos1.up()).isAir(world,pos1.up())) {
                            world.setBlockState(pos1, blockstate.with(SNOWY, world.getBlockState(pos1.up()).getBlock() == Blocks.SNOW));
                        }
                    }
                }
            }

            world.getPendingBlockTicks().scheduleTick(pos, state.getBlock(), random.nextInt(maxTickTime.get() - minTickTime.get() + 1));
            //dont spawn in water
            if (!world.getFluidState(pos.up()).isEmpty()) return;
            //don't spawn in peaceful
            if (world.getWorldInfo().getDifficulty() == Difficulty.PEACEFUL) return;
            //mobcap used because mobs are laggy in large numbers
            long mobcount = ((ServerWorld) world).getEntities().filter(IMob.class::isInstance).count();
            if (mobcount > mobCap.get()) return;
            int r = spawnRadius.get();
            if (world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(-r, -r, -r, r, r, r)).size() > 0) return;
            MobEntity mob = findMonsterToSpawn(world, pos.up(), random);
            if (mob != null) {
                mob.setPosition(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);
                if (!world.areCollisionShapesEmpty(mob) || !world.checkNoEntityCollision(mob)) return;
                world.addEntity(mob);
            }
        }
    }

    @Override
    public boolean canGrow(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_, boolean p_176473_4_) {
        return false;//no
    }

    @Override
    public void grow(World world, Random random, BlockPos pos, BlockState state) {
        //no
    }

    @Override
    public boolean canUseBonemeal(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_) {
        return false;//no
    }

    public boolean isInDaylight(World world, BlockPos pos) {
        return world.isDaytime() && world.getBrightness(pos.up()) > 0.5F && world.isSkyLightMax(pos.up());
    }

    private MobEntity findMonsterToSpawn(World world, BlockPos pos, Random rand) {
        //required to account for structure based mobs such as wither skeletons
        ServerChunkProvider s = (ServerChunkProvider) world.getChunkProvider();
        List<Biome.SpawnListEntry> spawnOptions = s.getChunkGenerator().getPossibleCreatures(EntityClassification.MONSTER, pos);
        //there is nothing to spawn
        if (spawnOptions.size() == 0) {
            return null;
        }
        int found = rand.nextInt(spawnOptions.size());
        Biome.SpawnListEntry entry = spawnOptions.get(found);
        //can the mob actually spawn here naturally, filters out mobs such as slimes which have more specific spawn requirements but
        // still show up in spawnlist; ignore them when force spawning
        if (!EntitySpawnPlacementRegistry.func_223515_a(entry.entityType, world, SpawnReason.NATURAL, pos, world.rand)
                && !forceSpawn.get() || CommunityUpdate.blacklisted_entities.contains(entry.entityType))
            return null;
        EntityType type = entry.entityType;
        Entity ent = type.create(world);
        //cursed earth only works with hostiles
        if (!(ent instanceof MobEntity))return null;
        ((MobEntity)ent).onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.NATURAL, null, null);
        return (MobEntity) ent;
    }

    // TODO: Fire burns Vexed Earth back to dirt + config

}
