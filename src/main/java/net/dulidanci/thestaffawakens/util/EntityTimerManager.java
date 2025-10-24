package net.dulidanci.thestaffawakens.util;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.item.cores.MagmaBlockCore;
import net.dulidanci.thestaffawakens.item.cores.TargetCore;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.server.MinecraftServer;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class EntityTimerManager {
    private static final Map<Pair<UUID, Integer>, Integer> entityTimers = new HashMap<>();
    /**
     * KEY:
     * <p>
     * UUID - Ticking entity's ID
     * <p>
     * Integer - Type of action to be executed
     * <p>
     * VALUE:
     * <p>
     * Integer - Ticks remaining
     */

    public static void register() {
        TheStaffAwakens.LOGGER.info("Registering EntityTimerManager for " + TheStaffAwakens.MOD_ID);
        ServerTickEvents.END_SERVER_TICK.register(EntityTimerManager::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        Iterator<Map.Entry<Pair<UUID, Integer>, Integer>> iterator = entityTimers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Pair<UUID, Integer>, Integer> entry = iterator.next();
            int ticksLeft = entry.getValue() - 1;

            if (ticksLeft <= 0) {
                UUID entityId = entry.getKey().getA();
                Entity entity = findEntityByUUID(server, entityId);
                if (entity != null) {
                    executeTimedAction(entity, entry.getKey().getB());
                }
                iterator.remove();
            } else {
                entry.setValue(ticksLeft);
            }
        }
    }

    public static void startEntityTimer(Entity entity, int ticks, int type) {
        entityTimers.put(new Pair<>(entity.getUuid(), type), ticks);
    }

    /**
     * @param type Stores which action has to be executed when the timer hits zero
     * @value 0 - NoAI turning off
     * <p>
     *     Called from BellCore
     * </p>
     * @value 1 - Removing fireballs
     * <p>
     *     Called from MagmaBlockCore
     * </p>
     * @value 2 - Remove glowing mob from team
     * <p>
     *     Called from TargetCore
     * </p>
     */
    private static void executeTimedAction(Entity entity, int type) {
        if (type == 0) {
            if (entity instanceof LivingEntity livingEntity) {
                MobUtilities.setNoAI(livingEntity, false);
            }
        } else if (type == 1) {
            if (entity instanceof FireballEntity fireball) {
                MagmaBlockCore.removeFireball(fireball);
            }
        } else if (type == 2) {
            if (entity instanceof LivingEntity living) {
                TargetCore.resetTeam(living, living.getWorld());
            }
        }
    }

    private static Entity findEntityByUUID(MinecraftServer server, UUID entityId) {
        for (var world : server.getWorlds()) {
            Entity entity = world.getEntity(entityId);
            if (entity != null) {
                return entity;
            }
        }
        return null;
    }


}
