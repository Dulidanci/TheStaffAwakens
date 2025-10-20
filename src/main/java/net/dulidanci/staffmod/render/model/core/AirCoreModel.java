package net.dulidanci.staffmod.render.model.core;

import net.dulidanci.staffmod.StaffMod;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class AirCoreModel extends CoreModel {
    private static final Identifier TEXTURE = new Identifier(StaffMod.MOD_ID, "textures/item/air_core.png");
    private final ModelPart root;

    public AirCoreModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData(Vector3f cubeOriginPoint) {
        ModelData data = new ModelData();

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
