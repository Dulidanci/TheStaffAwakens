package net.dulidanci.staffmod.render.model;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class CoreBakedModel implements BakedModel, FabricBakedModel {
    private final BakedModel core;

    public CoreBakedModel(BakedModel core) {
        this.core = core;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {

    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        core.emitItemQuads(stack, randomSupplier, context);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return core.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return core.hasDepth();
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
        return core.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return core.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return core.getOverrides();
    }
}
