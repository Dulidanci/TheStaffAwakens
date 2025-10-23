package net.dulidanci.thestaffawakens.entity.ai.goal;

import net.dulidanci.thestaffawakens.entity.custom.LoyalBeeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class AttackOnPlayerHitGoal extends Goal {
    private final LoyalBeeEntity bee;
    private final double speed;

    public AttackOnPlayerHitGoal(LoyalBeeEntity bee, double speed) {
        this.bee = bee;
        this.speed = speed;
    }

    @Override
    public boolean canStart() {
        return bee.getOwner() != null;
    }

    @Override
    public void tick() {
        if (bee.getOwner() != null) {
            LivingEntity lastAttacked = bee.getOwner().getAttacking();
            if (lastAttacked != null && !(lastAttacked instanceof LoyalBeeEntity) && lastAttacked.isAlive()) {
                bee.setTarget(lastAttacked);
                if (bee.squaredDistanceTo(lastAttacked) <= 1) {
                    bee.tryAttack(lastAttacked);
                } else {
                    bee.getNavigation().startMovingTo(lastAttacked, speed);
                }
            }
        }
    }
}
