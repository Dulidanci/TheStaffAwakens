package net.dulidanci.thestaffawakens.render.block.entity;

import net.dulidanci.thestaffawakens.block.custom.StaffWorkbenchBlock;
import net.dulidanci.thestaffawakens.block.entity.StaffWorkbenchBlockEntity;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class StaffWorkbenchBlockEntityRenderer implements BlockEntityRenderer<StaffWorkbenchBlockEntity> {
    public StaffWorkbenchBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(StaffWorkbenchBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, int overlay) {
        ItemStack stack = entity.getRenderStack();
        if (!stack.isEmpty() && stack.getItem() instanceof StaffItem staffItem) {
            matrices.push();

            switch (staffItem.getStaff().getType()) {
                case PERFECTED -> {
                    switch (entity.getCachedState().get(StaffWorkbenchBlock.FACING)) {
                        case NORTH -> {
                            matrices.translate(0.4296875, 1.0625, 0.53125);
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                        }
                        case EAST -> {
                            matrices.translate(0.46875, 1.0625, 0.4296875);
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
                        }
                        case SOUTH -> {
                            matrices.translate(0.5703125, 1.0625, 0.46875);
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
                        }
                        case WEST -> {
                            matrices.translate(0.53125, 1.0625, 0.5703125);
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                        }
                    }
                } case LOG -> {
                    switch (entity.getCachedState().get(StaffWorkbenchBlock.FACING)) {
                        case NORTH -> {
                            matrices.translate(0.4296875, 1.015625, 0.5625);
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                        }
                        case EAST -> {
                            matrices.translate(0.4375, 1.015625, 0.4296875);
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
                        }
                        case SOUTH -> {
                            matrices.translate(0.5703125, 1.015625, 0.4375);
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
                        }
                        case WEST -> {
                            matrices.translate(0.5625, 1.015625, 0.5703125);
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                        }
                    }
                }
            }

            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND,
                    getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

            matrices.pop();
        }
    }

    private int getLightLevel(World world, BlockPos pos) {
        if (world != null) {
            int bLight = world.getLightLevel(LightType.BLOCK, pos);
            int sLight = world.getLightLevel(LightType.SKY, pos);
            return LightmapTextureManager.pack(bLight, sLight);
        }
        return 0;
    }
}
