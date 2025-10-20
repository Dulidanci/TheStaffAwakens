package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.render.model.core.JunglePlanksCoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class JunglePlanksCore extends PlanksCore {
    protected JunglePlanksCore() {
        super(Blocks.JUNGLE_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.JUNGLE_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new JunglePlanksCoreModel(JunglePlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
