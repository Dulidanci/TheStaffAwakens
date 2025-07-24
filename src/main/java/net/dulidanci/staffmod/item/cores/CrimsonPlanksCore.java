package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class CrimsonPlanksCore extends PlanksCore {
    protected CrimsonPlanksCore() {
        super(Blocks.CRIMSON_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.CRIMSON_PLANKS;
    }
}
