package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.render.model.core.OakPlanksCoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class OakPlanksCore extends PlanksCore {
    protected OakPlanksCore() {
        super(Blocks.OAK_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.OAK_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new OakPlanksCoreModel(OakPlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
