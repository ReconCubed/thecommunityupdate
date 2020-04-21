package me.reconcubed.communityupdate;

import me.reconcubed.communityupdate.config.ConfigHolder;
import me.reconcubed.communityupdate.config.ServerConfig;
import me.reconcubed.communityupdate.init.ModBlocks;
import me.reconcubed.communityupdate.init.ModFeatures;
import me.reconcubed.communityupdate.init.ModStructurePieceType;
import me.reconcubed.communityupdate.world.gen.feature.MinersCampConfig;
import me.reconcubed.communityupdate.world.gen.feature.structure.MinersCamp;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CommunityUpdate.MODID)
public class CommunityUpdate {
    public static final String MODID = "communityupdate";

//    Hexed Earth
    public static final Tag<EntityType<?>> blacklisted_entities = new EntityTypeTags.Wrapper(new ResourceLocation(MODID, "blacklisted"));
    public static final Tag<Block> spreadable = new BlockTags.Wrapper(new ResourceLocation(MODID, "spreadable"));

    final ModLoadingContext modLoadingContext = ModLoadingContext.get();
    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public CommunityUpdate() {
        // Register Deferred Registers (Does not need to be before Configs)
        ModBlocks.BLOCKS.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);

        ModStructurePieceType.init();
//        ModItems.ITEMS.register(modEventBus);
//        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
//        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
//        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);

        // Register Configs (Does not need to be after Deferred Registers)
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);

        MinecraftForge.EVENT_BUS.addListener(this::useRoseOnDirt);
        MinecraftForge.EVENT_BUS.addListener(this::onCommonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }



    private void useRoseOnDirt(PlayerInteractEvent.RightClickBlock e) {
        if (!ServerConfig.witherRose.get()) return;
        PlayerEntity p = e.getPlayer();
        World w = p.world;
        BlockPos pos = e.getPos();
        if (p.isSneaking() && !w.isRemote && e.getItemStack().getItem() == Items.WITHER_ROSE && w.getBlockState(pos).getBlock() == Blocks.DIRT) {
            w.setBlockState(pos, ModBlocks.VEXED_EARTH.get().getDefaultState());
        }
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        this.addMinersCamp(Biomes.PLAINS, MinersCamp.MINERS_CAMP);
    }

    private void addMinersCamp(Biome biome, ResourceLocation templateLocation)
    {
        biome.addStructure(ModFeatures.MINERS_CAMP.get(), new MinersCampConfig(ServerConfig.minersCampGenerateChance.get(), templateLocation));
        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
                Biome.createDecoratedFeature(
                        ModFeatures.MINERS_CAMP.get(),
                        new MinersCampConfig(ServerConfig.minersCampGenerateChance.get(), templateLocation),
                        Placement.NOPE,
                        IPlacementConfig.NO_PLACEMENT_CONFIG
                    ));
    }



}
