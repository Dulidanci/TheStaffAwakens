package net.dulidanci.thestaffawakens.entity.custom;

import net.dulidanci.thestaffawakens.entity.ai.goal.AttackOnPlayerHitGoal;
import net.dulidanci.thestaffawakens.entity.ai.goal.FollowPlayerGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class LoyalBeeEntity extends BeeEntity {
    private PlayerEntity owner;
    public static final double speed = 1.2;

    public LoyalBeeEntity(EntityType<? extends BeeEntity> entityType, World world) {
        super(entityType, world);
        this.owner = null;
    }

    public void setOwner(PlayerEntity player) {
        this.owner = player;
    }

    public PlayerEntity getOwner() {
        return this.owner;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
//        this.goalSelector.clear(goal -> true);
//        this.targetSelector.clear(goal -> true);

        this.goalSelector.add(1, new FollowPlayerGoal(this, speed, 2.0f, 5.0f));
        this.goalSelector.add(0, new AttackOnPlayerHitGoal(this, speed * 1.5));
    }
}
