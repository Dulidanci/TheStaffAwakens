package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class DarkOakPlanksCore extends PlanksCore {
    protected DarkOakPlanksCore() {
        super(Blocks.DARK_OAK_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.DARK_OAK_PLANKS;
    }
}
