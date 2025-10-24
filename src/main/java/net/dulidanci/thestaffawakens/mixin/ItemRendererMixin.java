package net.dulidanci.thestaffawakens.mixin;

import net.dulidanci.thestaffawakens.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useStaffModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded,
                                    MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        for (Item item : ModItems.STAFFS) {
            if (stack.isOf(item) && (renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.FIXED)) {
                Identifier id = Registries.ITEM.getId(item);
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(
                        id.getNamespace(), id.getPath() + "_3d", "inventory"));
            }
        }
        return value;
    }
}
