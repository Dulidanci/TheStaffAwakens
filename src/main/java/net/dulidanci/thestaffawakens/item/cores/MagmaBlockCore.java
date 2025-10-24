package net.dulidanci.thestaffawakens.item.cores;

import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.dulidanci.thestaffawakens.render.model.core.MagmaBlockCoreModel;
import net.dulidanci.thestaffawakens.util.EntityTimerManager;
import net.dulidanci.thestaffawakens.util.ManaSupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class MagmaBlockCore implements CoreTemplate{
    public static final double mana = 3;

    @Override
    public void activateCore(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient) {
            return;
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            Vec3d facing = player.getRotationVector();

            FireballEntity fireball = new FireballEntity(world, player, facing.x, facing.y, facing.z, 1);
            fireball.setPos(fireball.getX(), fireball.getY() + 1, fireball.getZ());
            world.spawnEntity(fireball);
            EntityTimerManager.startEntityTimer(fireball, 1200, 1);
            ManaSupplier.decreaseMana(player, mana);
        }
    }

    public static void removeFireball(FireballEntity fireball) {
        if (fireball.isAlive()) {
            fireball.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.MAGMA_BLOCK;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new MagmaBlockCoreModel(MagmaBlockCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}