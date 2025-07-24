package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.function.Supplier;

public enum CoreTypes {
    ANVIL(AnvilCore::new, Blocks.ANVIL),
    BEEHIVE(BeehiveCore::new, Blocks.BEEHIVE),
    BELL(BellCore::new, Blocks.BELL),
    BONE_BLOCK(BoneBlockCore::new, Blocks.BONE_BLOCK),
    END_STONE(EndStoneCore::new, Blocks.END_STONE),
    GLOWSTONE(GlowstoneCore::new, Blocks.GLOWSTONE),
    LAPIS_LAZULI(LapisLazuliCore::new, Blocks.LAPIS_BLOCK),
    MAGMA_BLOCK(MagmaBlockCore::new, Blocks.MAGMA_BLOCK),
    NETHERRACK(NetherrackCore::new, Blocks.NETHERRACK),
    ACACIA_PLANKS(AcaciaPlanksCore::new, Blocks.ACACIA_PLANKS),
    BAMBOO_PLANKS(BambooPlanksCore::new, Blocks.BAMBOO_PLANKS),
    BIRCH_PLANKS(BirchPlanksCore::new, Blocks.BIRCH_PLANKS),
    CHERRY_PLANKS(CherryPlanksCore::new, Blocks.CHERRY_PLANKS),
    CRIMSON_PLANKS(CrimsonPlanksCore::new, Blocks.CRIMSON_PLANKS),
    DARK_OAK_PLANKS(DarkOakPlanksCore::new, Blocks.DARK_OAK_PLANKS),
    JUNGLE_PLANKS(JunglePlanksCore::new, Blocks.JUNGLE_PLANKS),
    MANGROVE_PLANKS(MangrovePlanksCore::new, Blocks.MANGROVE_PLANKS),
    OAK_PLANKS(OakPlanksCore::new, Blocks.OAK_PLANKS),
    SPRUCE_PLANKS(SprucePlanksCore::new, Blocks.SPRUCE_PLANKS),
    WARPED_PLANKS(WarpedPlanksCore::new, Blocks.WARPED_PLANKS),
    TARGET(TargetCore::new, Blocks.TARGET),
    TNT(TntCore::new, Blocks.TNT);

    private final Supplier<CoreTemplate> coreSupplier;
    private final Block block;

    CoreTypes(Supplier<CoreTemplate> supplier, Block block) {
        this.coreSupplier = supplier;
        this.block = block;
    }

    public CoreTemplate createCore() {
        return coreSupplier.get();
    }

    public Block getBlock() {
//        StaffMod.LOGGER.info("Loaded model with id: {}", modelId);
        return block;
    }
}
