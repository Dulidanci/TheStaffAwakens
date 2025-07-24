package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.util.EntityTimerManager;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
}