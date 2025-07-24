package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class BambooPlanksCore extends PlanksCore {
    protected BambooPlanksCore() {
        super(Blocks.BAMBOO_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.BAMBOO_PLANKS;
    }
}
