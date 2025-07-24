package net.dulidanci.staffmod;

import net.dulidanci.staffmod.item.custom.DynamicStaffItem;
import net.dulidanci.staffmod.render.model.StaffBakedModel;
import net.dulidanci.staffmod.render.model.StaffUnbakedModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelResolver;
import net.minecraft.block.Block;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StaffModelLoadingPlugin implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context ctx) {
        ctx.resolveModel().register((ModelResolver.Context context) -> {
            Identifier id = context.id();

            if (!(id.toString().startsWith(StaffMod.MOD_ID + ":item/"))) return null;
            System.out.println(id);

            String path = id.getPath().substring("item/".length());
            Identifier itemId = new Identifier(id.getNamespace(), path);
            System.out.println(itemId);

            Item item = Registries.ITEM.get(itemId);
            System.out.println(item);

            if (!(item instanceof DynamicStaffItem dynamicStaffItem)) return null;

            Block coreBlock = dynamicStaffItem.getCore().getType().getBlock();
            Identifier coreId = Registries.BLOCK.getId(coreBlock);
            Identifier coreModelId = new Identifier(coreId.getNamespace(), "block/" + coreId.getPath());

            Item staffItem = dynamicStaffItem.getStaff().getType().getItem();
            Identifier staffId = Registries.ITEM.getId(staffItem);
            Identifier staffModel2dId = new Identifier(staffId.getNamespace(), "item/" + staffId.getPath());
            Identifier staffModel3dId = new Identifier(staffId.getNamespace(), "item/" + staffId.getPath() + "_3d");

            System.out.println(coreModelId + "; " + staffModel2dId + "; " + staffModel3dId);

            return new StaffUnbakedModel(staffModel2dId, staffModel3dId, coreModelId);
        });
    }

//    public StaffBakedModel getStaffModel(DynamicStaffItem dynamicStaffItem, ModelTransformationMode mode) {
//        Block coreBlock = dynamicStaffItem.getCore().getType().getBlock();
//        Identifier coreId = Registries.BLOCK.getId(coreBlock);
//        Identifier coreModelId = new Identifier(coreId.getNamespace(), "block/" + coreId.getPath());
//
//        Item staffItem = dynamicStaffItem.getStaff().getType().getItem();
//        Identifier staffId = Registries.ITEM.getId(staffItem);
//        Identifier staffModelId = switch (mode) {
//            case GUI, FIXED -> new Identifier(staffId.getNamespace(), "item/" + staffId.getPath());
//            default -> new Identifier(staffId.getNamespace(), "item/" + staffId.getPath() + "_3d");
//        };
//
//        StaffUnbakedModel staffUnbakedModel = new StaffUnbakedModel(staffModelId, staffModelId, coreModelId);
//
//        return
//    }
}
