package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class OakPlanksCore extends PlanksCore {
    protected OakPlanksCore() {
        super(Blocks.OAK_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.OAK_PLANKS;
    }
}
