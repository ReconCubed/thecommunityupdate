package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.CommunityUpdate;
import me.reconcubed.communityupdate.block.BookshelfLeverBlock;
import me.reconcubed.communityupdate.block.TorchLeverBlock;
import me.reconcubed.communityupdate.block.VexedEarthBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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

}
