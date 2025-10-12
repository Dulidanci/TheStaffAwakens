package net.dulidanci.staffmod.render.model;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.util.json.ModelTransformationLoader;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class StaffBakedModel implements BakedModel, FabricBakedModel {
    private final BakedModel staff;
    private final BakedModel core;

    public StaffBakedModel(BakedModel staff, BakedModel core) {
        this.staff = staff;
        this.core = core;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        ModelTransformation ModelTransformation = ModelTransformationLoader.getOrLoadModelTransformation(new Identifier(StaffMod.MOD_ID, "display/core_transformation.json"));
//
//        for (ModelTransformationMode mode : ModelTransformationMode.values()) {
//            StaffMod.LOGGER.info("Getting Transformation for mode {}", mode);
//            Transformation transformation = ModelTransformation.getTransformation(mode);
//
//            MatrixStack matrixStack = new MatrixStack();
//            matrixStack.multiply(new Quaternionf().rotationXYZ(
//                transformation.rotation.x * (float) (Math.PI / 180.0),
//                transformation.rotation.y * (float) (Math.PI / 180.0),
//                transformation.rotation.z * (float) (Math.PI / 180.0)));
//            matrixStack.translate(transformation.translation.x, transformation.translation.y, transformation.translation.z);
//            System.out.println(transformation.translation.x);
//            System.out.println(transformation.translation.y);
//            System.out.println(transformation.translation.z);
//            matrixStack.scale(transformation.scale.x, transformation.scale.y, transformation.scale.z);
//
//            core.getTransformation().getTransformation(mode).apply(
//                    mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND || mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND,
//                              matrixStack);
//        }
//
//        StaffMod.LOGGER.info("emitItemQuads ran!");
        staff.emitItemQuads(stack, randomSupplier, context);
        core.emitItemQuads(stack, randomSupplier, context);
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {

    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return staff.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return staff.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return staff.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return staff.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return staff.getOverrides();
    }
}
