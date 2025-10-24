package net.dulidanci.thestaffawakens.util;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.item.cores.CoreTypes;
import net.dulidanci.thestaffawakens.item.cores.LapisLazuliCore;
import net.dulidanci.thestaffawakens.item.cores.PlanksCore;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerItemTracker {
    private static final CoreTypes[] CORES_TO_TRACK = {
            CoreTypes.OAK_PLANKS,          // 0
            CoreTypes.SPRUCE_PLANKS,       // 1
            CoreTypes.BIRCH_PLANKS,        // 2
            CoreTypes.JUNGLE_PLANKS,       // 3
            CoreTypes.ACACIA_PLANKS,       // 4
            CoreTypes.DARK_OAK_PLANKS,     // 5
            CoreTypes.MANGROVE_PLANKS,     // 6
            CoreTypes.CHERRY_PLANKS,       // 7
            CoreTypes.BAMBOO_PLANKS,       // 8
            CoreTypes.CRIMSON_PLANKS,      // 9
            CoreTypes.WARPED_PLANKS,       // 10
            CoreTypes.LAPIS_LAZULI         // 11
    };
    private static final Map<UUID, Pair<Item, Item>> heldItems = new HashMap<>();

    public static void register() {
        TheStaffAwakens.LOGGER.info("Registering PlayerItemTracker for " + TheStaffAwakens.MOD_ID);
        ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
            for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
                heldItems.putIfAbsent(player.getUuid(), new Pair<>(player.getMainHandStack().getItem(), player.getOffHandStack().getItem()));
                Pair<Item, Item> previouslyHeld = heldItems.get(player.getUuid());
                Pair<Item, Item> playerHands = new Pair<>(player.getMainHandStack().getItem(), player.getOffHandStack().getItem());

                if (isCheckedItem(playerHands.getLeft()) >= 0 && previouslyHeld.getLeft() != playerHands.getLeft()) {
                    onItemEquipped(player, playerHands.getLeft());
                }
                if (isCheckedItem(previouslyHeld.getLeft()) >= 0 && previouslyHeld.getLeft() == playerHands.getLeft()) {
                    whileHoldingItem(player, playerHands.getLeft());
                }
                if (isCheckedItem(previouslyHeld.getLeft()) >= 0 && previouslyHeld.getLeft() != playerHands.getLeft()) {
                    onItemRemoved(player, previouslyHeld.getLeft());
                }

                if (isCheckedItem(playerHands.getRight()) >= 0 && previouslyHeld.getRight() != playerHands.getRight()) {
                    onItemEquipped(player, playerHands.getRight());
                }
                if (isCheckedItem(previouslyHeld.getRight()) >= 0 && previouslyHeld.getRight() == playerHands.getRight()) {
                    whileHoldingItem(player, playerHands.getRight());
                }
                if (isCheckedItem(previouslyHeld.getRight()) >= 0 && previouslyHeld.getRight() != playerHands.getRight()) {
                    onItemRemoved(player, previouslyHeld.getRight());
                }

                heldItems.put(player.getUuid(), playerHands);
            }
        });
    }

    private static int isCheckedItem(Item item) {
        if (item instanceof StaffItem staffItem) {
            for (int i = 0; i < CORES_TO_TRACK.length; i++) {
                CoreTypes core = CORES_TO_TRACK[i];
                if (staffItem.getCore().getType().equals(core)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static void onItemEquipped(ServerPlayerEntity player, Item item) {
        if (item instanceof StaffItem staffItem) {
            if (staffItem.getCore() instanceof PlanksCore planksCore) {
                planksCore.generatePreview(player);
            } else if (staffItem.getCore() instanceof LapisLazuliCore) {
                LapisLazuliCore.levitating(player);
            }
        }
    }

    private static void whileHoldingItem(ServerPlayerEntity player, Item item) {
        if (item instanceof StaffItem staffItem) {
            if (staffItem.getCore() instanceof PlanksCore planksCore) {
                planksCore.generatePreview(player);
            } else if (staffItem.getCore() instanceof LapisLazuliCore) {
                LapisLazuliCore.levitating(player);
            }
        }
    }

    private static void onItemRemoved(ServerPlayerEntity player, Item item) {
        if (item instanceof StaffItem staffItem) {
            if (staffItem.getCore() instanceof PlanksCore planksCore) {
                planksCore.removePreview(player);
            }
        }
    }
}
