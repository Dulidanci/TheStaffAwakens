package net.dulidanci.thestaffawakens.item.cores;

import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.dulidanci.thestaffawakens.render.model.core.WarpedPlanksCoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class WarpedPlanksCore extends PlanksCore {
    protected WarpedPlanksCore() {
        super(Blocks.WARPED_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.WARPED_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new WarpedPlanksCoreModel(WarpedPlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
