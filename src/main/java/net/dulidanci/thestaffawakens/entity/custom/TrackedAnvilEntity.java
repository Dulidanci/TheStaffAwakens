package net.dulidanci.thestaffawakens.entity.custom;

import net.dulidanci.thestaffawakens.util.AnvilTracker;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TrackedAnvilEntity extends FallingBlockEntity {
    private final Runnable onLanding = () -> {
            AnvilTracker.remove(this);

            if (!this.getWorld().isClient) {
                World world = this.getWorld();
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();

                world.createExplosion(this, x, y, z, 2.0F, false, World.ExplosionSourceType.BLOW);
            }
    };

    public TrackedAnvilEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public static void spawnAnvil(ServerWorld serverWorld, PlayerEntity player, Vec3d velocity) {
        Vec3d spawnPos = player.getPos().add(0, 1, 0);

        NbtCompound nbt = new NbtCompound();
        NbtCompound blockStateNbt = new NbtCompound();
        blockStateNbt.putString("Name", "minecraft:anvil");
        nbt.put("BlockState", blockStateNbt);

        TrackedAnvilEntity trackedAnvil = new TrackedAnvilEntity(EntityType.FALLING_BLOCK, serverWorld);

        trackedAnvil.readNbt(nbt);
        trackedAnvil.setPosition(spawnPos);
        trackedAnvil.setVelocity(velocity);
        trackedAnvil.setDestroyedOnLanding();
        trackedAnvil.dropItem = false;
        AnvilTracker.add(trackedAnvil);
        serverWorld.spawnEntity(trackedAnvil);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnGround()) {
            this.onLanding.run();
        }
    }

    public static void setTargetForAnvils(Entity entity) {
        for (Entity anvil : AnvilTracker.getAnvilsSpawnedFromStaff()) {
            Vec3d startingPoint = anvil.getPos();
            Vec3d destination = entity.getPos();
            double dx = destination.x - startingPoint.x, dy = destination.y - startingPoint.y, dz = destination.z - startingPoint.z;
            double movement = Math.sqrt(dx * dx + dy * dy + dz * dz);
            anvil.setVelocity(dx / movement * 2, dy / movement * 2, dz / movement * 2);
        }
    }
}
