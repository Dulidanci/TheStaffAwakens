package net.dulidanci.thestaffawakens.util;

import net.minecraft.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class AnvilTracker{
    private static final Set<Entity> ANVILS_SPAWNED_FROM_STAFF = new HashSet<>();

    public static Set<Entity> getAnvilsSpawnedFromStaff() {
        return ANVILS_SPAWNED_FROM_STAFF;
    }

    public static void add(Entity entity) {
        ANVILS_SPAWNED_FROM_STAFF.add(entity);
    }

    public static void remove(Entity entity) {
        ANVILS_SPAWNED_FROM_STAFF.remove(entity);
    }
}
