package net.dulidanci.staffmod.item.cores;

import net.minecraft.block.Blocks;

public class AcaciaPlanksCore extends PlanksCore {
    protected AcaciaPlanksCore() {
        super(Blocks.ACACIA_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.ACACIA_PLANKS;
    }
}
