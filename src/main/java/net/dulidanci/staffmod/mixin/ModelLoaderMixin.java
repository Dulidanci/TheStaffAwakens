package net.dulidanci.staffmod.mixin;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.ModItems;
import net.dulidanci.staffmod.item.cores.CoreTypes;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
            ordinal = 3, shift = At.Shift.AFTER))
    public void addStaffModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
                               Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        for (Item item : ModItems.STAFFS) {
            Identifier id = Registries.ITEM.getId(item);
            this.addModel(new ModelIdentifier(id.getNamespace(), id.getPath() + "_3d", "inventory"));
        }
//        CoreTypes[] coreList = CoreTypes.values();
//        for (CoreTypes core : coreList) {
//            Block coreBlock = core.getBlock();
//            Identifier coreId = Registries.BLOCK.getId(coreBlock);
//            this.addModel(new ModelIdentifier(StaffMod.MOD_ID, "cores/" + coreId.getPath(), "inventory"));
//            this.addModel(new ModelIdentifier(StaffMod.MOD_ID, "core/" + coreId.getPath(), "inventory"));
//        }
    }
//
//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
//            ordinal = 3, shift = At.Shift.AFTER))
//    public void addDynamicStaffModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
//                               Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
//        for (Item item : ModItems.STAFFS) {
//            Identifier id = Registries.ITEM.getId(item);
//            this.addModel(new ModelIdentifier(id.getNamespace(), id.getPath() + "_2d", "inventory"));
//            this.addModel(new ModelIdentifier(id.getNamespace(), id.getPath() + "_3d", "inventory"));
//        }
//    }

//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
//            ordinal = 3, shift = At.Shift.AFTER))
//    public void addRegularStaff(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
//                                Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
//        this.addModel(new ModelIdentifier(StaffMod.MOD_ID, "regular_staff_3d", "inventory"));
//    }
//
//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
//            ordinal = 3, shift = At.Shift.AFTER))
//    public void addLogStaff(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
//                            Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
//        this.addModel(new ModelIdentifier(StaffMod.MOD_ID, "log_staff_3d", "inventory"));
//    }
}