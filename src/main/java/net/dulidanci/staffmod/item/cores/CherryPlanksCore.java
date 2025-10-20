package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.CherryPlanksCoreModel;
import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class CherryPlanksCore extends PlanksCore {
    protected CherryPlanksCore() {
        super(Blocks.CHERRY_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.CHERRY_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new CherryPlanksCoreModel(CherryPlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
