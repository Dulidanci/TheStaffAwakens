package net.dulidanci.thestaffawakens.entity.ai.goal;

import net.dulidanci.thestaffawakens.entity.custom.LoyalBeeEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;

public class FollowPlayerGoal extends Goal {
    private final PathAwareEntity mob;
    private final double speed;
    private final float minDistance;
    private final float maxDistance;
    private PlayerEntity target;

    public FollowPlayerGoal(PathAwareEntity mob, double speed, float minDistance, float maxDistance) {
        this.mob = mob;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
    }

    @Override
    public boolean canStart() {
        if (mob instanceof LoyalBeeEntity bee) {
            return bee.getOwner() != null;
        }
        return false;
    }

    @Override
    public void start() {
        if (mob instanceof LoyalBeeEntity bee) {
            target = bee.getOwner();
        }
    }

    @Override
    public boolean canStop() {
        if (mob instanceof LoyalBeeEntity) {
            return target != null && mob.squaredDistanceTo(target) < minDistance;
        }
        return false;
    }

    @Override
    public void tick() {
        if (target != null && mob.squaredDistanceTo(target) > maxDistance) {
            mob.getNavigation().startMovingTo(target, speed);
        }
    }
}
