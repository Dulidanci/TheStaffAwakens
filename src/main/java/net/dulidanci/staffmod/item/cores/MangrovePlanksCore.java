package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class MangrovePlanksCore extends PlanksCore {
    protected MangrovePlanksCore() {
        super(Blocks.MANGROVE_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.MANGROVE_PLANKS;
    }
}
