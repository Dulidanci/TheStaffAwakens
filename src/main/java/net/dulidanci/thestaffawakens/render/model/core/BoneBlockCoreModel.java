package net.dulidanci.thestaffawakens.render.model.core;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class BoneBlockCoreModel extends CoreModel {
    private static final Identifier TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "textures/item/bone_block_core.png");
    private final ModelPart root;

    public BoneBlockCoreModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData(Vector3f cubeOriginPoint) {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("cube", ModelPartBuilder.create()
                .uv(0,0)
                .cuboid(cubeOriginPoint.x, cubeOriginPoint.y, cubeOriginPoint.z, 4.0F, 4.0F, 4.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay);
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }
}
