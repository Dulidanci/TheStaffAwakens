package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.AirCoreModel;
import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Vector3f;

public class AirCore implements CoreTemplate {
    @Override
    public void activateCore(PlayerEntity player) {

    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.AIR;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new AirCoreModel(AirCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
