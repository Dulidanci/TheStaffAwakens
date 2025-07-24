package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class SprucePlanksCore extends PlanksCore {
    protected SprucePlanksCore() {
        super(Blocks.SPRUCE_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.SPRUCE_PLANKS;
    }
}
