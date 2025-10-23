package net.dulidanci.thestaffawakens.item.cores;

import net.dulidanci.thestaffawakens.render.model.core.BirchPlanksCoreModel;
import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class BirchPlanksCore extends PlanksCore {
    protected BirchPlanksCore() {
        super(Blocks.BIRCH_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.BIRCH_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new BirchPlanksCoreModel(BirchPlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
