package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Vector3f;

public interface CoreTemplate {
    void activateCore(PlayerEntity player);

    CoreTypes getType();

    CoreModel getModel(Vector3f cubeOriginPoint);
}
