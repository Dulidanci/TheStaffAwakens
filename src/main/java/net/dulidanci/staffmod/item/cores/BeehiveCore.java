package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.entity.ModEntities;
import net.dulidanci.staffmod.entity.custom.LoyalBeeEntity;
import net.dulidanci.staffmod.render.model.core.BeehiveCoreModel;
import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class BeehiveCore implements CoreTemplate{
    public static final double mana = 4;

    @Override
    public void activateCore(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient()) {
            return;
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            LoyalBeeEntity bee = new LoyalBeeEntity(ModEntities.LOYAL_BEE, world);
            bee.setOwner(player);
            bee.refreshPositionAndAngles(player.getBlockPos(), player.getYaw(), 0);
            world.spawnEntity(bee);
            ManaSupplier.decreaseMana(player, mana);
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.BEEHIVE;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new BeehiveCoreModel(BeehiveCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}