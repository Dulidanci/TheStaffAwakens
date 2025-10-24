package net.dulidanci.thestaffawakens.util;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.enchantments.ModEnchantments;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManaSupplier {
    public static final Map<UUID, Double> manaPoints = new HashMap<>();
    public static final Map<UUID, Double> manaMax = new HashMap<>();
    private static final double baseMax = 40;
    private static final double extraPerLevel = 8;
    private static final Map<UUID, Integer> timeLimit = new HashMap<>();
    private static final Map<UUID, Integer> timer = new HashMap<>();
    private static final int baseTime = 80;
    private static final int timeDiscountPerLevel = 3;

    public static void register() {
        TheStaffAwakens.LOGGER.info("Registering ManaSupplier for " + TheStaffAwakens.MOD_ID);
        ServerTickEvents.END_SERVER_TICK.register(ManaSupplier::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        for (Map.Entry<UUID, Integer> entry : timer.entrySet()) {
            UUID uuid = entry.getKey();
            if (entry.getValue() >= timeLimit.get(uuid)) {
                if (manaPoints.get(uuid) < manaMax.get(uuid)) {
                    manaPoints.put(uuid, manaPoints.get(uuid) + 1);
                    entry.setValue(0);
                }
            } else {
                entry.setValue(entry.getValue() + 1);
            }
        }
    }

    public static Pair<Double, Double> tickingPlayerStats(PlayerEntity player) {
        manaPoints.putIfAbsent(player.getUuid(), baseMax);
        manaMax.put(player.getUuid(), baseMax + countEnchantmentLevel(player) * extraPerLevel);
        timer.putIfAbsent(player.getUuid(), 0);
        timeLimit.put(player.getUuid(), baseTime - countEnchantmentLevel(player) * timeDiscountPerLevel);
        if (manaPoints.get(player.getUuid()) > manaMax.get(player.getUuid())) {
            manaPoints.put(player.getUuid(), manaMax.get(player.getUuid()));
        }
        return new Pair<>(manaPoints.get(player.getUuid()), manaMax.get(player.getUuid()));
    }

    public static boolean manaCheck(PlayerEntity player, double cost) {
        if (player != null) {
            if (player.isCreative()) {
                return true;
            }
            for (Map.Entry<UUID, Double> entry : manaPoints.entrySet()) {
                if (entry.getKey().equals(player.getUuid())) {
                    return entry.getValue() >= cost;
                }
            }
            manaPoints.put(player.getUuid(), baseMax);
        }
        return false;
    }

    public static void decreaseMana(PlayerEntity player, double cost) {
        if (player != null) {
            if (player.isCreative()) {
                return;
            }
            for (Map.Entry<UUID, Double> entry : manaPoints.entrySet()) {
                if (entry.getKey().equals(player.getUuid())) {
                    double current = entry.getValue();
                    if (current >= cost) {
                        entry.setValue(current - cost);
                    }
                    return;
                }
            }
            manaPoints.put(player.getUuid(), baseMax);
        }
    }

    public static int countEnchantmentLevel(PlayerEntity player) {
        int levelCount = 0;
        for (int i = 2; i < 6; i++) {
            ItemStack itemStack = player.getEquippedStack(EquipmentSlot.values()[i]);
            if (itemStack != null) {
                levelCount += EnchantmentHelper.getLevel(ModEnchantments.MAGICAL, itemStack);
            }
        }
        return levelCount;
    }
}
