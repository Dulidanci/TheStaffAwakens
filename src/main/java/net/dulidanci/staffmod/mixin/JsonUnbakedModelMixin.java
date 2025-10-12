package net.dulidanci.staffmod.mixin;

import net.dulidanci.staffmod.StaffMod;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JsonUnbakedModel.class)
public class JsonUnbakedModelMixin {
    @Shadow @Final @Nullable private JsonUnbakedModel.@Nullable GuiLight guiLight;
    @Shadow public String id;
    @Shadow @Nullable protected JsonUnbakedModel parent;

    @Inject(method = "getGuiLight", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/json/JsonUnbakedModel;getGuiLight()Lnet/minecraft/client/render/model/json/JsonUnbakedModel$GuiLight;", shift = At.Shift.AFTER))
    public void getGuiLight(CallbackInfoReturnable<JsonUnbakedModel.GuiLight> cir) {
        if (this.id.startsWith(StaffMod.MOD_ID))
            StaffMod.LOGGER.info("Got GUI light for model {} with value {}", this.id,
//                this.guiLight != null ? this.guiLight : (this.parent != null ? this.parent.getGuiLight() : JsonUnbakedModel.GuiLight.BLOCK));
                this.guiLight != null ? this.guiLight : "PARENT:" + (this.parent != null ? this.parent.getGuiLight() : null));
    }
}
