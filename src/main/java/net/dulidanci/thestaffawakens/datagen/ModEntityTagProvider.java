package net.dulidanci.thestaffawakens.datagen;

import net.dulidanci.thestaffawakens.entity.ModEntities;
import net.dulidanci.thestaffawakens.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public ModEntityTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.GREEN_GLOW)
                .add(EntityType.BAT)
                .add(EntityType.ALLAY)
                .add(EntityType.SNOW_GOLEM)
                .add(EntityType.IRON_GOLEM)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.TADPOLE)
                .add(EntityType.COD)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.SALMON)
                .add(EntityType.DOLPHIN)
                .add(EntityType.SQUID)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.VILLAGER)
                .add(EntityType.WANDERING_TRADER)
                .add(EntityType.BEE)
                .add(ModEntities.LOYAL_BEE)
                .add(EntityType.PIG)
                .add(EntityType.FOX)
                .add(EntityType.SHEEP)
                .add(EntityType.RABBIT)
                .add(EntityType.COW)
                .add(EntityType.MOOSHROOM)
                .add(EntityType.MULE)
                .add(EntityType.DONKEY)
                .add(EntityType.LLAMA)
                .add(EntityType.TRADER_LLAMA)
                .add(EntityType.HORSE)
                .add(EntityType.CAMEL)
                .add(EntityType.GOAT)
                .add(EntityType.PANDA)
                .add(EntityType.FROG)
                .add(EntityType.AXOLOTL)
                .add(EntityType.CHICKEN)
                .add(EntityType.POLAR_BEAR)
                .add(EntityType.TURTLE)
                .add(EntityType.STRIDER)
                .add(EntityType.OCELOT)
                .add(EntityType.SNIFFER)
                .add(EntityType.WOLF)
                .add(EntityType.CAT)
                .add(EntityType.PARROT);

        getOrCreateTagBuilder(ModTags.RED_GLOW)
                .add(EntityType.PHANTOM)
                .add(EntityType.GHAST)
                .add(EntityType.SHULKER)
                .add(EntityType.SKELETON_HORSE)
                .add(EntityType.ZOMBIE_HORSE)
                .add(EntityType.HOGLIN)
                .add(EntityType.SILVERFISH)
                .add(EntityType.GIANT)
                .add(EntityType.WITHER)
                .add(EntityType.WARDEN)
                .add(EntityType.ZOMBIE)
                .add(EntityType.ZOMBIFIED_PIGLIN)
                .add(EntityType.DROWNED)
                .add(EntityType.HUSK)
                .add(EntityType.ZOMBIE_VILLAGER)
                .add(EntityType.ENDERMITE)
                .add(EntityType.BLAZE)
                .add(EntityType.ZOGLIN)
                .add(EntityType.BREEZE)
                .add(EntityType.VEX)
                .add(EntityType.SKELETON)
                .add(EntityType.WITHER_SKELETON)
                .add(EntityType.STRAY)
                .add(EntityType.GUARDIAN)
                .add(EntityType.ELDER_GUARDIAN)
                .add(EntityType.RAVAGER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.PILLAGER)
                .add(EntityType.ILLUSIONER)
                .add(EntityType.EVOKER)
                .add(EntityType.WITCH)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.PIGLIN)
                .add(EntityType.ENDERMAN)
                .add(EntityType.CREEPER)
                .add(EntityType.SPIDER)
                .add(EntityType.CAVE_SPIDER)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.SLIME)
                .add(EntityType.MAGMA_CUBE);
    }
}
