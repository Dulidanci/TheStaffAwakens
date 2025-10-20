package net.dulidanci.staffmod.item.cores;

import net.dulidanci.staffmod.render.model.core.BoneBlockCoreModel;
import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class BoneBlockCore implements CoreTemplate{
    public static final double mana = 1;

    @Override
    public void activateCore(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient()) {
            return;
        }
        if (ManaSupplier.manaCheck(player, mana)) {
            MinecraftClient client = MinecraftClient.getInstance();
            HitResult hit = client.crosshairTarget;

            if (hit != null) {
                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    BlockPos targetBlockPos = blockHit.getBlockPos();
                    BlockState targetBlockState = world.getBlockState(targetBlockPos);
                    if (targetBlockState.getBlock() instanceof Fertilizable fertilizable) {
                        if (fertilizable.isFertilizable(world, targetBlockPos, targetBlockState)) {
                            fertilizable.grow((ServerWorld) world, Random.create(), targetBlockPos, targetBlockState);
                            ((ServerWorld) world).spawnParticles(ParticleTypes.HAPPY_VILLAGER,
                                    targetBlockPos.getX() + 0.5,targetBlockPos.getY() + 0.5,targetBlockPos.getZ() + 0.5,
                                    4, 0.5, 0.5, 0.5, 0.1);
                            ManaSupplier.decreaseMana(player, mana);
                        }
                    }
                }
            }
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.BONE_BLOCK;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new BoneBlockCoreModel(BoneBlockCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}