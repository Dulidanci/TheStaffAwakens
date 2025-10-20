package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.render.model.core.CrimsonPlanksCoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class CrimsonPlanksCore extends PlanksCore {
    protected CrimsonPlanksCore() {
        super(Blocks.CRIMSON_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.CRIMSON_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new CrimsonPlanksCoreModel(CrimsonPlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
