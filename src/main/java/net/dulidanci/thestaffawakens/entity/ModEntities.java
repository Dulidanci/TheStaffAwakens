package net.dulidanci.thestaffawakens.entity;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.entity.custom.LoyalBeeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<LoyalBeeEntity> LOYAL_BEE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(TheStaffAwakens.MOD_ID, "loyal_bee"),
            EntityType.Builder.create(LoyalBeeEntity::new, SpawnGroup.CREATURE)
                    .setDimensions(0.7f, 0.6f)
                    .build()
    );

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(LOYAL_BEE, BeeEntity.createBeeAttributes()
                .add(EntityAttributes.GENERIC_FLYING_SPEED, LoyalBeeEntity.speed)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0));

        TheStaffAwakens.LOGGER.info("Registering ModEntities for " + TheStaffAwakens.MOD_ID);
    }
}
