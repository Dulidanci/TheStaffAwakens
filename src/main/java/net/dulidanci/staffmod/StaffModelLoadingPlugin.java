package net.dulidanci.staffmod;

import net.dulidanci.staffmod.item.custom.DynamicStaffItem;
import net.dulidanci.staffmod.render.model.CoreBakedModel;
import net.dulidanci.staffmod.render.model.CoreUnbakedModel;
import net.dulidanci.staffmod.render.model.StaffUnbakedModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelResolver;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StaffModelLoadingPlugin implements ModelLoadingPlugin {
    /**
     * Should intercept Identifiers:
     * - staffmod:item/example_staff_with_example_core
     * - staffmod:item/example_staff_with_example_core_3d
     * - staffmod:item/cores/block_identifier
     */
    @Override
    public void onInitializeModelLoader(Context ctx) {
        ctx.resolveModel().register((ModelResolver.Context context) -> {
            Identifier id = context.id();

            if (!(id.toString().startsWith(StaffMod.MOD_ID + ":item/"))) return null;
//            System.out.println(id);

            String path = id.getPath().substring("item/".length());
            boolean is3d = path.endsWith("_3d");
            if (is3d) {
                path = path.substring(0, path.length() - 3);
            }

            if (path.startsWith("core/")) {
                path = path.substring("core/".length());
                return new CoreUnbakedModel(new Identifier(StaffMod.MOD_ID, "item/cores/" + path));
            }

            Identifier itemId = new Identifier(id.getNamespace(), path);

            Item item = Registries.ITEM.get(itemId);

            if (!(item instanceof DynamicStaffItem dynamicStaffItem)) return null;

            Block coreBlock = dynamicStaffItem.getCore().getType().getBlock();
            Identifier coreId = Registries.BLOCK.getId(coreBlock);
            Identifier coreModelId = new Identifier(StaffMod.MOD_ID, "item/core/" + coreId.getPath());

            Item staffItem = dynamicStaffItem.getStaff().getType().getItem();
            Identifier staffId = Registries.ITEM.getId(staffItem);
            Identifier staffModelId = is3d ? new Identifier(staffId.getNamespace(), "item/" + staffId.getPath() + "_3d") :
                    new Identifier(staffId.getNamespace(), "item/" + staffId.getPath());

//            System.out.println(coreModelId + "; " + staffModelId);

            return new StaffUnbakedModel(staffModelId, coreModelId);
        });
    }
}
