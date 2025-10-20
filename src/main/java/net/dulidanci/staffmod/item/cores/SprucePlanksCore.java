package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.render.model.core.SprucePlanksCoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class SprucePlanksCore extends PlanksCore {
    protected SprucePlanksCore() {
        super(Blocks.SPRUCE_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.SPRUCE_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new SprucePlanksCoreModel(SprucePlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
