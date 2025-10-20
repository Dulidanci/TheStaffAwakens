package net.dulidanci.staffmod.render.model.core;

import net.dulidanci.staffmod.StaffMod;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class AnvilCoreModel extends CoreModel {
    private static final Identifier TEXTURE = new Identifier(StaffMod.MOD_ID, "textures/item/anvil_core.png");
    private final ModelPart root;

    public AnvilCoreModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData(Vector3f cubeOriginPoint) {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("base", ModelPartBuilder.create()
                .uv(0,0)
                .cuboid(cubeOriginPoint.x + 0.5F, cubeOriginPoint.y, cubeOriginPoint.z + 0.5F, 3.0F, 1.0F, 3.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("plate", ModelPartBuilder.create()
                .uv(0,4)
                .cuboid(cubeOriginPoint.x + 1.0F, cubeOriginPoint.y + 1.0F, cubeOriginPoint.z + 0.75F, 2.0F, 0.25F, 2.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("middle", ModelPartBuilder.create()
                        .uv(9,4)
                        .cuboid(cubeOriginPoint.x + 1.5F, cubeOriginPoint.y + 1.25F, cubeOriginPoint.z + 1.0F, 1.0F, 1.25F, 2.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("top", ModelPartBuilder.create()
                        .uv(0,8)
                        .cuboid(cubeOriginPoint.x + 0.75F, cubeOriginPoint.y + 2.5F, cubeOriginPoint.z, 2.5F, 1.5F, 4.0F, Dilation.NONE, 0.25F, 0.25F),
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
