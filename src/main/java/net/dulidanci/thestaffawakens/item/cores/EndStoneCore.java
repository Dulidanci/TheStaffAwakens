package net.dulidanci.thestaffawakens.item.cores;

import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.dulidanci.thestaffawakens.render.model.core.EndStoneCoreModel;
import net.dulidanci.thestaffawakens.util.ManaSupplier;
import net.dulidanci.thestaffawakens.util.ModTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class EndStoneCore implements CoreTemplate{
    public static final double mana = 4;

    @Override
    public void activateCore(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient()) {
            return;
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            Vec3d facing = player.getRotationVector();
            Vec3d stepVector = new Vec3d(facing.getX(), 0, facing.getZ()).normalize().multiply(4);
            Vec3d destination = player.getPos().add(stepVector).add(0,1,0);
            BlockPos destinationBlockPos;

            for (int i = 1; i < 50; i++) {
                destination.add(stepVector);
                destinationBlockPos = new BlockPos((int) Math.round(destination.x), (int) Math.round(destination.y), (int) Math.round(destination.z));
                if (world.getBlockState(destinationBlockPos).isIn(ModTags.CAN_TELEPORT_INTO)) {
                    break;
                }
            }

            player.teleport(destination.getX(), destination.getY(), destination.getZ());

            ManaSupplier.decreaseMana(player, mana);
        }
    }

    @Override
    public CoreTypes getType() {
        return CoreTypes.END_STONE;
    }

    @Override
    public CoreModel getModel(Vector3f cubeOriginPoint) {
        return new EndStoneCoreModel(EndStoneCoreModel.getTexturedModelData(cubeOriginPoint).createModel());
    }
}