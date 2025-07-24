package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class BirchPlanksCore extends PlanksCore {
    protected BirchPlanksCore() {
        super(Blocks.BIRCH_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.BIRCH_PLANKS;
    }
}
