package net.dulidanci.staffmod.item.cores;

import net.minecraft.entity.player.PlayerEntity;

public interface CoreTemplate {
    void activateCore(PlayerEntity player);

    CoreTypes getType();
}
