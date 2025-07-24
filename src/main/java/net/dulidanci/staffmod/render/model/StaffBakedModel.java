package net.dulidanci.staffmod.render.model;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BuiltinBakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class StaffBakedModel implements BakedModel, FabricBakedModel {
    private final BakedModel staff2d;
    private final BakedModel staff3d;
    private final BakedModel core;

    public StaffBakedModel(BakedModel staff2d, BakedModel staff3d, BakedModel core) {
        this.staff2d = staff2d;
        this.staff3d = staff3d;
        this.core = core;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        staff2d.emitItemQuads(stack, randomSupplier, context);
        staff3d.emitItemQuads(stack, randomSupplier, context);
        core.emitItemQuads(stack, randomSupplier, context);
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        staff2d.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        staff3d.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        core.emitBlockQuads(blockView, state, pos, randomSupplier, context);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        List<BakedQuad> quads = new ArrayList<>();
        quads.addAll(staff2d.getQuads(state, face, random));
        quads.addAll(staff3d.getQuads(state, face, random));
        quads.addAll(core.getQuads(state, face, random));
        return quads;
//        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return staff3d.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return staff3d.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return staff3d.isSideLit();
    }

    @Override
    public boolean isBuiltin() {
        return true;
    }

    @Override
    public Sprite getParticleSprite() {
        return staff3d.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return staff3d.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return staff3d.getOverrides();
    }

    public BakedModel getCoreModel() {
        return core;
    }

    public BakedModel getStaff2dModel() {
        return staff2d;
    }

    public BakedModel getStaff3dModel() {
        return staff3d;
    }
}
