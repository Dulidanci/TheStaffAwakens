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
    private final Identifier staffModel2dId;
    private final Identifier staffModel3dId;
    private final Identifier coreModelId;

    public StaffUnbakedModel(Identifier staffModel2dId, Identifier staffModel3dId, Identifier coreModelId) {
        this.staffModel2dId = staffModel2dId;
        this.staffModel3dId = staffModel3dId;
        this.coreModelId = coreModelId;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of(staffModel2dId, staffModel3dId, coreModelId);
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Nullable
    @Override
    public StaffBakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {

        UnbakedModel staff2dUnbakedModel = baker.getOrLoadModel(staffModel2dId);
        UnbakedModel staff3dUnbakedModel = baker.getOrLoadModel(staffModel3dId);
        UnbakedModel coreUnbakedModel = baker.getOrLoadModel(coreModelId);

        BakedModel staff2dBakedModel = staff2dUnbakedModel.bake(baker, textureGetter, rotationContainer, staffModel2dId);
        BakedModel staff3dBakedModel = staff3dUnbakedModel.bake(baker, textureGetter, rotationContainer, staffModel3dId);
        BakedModel coreBakedModel = coreUnbakedModel.bake(baker, textureGetter, rotationContainer, coreModelId);


        return new StaffBakedModel(staff2dBakedModel, staff3dBakedModel, coreBakedModel);
    }
}
