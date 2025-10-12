package net.dulidanci.staffmod.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.block.ModBlocks;
import net.dulidanci.staffmod.item.ModItems;
import net.dulidanci.staffmod.item.custom.DynamicStaffItem;
import net.dulidanci.staffmod.util.json.JsonBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BLUEPRINT_PLANKS);

        blockStateModelGenerator.registerSimpleState(ModBlocks.STAFF_UPGRADE_STATION);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (DynamicStaffItem dynamicStaffItem : ModItems.DYNAMIC_STAFFS) {
            registerDynamicStaffModels(itemModelGenerator, dynamicStaffItem);
        }
//        CoreTypes[] coreList = CoreTypes.values();
//        for (CoreTypes core : coreList) {
//            Block coreBlock = core.getBlock();
//            Identifier coreId = Registries.BLOCK.getId(coreBlock);
//            registerCoreModel(itemModelGenerator, coreId);
//        }
    }

    private void registerDynamicStaffModels(ItemModelGenerator itemModelGenerator, DynamicStaffItem item) {
        Block core = item.getCore().getType().getBlock();
        Identifier coreId = Registries.BLOCK.getId(core);
        Item staff = item.getStaff().getType().getItem();
        Identifier staffId = Registries.ITEM.getId(staff);
        itemModelGenerator.writer.accept(
                new Identifier(StaffMod.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath()),
                () -> JsonBuilder.create()
                        .add("parent", "item/generated")
                        .addObject("textures", JsonBuilder.create()
                                .add("layer0", StaffMod.MOD_ID + ":item/" + staffId.getPath())
                                .add("layer1", StaffMod.MOD_ID + ":item/cores/" + coreId.getPath())
                                .build())
                        .build()
        );
        itemModelGenerator.writer.accept(
                new Identifier(StaffMod.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath() + "_3d"),
                () -> JsonBuilder.create()
                        .add("parent", "builtin/entity")
                        .build()
        );
    }

    private void registerCoreModel(ItemModelGenerator itemModelGenerator, Identifier id) {
//        Path base = Paths.get("src", "main", "resources", "assets", "staffmod", "display", "core_transformation.json");
//        JsonObject json = JsonLoader.loadJson(base);
        itemModelGenerator.writer.accept(
                new Identifier(StaffMod.MOD_ID, "item/cores/" + id.getPath()),
                () -> JsonBuilder.create()
                        .add("parent", "minecraft:block/" + id.getPath())
                        .add("gui_light", "front")
//                        .addDisplayFromFile(json)
                        .build()
        );
    }
}
