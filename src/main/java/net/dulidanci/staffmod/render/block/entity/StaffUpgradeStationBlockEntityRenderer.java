package net.dulidanci.staffmod.render.block.entity;

import net.dulidanci.staffmod.block.entity.StaffUpgradeStationBlockEntity;
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

public class StaffUpgradeStationBlockEntityRenderer implements BlockEntityRenderer<StaffUpgradeStationBlockEntity> {
    public StaffUpgradeStationBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(StaffUpgradeStationBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, int overlay) {
        ItemStack stack = entity.getRenderStack();
        if (!stack.isEmpty()) {
            matrices.push();

            matrices.translate(0.5, 1.0625, 0.53125);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

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
