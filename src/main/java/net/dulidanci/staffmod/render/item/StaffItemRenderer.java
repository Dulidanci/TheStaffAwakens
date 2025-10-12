package net.dulidanci.staffmod.render.item;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.custom.DynamicStaffItem;
import net.dulidanci.staffmod.mixin.ItemRendererAccessor;
import net.dulidanci.staffmod.render.model.StaffBakedModel;
import net.dulidanci.staffmod.util.json.ModelTransformationLoader;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MatrixUtil;
import net.minecraft.util.math.RotationAxis;

public class StaffItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    /*
    @Override
    public void render(ItemStack itemStack, ModelTransformationMode modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
        if (!(itemStack.getItem() instanceof DynamicStaffItem dynamicStaffItem)) return;

        MinecraftClient client = MinecraftClient.getInstance();
        boolean is2d = switch (modelTransformationMode) {
            case GUI, FIXED -> true;
            default -> false;
        };
        boolean isLeftHanded = switch (modelTransformationMode) {
            case FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> true;
            default -> false;
        };

        Identifier itemId = Registries.ITEM.getId(dynamicStaffItem);
        ModelIdentifier modelId = is2d ? new ModelIdentifier(itemId.getNamespace(), itemId.getPath() + "_2d", "inventory") :
                new ModelIdentifier(itemId.getNamespace(), itemId.getPath() + "_3d", "inventory");
        BakedModel fullModel = client.getBakedModelManager().getModel(modelId);

        RenderLayer renderLayer = RenderLayers.getItemLayer(itemStack, true);
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, renderLayer, true, itemStack.hasGlint());

        ((ItemRendererAccessor) client.getItemRenderer()).invokeRenderBakedItemModel(fullModel, itemStack, i, i1, matrixStack, vertexConsumer);
    }

     */

    /*
    @Override
    public void render(ItemStack itemStack, ModelTransformationMode modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
        StaffMod.LOGGER.info("Hi {}", getClass());
        if (!(itemStack.getItem() instanceof DynamicStaffItem dynamicStaffItem)) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        boolean is2d = switch (modelTransformationMode) {
            case GUI, FIXED -> true;
            default -> false;
        };
        boolean isLeftHanded = switch (modelTransformationMode) {
            case FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> true;
            default -> false;
        };

//        BakedModel fullModel = client.getBakedModelManager().getModel(Registries.ITEM.getId(itemStack.getItem()));
//        System.out.println(fullModel);
////        client.getItemRenderer().renderItem(itemStack, modelTransformationMode, false, matrixStack, vertexConsumerProvider, i, i1, fullModel);
//
//
//        if (!(fullModel instanceof StaffBakedModel staffBakedModel)) return;
//        System.out.println(is2d);
//
//        BakedModel coreModel = staffBakedModel.getCoreModel();
//        BakedModel staffModel = is2d ? staffBakedModel.getStaff2dModel() : staffBakedModel.getStaff3dModel();
//
//        matrixStack.push();
//        client.getItemRenderer().renderItem(itemStack, modelTransformationMode, false, matrixStack, vertexConsumerProvider, i, i1, coreModel);
//        client.getItemRenderer().renderItem(itemStack, modelTransformationMode, false, matrixStack, vertexConsumerProvider, i, i1, staffModel);
//        matrixStack.pop();
//
        Item staffItem = dynamicStaffItem.getStaff().getType().getItem();
        Identifier staffId = Registries.ITEM.getId(staffItem);
        ModelIdentifier staffModelId = new ModelIdentifier(staffId, "inventory");

        Block coreBlock = dynamicStaffItem.getCore().getType().getBlock();

        BakedModel staffModel = client.getBakedModelManager().getModel(staffModelId);
        BakedModel coreModel = client.getBakedModelManager().getBlockModels().getModel(coreBlock.getDefaultState());

        matrixStack.push();
        matrixStack.translate(0.5F, 0.5F, 0.0);
        client.getItemRenderer().renderItem(new ItemStack(staffItem), modelTransformationMode, isLeftHanded, matrixStack, vertexConsumerProvider, i, i1, staffModel);
//        client.getItemRenderer().renderItem(new ItemStack(coreBlock.asItem()), modelTransformationMode, isLeftHanded, matrixStack, vertexConsumerProvider, i, i1, coreModel);
        matrixStack.pop();
    }

     */



    @Override
    public void render(ItemStack itemStack, ModelTransformationMode modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
        if (!(itemStack.getItem() instanceof DynamicStaffItem dynamicStaffItem)) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        boolean isLeftHanded = (modelTransformationMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || modelTransformationMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND);

//        RenderLayer layer = RenderLayers.getItemLayer(itemStack, true);
//        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, layer, true, itemStack.hasGlint());

        Block coreBlock = dynamicStaffItem.getCore().getType().getBlock();
        BakedModel coreModel = client.getBakedModelManager().getBlockModels().getModel(coreBlock.getDefaultState());

        matrixStack.push();

        ModelTransformation coreModelTransformation = ModelTransformationLoader.getOrLoadModelTransformation(new Identifier(StaffMod.MOD_ID, "display/core_transformation.json"));
        coreModelTransformation.getTransformation(modelTransformationMode).apply(isLeftHanded, matrixStack);

//        switch (modelTransformationMode) {
//            case THIRD_PERSON_LEFT_HAND -> {
//                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(68));
//                matrixStack.translate(0.338541, 1.890625, -0.427083);
//                matrixStack.scale(0.328125f, 0.328125f, 0.328125f);
//            }
//            case THIRD_PERSON_RIGHT_HAND -> {
//                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(68));
//                matrixStack.translate(0.328125, 1.890625, -0.40625);
//                matrixStack.scale(0.328125f, 0.328125f, 0.328125f);
//            }
//            case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND, HEAD -> {
//                matrixStack.translate(0.375, 1.1875, 0.375);
//                matrixStack.scale(0.1875f, 0.1875f, 0.1875f);
//            }
//            case GROUND -> {
//                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
//                matrixStack.translate(0.4375, -0.03125, 0.4375);
//                matrixStack.scale(0.1875f, 0.1875f, 0.1875f);
//            }
//        }

//        client.getItemRenderer().renderItem(new ItemStack(coreBlock.asItem()), modelTransformationMode,
//                i, OverlayTexture.DEFAULT_UV,
//                matrixStack, vertexConsumerProvider, client.world, 1);
        client.getItemRenderer().renderItem(new ItemStack(coreBlock.asItem()), modelTransformationMode, isLeftHanded, matrixStack, vertexConsumerProvider, i, i1, coreModel);
//        ((ItemRendererAccessor) client.getItemRenderer()).invokeRenderBakedItemModel(coreModel, itemStack, i, i1, matrixStack, vertexConsumer);

        matrixStack.pop();

        Item staffItem = dynamicStaffItem.getStaff().getType().getItem();
        Identifier staffId = Registries.ITEM.getId(staffItem);
        ModelIdentifier staffModelId = new ModelIdentifier(staffId.getNamespace(), staffId.getPath() + "_3d", "inventory");
        BakedModel staffModel = client.getBakedModelManager().getModel(staffModelId);

        matrixStack.push();

//        ModelTransformation staffModelTransformation = ModelTransformationLoader.getOrLoadModelTransformation(new Identifier(StaffMod.MOD_ID, "display/staff_transformation.json"));
//
//        staffModelTransformation.getTransformation(modelTransformationMode).apply(isLeftHanded, matrixStack);

        switch (modelTransformationMode) {
            case FIRST_PERSON_RIGHT_HAND -> {
                matrixStack.translate(0.5, 0.2, 0.0);
                matrixStack.scale(1.75f, 1.75f, 1.75f);
            }
            case THIRD_PERSON_LEFT_HAND -> matrixStack.translate(-0.72, -0.109375, -0.35);
            case THIRD_PERSON_RIGHT_HAND -> matrixStack.translate(-0.785, -0.125, -0.35);
            case GROUND -> matrixStack.translate(0.0, -1.0, 0.0);
        }

//        ((ItemRendererAccessor) client.getItemRenderer()).invokeRenderBakedItemModel(staffModel, itemStack,
//                i, i1, matrixStack, vertexConsumer);
//        client.getItemRenderer().renderItem(new ItemStack(staffItem), modelTransformationMode, false, matrixStack, vertexConsumerProvider, i, i1, staffModel);
//        client.getItemRenderer().renderItem(2 * new ItemStack(staffItem), 2 *modelTransformationMode, false, 2 *matrixStack, 2 * vertexConsumerProvider, 2i, i1, staffModel);
        client.getItemRenderer().renderItem(new ItemStack(staffItem), modelTransformationMode, isLeftHanded, matrixStack, vertexConsumerProvider, i, i1, staffModel);
//        client.getItemRenderer().renderItem(new ItemStack(staffItem), modelTransformationMode,
//                LightmapTextureManager.pack(15, 15), OverlayTexture.DEFAULT_UV, matrixStack,
//                vertexConsumerProvider, client.world, 1);

//        if (modelTransformationMode == ModelTransformationMode.GUI || modelTransformationMode == ModelTransformationMode.FIXED) {
//            DiffuseLighting.enableGuiDepthLighting();
//        }
        matrixStack.pop();
    }
}
