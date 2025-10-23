package net.dulidanci.thestaffawakens.item.cores;

import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.dulidanci.thestaffawakens.render.model.core.DarkOakPlanksCoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class DarkOakPlanksCore extends PlanksCore {
    protected DarkOakPlanksCore() {
        super(Blocks.DARK_OAK_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.DARK_OAK_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new DarkOakPlanksCoreModel(DarkOakPlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
