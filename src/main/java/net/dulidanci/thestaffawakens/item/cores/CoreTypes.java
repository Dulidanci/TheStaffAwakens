package net.dulidanci.thestaffawakens.item.cores;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.function.Supplier;

public enum CoreTypes {
    AIR(AirCore::new, Blocks.AIR, 0),
    ANVIL(AnvilCore::new, Blocks.ANVIL, 8),
    BEEHIVE(BeehiveCore::new, Blocks.BEEHIVE, 4),
    BELL(BellCore::new, Blocks.BELL, 5),
    BONE_BLOCK(BoneBlockCore::new, Blocks.BONE_BLOCK, 1),
    END_STONE(EndStoneCore::new, Blocks.END_STONE, 4),
    GLOWSTONE(GlowstoneCore::new, Blocks.GLOWSTONE, 1),
    LAPIS_LAZULI(LapisLazuliCore::new, Blocks.LAPIS_BLOCK, 3),
    MAGMA_BLOCK(MagmaBlockCore::new, Blocks.MAGMA_BLOCK, 3),
    NETHERRACK(NetherrackCore::new, Blocks.NETHERRACK, 0),
    ACACIA_PLANKS(AcaciaPlanksCore::new, Blocks.ACACIA_PLANKS, 2),
    BAMBOO_PLANKS(BambooPlanksCore::new, Blocks.BAMBOO_PLANKS, 2),
    BIRCH_PLANKS(BirchPlanksCore::new, Blocks.BIRCH_PLANKS, 2),
    CHERRY_PLANKS(CherryPlanksCore::new, Blocks.CHERRY_PLANKS, 2),
    CRIMSON_PLANKS(CrimsonPlanksCore::new, Blocks.CRIMSON_PLANKS, 2),
    DARK_OAK_PLANKS(DarkOakPlanksCore::new, Blocks.DARK_OAK_PLANKS, 2),
    JUNGLE_PLANKS(JunglePlanksCore::new, Blocks.JUNGLE_PLANKS, 2),
    MANGROVE_PLANKS(MangrovePlanksCore::new, Blocks.MANGROVE_PLANKS, 2),
    OAK_PLANKS(OakPlanksCore::new, Blocks.OAK_PLANKS, 2),
    SPRUCE_PLANKS(SprucePlanksCore::new, Blocks.SPRUCE_PLANKS, 2),
    WARPED_PLANKS(WarpedPlanksCore::new, Blocks.WARPED_PLANKS, 2),
    TARGET(TargetCore::new, Blocks.TARGET, 2),
    TNT(TntCore::new, Blocks.TNT, 1);

    private final Supplier<CoreTemplate> coreSupplier;
    private final Block block;
    private final int mana;

    CoreTypes(Supplier<CoreTemplate> supplier, Block block, int mana) {
        this.coreSupplier = supplier;
        this.block = block;
        this.mana = mana;
    }

    public CoreTemplate createCore() {
        return coreSupplier.get();
    }

    public Block getBlock() {
        return block;
    }

    public int getManaCost() {
        return mana;
    }

    public static CoreTypes getCoreFromBlock(Block block) {
        for (CoreTypes core : CoreTypes.values()) {
            if (core.getBlock().equals(block)) return core;
        }
        return AIR;
    }
}
