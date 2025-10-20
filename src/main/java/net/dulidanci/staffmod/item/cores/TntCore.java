package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.render.model.core.TntCoreModel;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class TntCore implements CoreTemplate {
    public static final double mana = 1;

    @Override
    public void activateCore(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient()) {
            return;
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            spawnTnt(player);
            ManaSupplier.decreaseMana(player, mana);
        }
    }

    private void spawnTnt(PlayerEntity player) {
        World world = player.getWorld();
        TntEntity spawnedTnt = EntityType.TNT.create(world);

        BlockPos position = player.getBlockPos().add(0, 1, 0);
        Vec3d facing = player.getRotationVector().normalize();

        if (spawnedTnt != null) {
            spawnedTnt.refreshPositionAndAngles(position, 0, 0);
            spawnedTnt.setVelocity(facing);
            world.spawnEntity(spawnedTnt);
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.TNT;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new TntCoreModel(TntCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}