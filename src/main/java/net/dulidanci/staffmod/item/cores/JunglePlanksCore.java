package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class JunglePlanksCore extends PlanksCore {
    protected JunglePlanksCore() {
        super(Blocks.JUNGLE_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.JUNGLE_PLANKS;
    }
}
