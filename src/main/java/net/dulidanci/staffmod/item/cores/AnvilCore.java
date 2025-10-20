package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.entity.custom.TrackedAnvilEntity;
import net.dulidanci.staffmod.render.model.core.AnvilCoreModel;
import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class AnvilCore implements CoreTemplate {
    public static final double mana = 8;

    @Override
    public void activateCore(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient) {
            return;
        }
        if (ManaSupplier.manaCheck(player, mana)) {
            ServerWorld serverWorld = (ServerWorld) world;
            for (int i = -1; i < 2; i += 2) {
                for (int j = -1; j < 2; j += 2) {
                    Vec3d velocity = new Vec3d(i * 0.2, 1.5, j * 0.2);
                    TrackedAnvilEntity.spawnAnvil(serverWorld, player, velocity);
                }
            }
            ManaSupplier.decreaseMana(player, mana);
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.ANVIL;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new AnvilCoreModel(AnvilCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}
