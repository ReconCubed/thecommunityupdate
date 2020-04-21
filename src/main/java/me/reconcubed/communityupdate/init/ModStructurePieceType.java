package me.reconcubed.communityupdate.init;

import me.reconcubed.communityupdate.world.gen.feature.structure.MinersCamp;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ModStructurePieceType {
    public static final IStructurePieceType MINERS_CAMP = register(MinersCamp.Piece::new, "communityupdate:miners_camp");
//    public static final IStructurePieceType HUGE_ORE_PIECE = register(HugeOre.Piece::new, "venture:huge_ore_piece");

    public static void init() {} //Force static fields to initialize

    private static IStructurePieceType register(IStructurePieceType type, String key)
    {
        return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(key), type);
    }

}
