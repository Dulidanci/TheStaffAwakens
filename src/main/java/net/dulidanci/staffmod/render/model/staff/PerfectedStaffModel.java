package net.dulidanci.staffmod.render.model.staff;

import net.dulidanci.staffmod.StaffMod;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class PerfectedStaffModel extends StaffModel {
    private static final Identifier TEXTURE = new Identifier(StaffMod.MOD_ID, "textures/item/perfected_staff_3d.png");
    private final ModelPart root;
    private static final Vector3f core_cube_origin_point = new Vector3f(-2.0F, 15.0F, -1.5F);


    public PerfectedStaffModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("rod", ModelPartBuilder.create()
                .uv(0,0)
                .cuboid(0.0F, 0.0F, 0.0F, 1.0F, 14.5F, 1.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("bottom_plate", ModelPartBuilder.create()
                .uv(4, 0)
                .cuboid(0.0F, 14.5F, 0.0F, 2.5F, 0.5F, 1.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("side_plate", ModelPartBuilder.create()
                .uv(4,2)
                .cuboid(2.0F, 15.0F, 0.0F, 0.5F, 4.0F, 1.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("top_plate", ModelPartBuilder.create()
                .uv(4, 7)
                .cuboid(0.0F, 19.0F, 0.0F, 2.5F, 0.5F, 1.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("peak", ModelPartBuilder.create()
                .uv(4,9)
                .cuboid(0.0F, 19.5F, 0.0F, 1.0F, 1.0F, 1.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 62, 62);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay);
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }

    @Override
    public Vector3f getCoreCubeOriginPoint() {
        return core_cube_origin_point;
    }
}
