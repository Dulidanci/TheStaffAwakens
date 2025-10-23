package net.dulidanci.thestaffawakens.item.cores;

import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.dulidanci.thestaffawakens.render.model.core.GlowstoneCoreModel;
import net.dulidanci.thestaffawakens.util.ManaSupplier;
import net.dulidanci.thestaffawakens.util.ModTags;
import net.minecraft.block.LightBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class GlowstoneCore implements CoreTemplate{
    public static final double mana = 1;

    @Override
    public void activateCore(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient) {
            return;
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            BlockPos playerPosition = player.getBlockPos();
            if (world.getBlockState(playerPosition).isIn(ModTags.REPLACEABLE_BY_LIGHT)) {
                world.setBlockState(playerPosition, ModBlocks.FADING_LIGHT_BLOCK.getDefaultState().with(LightBlock.LEVEL_15, 15));
                world.scheduleBlockTick(playerPosition, ModBlocks.FADING_LIGHT_BLOCK, 800);
                ManaSupplier.decreaseMana(player, mana);

            } else if (world.getBlockState(playerPosition.add(0, 1, 0)).isIn(ModTags.REPLACEABLE_BY_LIGHT)) {
                world.setBlockState(playerPosition.add(0, 1, 0), ModBlocks.FADING_LIGHT_BLOCK.getDefaultState().with(LightBlock.LEVEL_15, 15));
                world.scheduleBlockTick(playerPosition.add(0, 1, 0), ModBlocks.FADING_LIGHT_BLOCK, 800);
                ManaSupplier.decreaseMana(player, mana);
            }
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.GLOWSTONE;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new GlowstoneCoreModel(GlowstoneCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}