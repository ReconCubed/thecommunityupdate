package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.block.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, CommunityUpdate.MODID);

    public static final RegistryObject<Block> VEXED_EARTH = BLOCKS.register("vexed_earth", () -> new VexedEarthBlock(
            Block.Properties.create(Material.ORGANIC)
                    .hardnessAndResistance(0.5F)
                    .sound(SoundType.PLANT)
                    .harvestTool(ToolType.SHOVEL)));

    public static final RegistryObject<Block> BOOKSHELF_LEVER = BLOCKS.register("bookshelf_lever", () -> new BookshelfLeverBlock(
            Block.Properties.create(Material.WOOD)
                    .hardnessAndResistance(1.5F)
                    .sound(SoundType.WOOD)
                    .harvestTool(ToolType.AXE)));

    public static final RegistryObject<Block> TORCH_LEVER = BLOCKS.register("torch_lever", () -> new TorchLeverBlock(
            Block.Properties.create(Material.MISCELLANEOUS)
                    .hardnessAndResistance(0)
                    .sound(SoundType.WOOD)
                    .lightValue(14)
                    .doesNotBlockMovement()));

    public static final RegistryObject<Block> BOOKSHELF_BUTTON = BLOCKS.register("bookshelf_button", () -> new BookshelfButtonBlock(
            Block.Properties.create(Material.WOOD)
                    .hardnessAndResistance(1.5F)
                    .sound(SoundType.WOOD)
                    .harvestTool(ToolType.AXE)));

    public static final RegistryObject<Block> TORCH_BUTTON = BLOCKS.register("torch_button", () -> new TorchButtonBlock(
            Block.Properties.create(Material.MISCELLANEOUS)
                    .hardnessAndResistance(0)
                    .sound(SoundType.WOOD)
                    .lightValue(14)
                    .doesNotBlockMovement()));

    public static final RegistryObject<Block> INVERTED_REDSTONE_LAMP = BLOCKS.register("inverted_redstone_lamp", () -> new InvertedRedstoneLampBlock(
            Block.Properties.create(Material.REDSTONE_LIGHT)
                    .lightValue(15)
                    .hardnessAndResistance(0.3F)
                    .sound(SoundType.GLASS)));

    public static final RegistryObject<Block> LANDMARK = BLOCKS.register("landmark", () -> new LandmarkBlock(
            Block.Properties.create(Material.ROCK)
                    .lightValue(7)
                    .hardnessAndResistance(1.5F, 9.0F)
                    .sound(SoundType.STONE)));

    /**
     * ----------------    CONCRETE STAIRS    ----------------
     */

    public static final RegistryObject<Block> WHITE_CONCRETE_STAIRS = BLOCKS.register("white_concrete_stairs", () -> new StairsBlock(Blocks.WHITE_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.WHITE_CONCRETE)));

    public static final RegistryObject<Block> ORANGE_CONCRETE_STAIRS = BLOCKS.register("orange_concrete_stairs", () -> new StairsBlock(Blocks.ORANGE_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.ORANGE_CONCRETE)));

    public static final RegistryObject<Block> MAGENTA_CONCRETE_STAIRS = BLOCKS.register("magenta_concrete_stairs", () -> new StairsBlock(Blocks.MAGENTA_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.MAGENTA_CONCRETE)));

    public static final RegistryObject<Block> LIGHT_BLUE_CONCRETE_STAIRS = BLOCKS.register("light_blue_concrete_stairs", () -> new StairsBlock(Blocks.LIGHT_BLUE_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.LIGHT_BLUE_CONCRETE)));

    public static final RegistryObject<Block> CYAN_CONCRETE_STAIRS = BLOCKS.register("cyan_concrete_stairs", () -> new StairsBlock(Blocks.CYAN_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.CYAN_CONCRETE)));

    public static final RegistryObject<Block> YELLOW_CONCRETE_STAIRS = BLOCKS.register("yellow_concrete_stairs", () -> new StairsBlock(Blocks.YELLOW_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.YELLOW_CONCRETE)));

    public static final RegistryObject<Block> LIME_CONCRETE_STAIRS = BLOCKS.register("lime_concrete_stairs", () -> new StairsBlock(Blocks.LIME_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.LIME_CONCRETE)));

    public static final RegistryObject<Block> PINK_CONCRETE_STAIRS = BLOCKS.register("pink_concrete_stairs", () -> new StairsBlock(Blocks.PINK_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.PINK_CONCRETE)));

    public static final RegistryObject<Block> GRAY_CONCRETE_STAIRS = BLOCKS.register("gray_concrete_stairs", () -> new StairsBlock(Blocks.GRAY_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.GRAY_CONCRETE)));

    public static final RegistryObject<Block> LIGHT_GRAY_CONCRETE_STAIRS = BLOCKS.register("light_gray_concrete_stairs", () -> new StairsBlock(Blocks.LIGHT_GRAY_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.LIGHT_GRAY_CONCRETE)));

    public static final RegistryObject<Block> BLUE_CONCRETE_STAIRS = BLOCKS.register("blue_concrete_stairs", () -> new StairsBlock(Blocks.BLUE_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.BLUE_CONCRETE)));

    public static final RegistryObject<Block> PURPLE_CONCRETE_STAIRS = BLOCKS.register("purple_concrete_stairs", () -> new StairsBlock(Blocks.PURPLE_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.PURPLE_CONCRETE)));

    public static final RegistryObject<Block> GREEN_CONCRETE_STAIRS = BLOCKS.register("green_concrete_stairs", () -> new StairsBlock(Blocks.GREEN_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.GREEN_CONCRETE)));

    public static final RegistryObject<Block> BROWN_CONCRETE_STAIRS = BLOCKS.register("brown_concrete_stairs", () -> new StairsBlock(Blocks.BROWN_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.BROWN_CONCRETE)));

    public static final RegistryObject<Block> RED_CONCRETE_STAIRS = BLOCKS.register("red_concrete_stairs", () -> new StairsBlock(Blocks.RED_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.RED_CONCRETE)));

    public static final RegistryObject<Block> BLACK_CONCRETE_STAIRS = BLOCKS.register("black_concrete_stairs", () -> new StairsBlock(Blocks.BLACK_CONCRETE::getDefaultState,
            Block.Properties.from(Blocks.BLACK_CONCRETE)));

    /**
     * ----------------    CONCRETE SLABS    ----------------
     */

    public static final RegistryObject<Block> WHITE_CONCRETE_SLAB = BLOCKS.register("white_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.WHITE_CONCRETE)));

    public static final RegistryObject<Block> ORANGE_CONCRETE_SLAB = BLOCKS.register("orange_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.ORANGE_CONCRETE)));

    public static final RegistryObject<Block> MAGENTA_CONCRETE_SLAB = BLOCKS.register("magenta_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.MAGENTA_CONCRETE)));

    public static final RegistryObject<Block> LIGHT_BLUE_CONCRETE_SLAB = BLOCKS.register("light_blue_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.LIGHT_BLUE_CONCRETE)));

    public static final RegistryObject<Block> CYAN_CONCRETE_SLAB = BLOCKS.register("cyan_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.CYAN_CONCRETE)));

    public static final RegistryObject<Block> YELLOW_CONCRETE_SLAB = BLOCKS.register("yellow_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.YELLOW_CONCRETE)));

    public static final RegistryObject<Block> LIME_CONCRETE_SLAB = BLOCKS.register("lime_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.LIME_CONCRETE)));

    public static final RegistryObject<Block> PINK_CONCRETE_SLAB = BLOCKS.register("pink_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.PINK_CONCRETE)));

    public static final RegistryObject<Block> GRAY_CONCRETE_SLAB = BLOCKS.register("gray_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.GRAY_CONCRETE)));

    public static final RegistryObject<Block> LIGHT_GRAY_CONCRETE_SLAB = BLOCKS.register("light_gray_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.LIGHT_GRAY_CONCRETE)));

    public static final RegistryObject<Block> BLUE_CONCRETE_SLAB = BLOCKS.register("blue_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.BLUE_CONCRETE)));

    public static final RegistryObject<Block> PURPLE_CONCRETE_SLAB = BLOCKS.register("purple_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.PURPLE_CONCRETE)));

    public static final RegistryObject<Block> GREEN_CONCRETE_SLAB = BLOCKS.register("green_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.GREEN_CONCRETE)));

    public static final RegistryObject<Block> BROWN_CONCRETE_SLAB = BLOCKS.register("brown_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.BROWN_CONCRETE)));

    public static final RegistryObject<Block> RED_CONCRETE_SLAB = BLOCKS.register("red_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.RED_CONCRETE)));

    public static final RegistryObject<Block> BLACK_CONCRETE_SLAB = BLOCKS.register("black_concrete_slab", () -> new SlabBlock(Block.Properties.from(Blocks.BLACK_CONCRETE)));


}
