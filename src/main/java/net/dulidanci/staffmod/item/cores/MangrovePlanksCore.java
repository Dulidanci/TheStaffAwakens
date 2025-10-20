package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.render.model.core.MangrovePlanksCoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class MangrovePlanksCore extends PlanksCore {
    protected MangrovePlanksCore() {
        super(Blocks.MANGROVE_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.MANGROVE_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new MangrovePlanksCoreModel(MangrovePlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
