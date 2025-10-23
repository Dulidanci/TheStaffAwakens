package net.dulidanci.thestaffawakens.item.cores;

import net.dulidanci.thestaffawakens.render.model.core.BambooPlanksCoreModel;
import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.minecraft.block.Blocks;
import org.joml.Vector3f;

public class BambooPlanksCore extends PlanksCore {
    protected BambooPlanksCore() {
        super(Blocks.BAMBOO_PLANKS);
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.BAMBOO_PLANKS;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new BambooPlanksCoreModel(BambooPlanksCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
