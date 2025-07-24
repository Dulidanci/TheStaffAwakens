package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class WarpedPlanksCore extends PlanksCore {
    protected WarpedPlanksCore() {
        super(Blocks.WARPED_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.WARPED_PLANKS;
    }
}
