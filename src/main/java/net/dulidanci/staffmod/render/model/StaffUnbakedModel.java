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

public class StaffUnbakedModel implements UnbakedModel {
    private final Identifier staffModelId;
    private final Identifier coreModelId;

    public StaffUnbakedModel(Identifier staffModelId, Identifier coreModelId) {
        this.staffModelId = staffModelId;
        this.coreModelId = coreModelId;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of(staffModelId, coreModelId);
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Nullable
    @Override
    public StaffBakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {

        BakedModel staffBakedModel = baker.getOrLoadModel(staffModelId).bake(baker, textureGetter, rotationContainer, staffModelId);
        BakedModel coreBakedModel = baker.getOrLoadModel(coreModelId).bake(baker, textureGetter, rotationContainer, coreModelId);

        return new StaffBakedModel(staffBakedModel, coreBakedModel);
    }
}
