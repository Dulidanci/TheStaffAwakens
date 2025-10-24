package net.dulidanci.thestaffawakens.render.item;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.dulidanci.thestaffawakens.render.model.core.CoreModel;
import net.dulidanci.thestaffawakens.render.model.staff.StaffModel;
import net.dulidanci.thestaffawakens.util.json.ModelTransformationLoader;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class StaffItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final Identifier displayDirectory = new Identifier(TheStaffAwakens.MOD_ID, "display/");

    @Override
    public void render(ItemStack itemStack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        if (!(itemStack.getItem() instanceof StaffItem item)) {
            return;
        }

        Identifier staffItemId = Registries.ITEM.getId(item.getStaff().getType().getItem());

        matrices.push();

        ModelTransformation staffTransformation = ModelTransformationLoader.getOrLoadModelTransformation(new Identifier(
                displayDirectory.getNamespace(), displayDirectory.getPath() + staffItemId.getPath() + ".json"));
        staffTransformation.getTransformation(mode).apply(false, matrices);

        StaffModel staffModel = item.getStaff().getModel();
        Identifier STAFF_TEXTURE = staffModel.getTexture();

        CoreModel coreModel = item.getCore().getModel(staffModel.getCoreCubeOriginPoint());
        Identifier CORE_TEXTURE = coreModel.getTexture();

        VertexConsumer staffVertices = vertices.getBuffer(staffModel.getLayer(STAFF_TEXTURE));
        staffModel.render(matrices, staffVertices, light, overlay, 1f, 1f, 1f, 1f);

        VertexConsumer coreVertices = vertices.getBuffer(coreModel.getLayer(CORE_TEXTURE));
        coreModel.render(matrices, coreVertices, light, overlay, 1F, 1F, 1F, 1F);

        matrices.pop();
    }
}
