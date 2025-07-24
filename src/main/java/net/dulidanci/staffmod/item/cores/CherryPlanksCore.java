package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class CherryPlanksCore extends PlanksCore {
    protected CherryPlanksCore() {
        super(Blocks.CHERRY_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.CHERRY_PLANKS;
    }
}
