package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.AcaciaPlanksCoreModel;
import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class AcaciaPlanksCore extends PlanksCore {
    protected AcaciaPlanksCore() {
        super(Blocks.ACACIA_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.ACACIA_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new AcaciaPlanksCoreModel(AcaciaPlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
