package net.dulidanci.staffmod;

import net.dulidanci.staffmod.block.ModBlocks;
import net.dulidanci.staffmod.block.ModBlockEntities;
import net.dulidanci.staffmod.enchantments.ModEnchantments;
import net.dulidanci.staffmod.entity.ModEntities;
import net.dulidanci.staffmod.entity.custom.TrackedAnvilEntity;
import net.dulidanci.staffmod.item.ModItemGroups;
import net.dulidanci.staffmod.item.ModItems;
import net.dulidanci.staffmod.item.cores.BellCore;
import net.dulidanci.staffmod.item.cores.CoreTypes;
import net.dulidanci.staffmod.item.cores.LapisLazuliCore;
import net.dulidanci.staffmod.item.custom.DynamicStaffItem;
import net.dulidanci.staffmod.screen.ModScreenHandlers;
import net.dulidanci.staffmod.util.EntityTimerManager;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.dulidanci.staffmod.util.PlayerItemTracker;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffMod implements ModInitializer {
	public static final String MOD_ID = "staffmod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		StaffMod.LOGGER.info("Initializing " + StaffMod.MOD_ID + " 'main' entrypoint!");

		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registeringBlockEntities();

		ModEntities.registerModEntities();

		ModEnchantments.registerModEnchantments();

		ModScreenHandlers.register();

		PlayerItemTracker.register();
		EntityTimerManager.register();
		ManaSupplier.register();

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			// Your custom logic here
			if (!world.isClient) {
				TrackedAnvilEntity.setTargetForAnvils(entity);

				if (player.getMainHandStack().getItem() instanceof DynamicStaffItem dynamicStaffItem) {
					if (dynamicStaffItem.getCore().getType().equals(CoreTypes.MAGMA_BLOCK)) {
						entity.setOnFireFor(8);
					}
					if (dynamicStaffItem.getCore().getType().equals(CoreTypes.LAPIS_LAZULI)) {
						if (entity instanceof LivingEntity livingEntity) {
							if (ManaSupplier.manaCheck(player, LapisLazuliCore.manaOnHit)) {
								livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 600, 0));
								ManaSupplier.decreaseMana(player, LapisLazuliCore.manaOnHit);
							}
						}
					}
					if (dynamicStaffItem.getCore().getType().equals(CoreTypes.BELL)) {
						if (entity instanceof MobEntity mob) {
							BellCore.onHit(mob, world, player);
						}
					}
				}
			}
			return ActionResult.PASS; // Or CONSUME to cancel further processing
		});


	}
}
