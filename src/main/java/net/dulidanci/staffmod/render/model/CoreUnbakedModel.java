package net.dulidanci.staffmod.render.model;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CoreUnbakedModel implements UnbakedModel {
    private final Identifier coreModelId;

    public CoreUnbakedModel(Identifier coreModelId) {
        this.coreModelId = coreModelId;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of(coreModelId);
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        BakedModel coreBakedModel = baker.bake(coreModelId, rotationContainer);

        return new CoreBakedModel(coreBakedModel);
    }
}
