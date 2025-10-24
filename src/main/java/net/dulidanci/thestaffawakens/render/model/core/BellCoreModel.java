package net.dulidanci.thestaffawakens.render.model.core;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class BellCoreModel extends CoreModel {
    private static final Identifier TEXTURE = new Identifier(TheStaffAwakens.MOD_ID, "textures/item/bell_core.png");
    private final ModelPart root;

    public BellCoreModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData(Vector3f cubeOriginPoint) {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

//        cubeOriginPoint.add(0.5F, 0.0F, 0.0F);

        root.addChild("base", ModelPartBuilder.create()
                .uv(0,0)
                .cuboid(cubeOriginPoint.x + 1.5F, cubeOriginPoint.y + 1.0F, cubeOriginPoint.z + 1.0F, 2.0F, 0.5F, 2.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("body", ModelPartBuilder.create()
                .uv(0,3)
                .cuboid(cubeOriginPoint.x + 1.75F, cubeOriginPoint.y + 1.5F, cubeOriginPoint.z + 1.25F, 1.5F, 1.75F, 1.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("hanger", ModelPartBuilder.create()
                .uv(6,3)
                .cuboid(cubeOriginPoint.x + 2.25F, cubeOriginPoint.y + 3.25F, cubeOriginPoint.z + 1.75F, 0.5F, 0.75F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 32, 32);
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
