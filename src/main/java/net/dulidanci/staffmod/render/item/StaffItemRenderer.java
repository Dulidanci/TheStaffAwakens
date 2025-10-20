package net.dulidanci.staffmod.render.item;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.cores.CoreTypes;
import net.dulidanci.staffmod.item.custom.StaffItem;
import net.dulidanci.staffmod.render.model.core.CoreModel;
import net.dulidanci.staffmod.render.model.staff.StaffModel;
import net.dulidanci.staffmod.util.json.ModelTransformationLoader;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class StaffItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final Identifier displayDirectory = new Identifier(StaffMod.MOD_ID, "display/");

    @Override
    public void render(ItemStack itemStack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        if (!(itemStack.getItem() instanceof StaffItem item)) {
            return;
        }

        Identifier itemId = Registries.ITEM.getId(item.getStaff().getType().getItem());
        boolean isLeftHanded = (mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND);

        matrices.push();

        switch (mode) {
            case THIRD_PERSON_RIGHT_HAND -> {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(70));
                matrices.translate(0.45, -0.15, -0.325);
                matrices.scale(1.75F, 1.75F, 1.75F);
            }
            case THIRD_PERSON_LEFT_HAND -> {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(70));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                matrices.translate(-0.55, -0.15, 0.225);
                matrices.scale(1.75F, 1.75F, 1.75F);
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                matrices.translate(0.85, -0.5, 0.0);
                matrices.scale(1.75F, 1.75F, 1.75F);
            }
            case FIRST_PERSON_LEFT_HAND -> {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                matrices.translate(0.0, -0.4375, 0.0);
                matrices.scale(1.75F, 1.75F, 1.75F);
            }
            case GROUND -> {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                matrices.translate(0.5, -0.1, -0.5);
            }
        }

//            ModelTransformation staffTransformation = ModelTransformationLoader.getOrLoadModelTransformation(new Identifier(
//                    displayDirectory.getNamespace(), displayDirectory.getPath() + itemId.getPath() + ".json"));
//            staffTransformation.getTransformation(mode).apply(false, matrices);

        StaffModel staffModel = item.getStaff().getModel();
        Identifier STAFF_TEXTURE = staffModel.getTexture();

        CoreModel coreModel = item.getCore().getModel(staffModel.getCoreCubeOriginPoint());
        Identifier CORE_TEXTURE = coreModel.getTexture();


//            float time = MinecraftClient.getInstance().world.getTime() + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);

        // animáció
//            staffModel.setAngles(time);

        VertexConsumer staffVertices = vertices.getBuffer(staffModel.getLayer(STAFF_TEXTURE));
        staffModel.render(matrices, staffVertices, light, overlay, 1f, 1f, 1f, 1f);

        VertexConsumer coreVertices = vertices.getBuffer(coreModel.getLayer(CORE_TEXTURE));
        coreModel.render(matrices, coreVertices, light, overlay, 1F, 1F, 1F, 1F);

        matrices.pop();
    }
}
