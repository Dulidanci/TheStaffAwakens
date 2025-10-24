package net.dulidanci.thestaffawakens.item.cores;

import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.dulidanci.thestaffawakens.render.model.core.NetherrackCoreModel;
import net.dulidanci.thestaffawakens.util.ManaSupplier;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class NetherrackCore implements CoreTemplate{
    public static final double mana = 0;

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
                    if (world.getBlockState(targetBlockPos) != Blocks.NETHERRACK.getDefaultState()) {
                        world.setBlockState(targetBlockPos, Blocks.NETHERRACK.getDefaultState());
                        ManaSupplier.decreaseMana(player, mana);
                    }
                }
            }
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.NETHERRACK;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new NetherrackCoreModel(NetherrackCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}