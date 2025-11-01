package net.dulidanci.thestaffawakens;

import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.dulidanci.thestaffawakens.block.ModBlockEntities;
import net.dulidanci.thestaffawakens.enchantments.ModEnchantments;
import net.dulidanci.thestaffawakens.entity.ModEntities;
import net.dulidanci.thestaffawakens.entity.custom.TrackedAnvilEntity;
import net.dulidanci.thestaffawakens.item.ModItemGroups;
import net.dulidanci.thestaffawakens.item.ModItems;
import net.dulidanci.thestaffawakens.item.cores.BellCore;
import net.dulidanci.thestaffawakens.item.cores.CoreTypes;
import net.dulidanci.thestaffawakens.item.cores.LapisLazuliCore;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.dulidanci.thestaffawakens.render.screen.ModScreenHandlers;
import net.dulidanci.thestaffawakens.util.EntityTimerManager;
import net.dulidanci.thestaffawakens.util.ManaSupplier;
import net.dulidanci.thestaffawakens.util.PlayerItemTracker;
import net.dulidanci.thestaffawakens.util.StaffPairingTable;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheStaffAwakens implements ModInitializer {
	public static final String MOD_ID = "thestaffawakens";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		TheStaffAwakens.LOGGER.info("Initializing " + TheStaffAwakens.MOD_ID + " 'main' entrypoint!");

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
		StaffPairingTable.register();

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			// Your custom logic here
			if (!world.isClient) {
				TrackedAnvilEntity.setTargetForAnvils(entity);

				if (player.getMainHandStack().getItem() instanceof StaffItem staffItem) {
					if (staffItem.getCore().getType().equals(CoreTypes.MAGMA_BLOCK)) {
						entity.setOnFireFor(8);
					}
					if (staffItem.getCore().getType().equals(CoreTypes.LAPIS_LAZULI)) {
						if (entity instanceof LivingEntity livingEntity) {
							if (ManaSupplier.manaCheck(player, LapisLazuliCore.manaOnHit)) {
								livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 600, 0));
								ManaSupplier.decreaseMana(player, LapisLazuliCore.manaOnHit);
							}
						}
					}
					if (staffItem.getCore().getType().equals(CoreTypes.BELL)) {
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
