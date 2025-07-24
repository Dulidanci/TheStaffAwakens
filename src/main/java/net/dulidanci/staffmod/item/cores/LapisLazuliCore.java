package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class LapisLazuliCore implements CoreTemplate{
    public static final int mana = 3;
    public static final int manaOnHit = 2;

    @Override
    public void activateCore(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient) {
            return;
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 60, 7, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0, false, true));
            ManaSupplier.decreaseMana(player, mana);
        }
    }

    public static void levitating(PlayerEntity player) {
        BlockPos position = player.getBlockPos();
        World world = player.getWorld();
        if (world.isClient()) {
            return;
        }
        ArrayList<BlockPos> posList = new ArrayList<>();
        for (int i = -2; i < 0; i++) {
            posList.add(position.add(0, i, 0));
        }
        for (BlockPos current : posList) {
            if (!player.hasStatusEffect(StatusEffects.LEVITATION)) {
                if (world.getBlockState(current) != Blocks.AIR.getDefaultState()) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 5, 2, true, false));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40, 0, true, false));
                }
            }
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.LAPIS_LAZULI;
    }
}