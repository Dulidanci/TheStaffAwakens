package net.dulidanci.staffmod.render.model.staff;

import net.dulidanci.staffmod.StaffMod;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class LogStaffModel extends StaffModel{
    private static final Identifier TEXTURE = new Identifier(StaffMod.MOD_ID, "textures/item/log_staff_3d.png");
    private final ModelPart root;
    private static final Vector3f core_cube_origin_point = new Vector3f(-1.0F, 19.0F, -1.0F);

    public LogStaffModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("stump", ModelPartBuilder.create()
                .uv(4,0)
                .cuboid(0.0F, 0.0F, 0.0F, 1.5F, 3.0F, 1.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("branch", ModelPartBuilder.create()
                .uv(0,0)
                .cuboid(0.5F, 3.0F, 0.5F, 1.0F, 16.0F, 1.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("peak", ModelPartBuilder.create()
                        .uv(10,0)
                        .cuboid(0.75F, 19.0F, 0.75F, 0.5F, 4.5F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("twig_1", ModelPartBuilder.create()
                        .uv(4,5)
                        .cuboid(0.0F, 3.0F, 0.0F, 0.5F, 2.0F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("twig_2", ModelPartBuilder.create()
                        .uv(6,5)
                        .cuboid(-0.5F, 4.5F, 0.0F, 0.5F, 1.5F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("twig_3", ModelPartBuilder.create()
                        .uv(8,5)
                        .cuboid(-0.5F, 5.5F, -0.5F, 0.5F, 1.0F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("small_bush", ModelPartBuilder.create()
                        .uv(4,8)
                        .cuboid(-0.75F, 6.5F, -0.75F, 1.0F, 1.0F, 1.0F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("bulge_1", ModelPartBuilder.create()
                        .uv(4,10)
                        .cuboid(1.5F, 10.0F, 0.5F, 0.5F, 0.5F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("small_leaf_1", ModelPartBuilder.create()
                        .uv(6,10)
                        .cuboid(2.0F, 10.5F, 0.0F, 0.0F, 0.5F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("bulge_2", ModelPartBuilder.create()
                        .uv(4,11)
                        .cuboid(0.5F, 16.0F, 1.5F, 0.5F, 0.5F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("small_leaf_2", ModelPartBuilder.create()
                        .uv(6,11)
                        .cuboid(1.0F, 16.5F, 2.0F, 0.0F, 0.5F, 0.5F, Dilation.NONE, 0.25F, 0.25F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 68, 68);
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
