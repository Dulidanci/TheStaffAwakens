package net.dulidanci.thestaffawakens.datagen;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.dulidanci.thestaffawakens.item.ModItems;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.dulidanci.thestaffawakens.util.json.JsonBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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

        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.STAFF_WORKBENCH);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (StaffItem staffItem : ModItems.STAFFS) {
            registerStaffModels(itemModelGenerator, staffItem);
        }
    }

    private void registerStaffModels(ItemModelGenerator itemModelGenerator, StaffItem item) {

        Block core = item.getCore().getType().getBlock();
        Identifier coreId = Registries.BLOCK.getId(core);

        Item staff = item.getStaff().getType().getItem();
        Identifier staffId = Registries.ITEM.getId(staff);

        if (core == Blocks.AIR) {
            staffModelWithAir(itemModelGenerator, item, staffId);
        } else if (staff == ModItems.PERFECTED_STAFF && core != Blocks.BELL) {
            staffModelWithShiftedCore(itemModelGenerator, item, staffId, coreId);
        } else {
            staffModelWithCenteredCore(itemModelGenerator, item, staffId, coreId);
        }

        itemModelGenerator.writer.accept(
                new Identifier(TheStaffAwakens.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath() + "_3d"),
                () -> JsonBuilder.create()
                        .add("parent", "builtin/entity")
                        .build()
        );
    }

    private void staffModelWithCenteredCore(ItemModelGenerator itemModelGenerator, StaffItem item, Identifier staffId, Identifier coreId) {
        itemModelGenerator.writer.accept(
                new Identifier(TheStaffAwakens.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath()),
                () -> JsonBuilder.create()
                        .add("parent", "item/generated")
                        .addObject("textures", JsonBuilder.create()
                                .add("layer0", TheStaffAwakens.MOD_ID + ":item/" + staffId.getPath())
                                .add("layer1", TheStaffAwakens.MOD_ID + ":item/cores/" + coreId.getPath())
                                .build())
                        .build()
        );

    }

    private void staffModelWithShiftedCore(ItemModelGenerator itemModelGenerator, StaffItem item, Identifier staffId, Identifier coreId) {
        itemModelGenerator.writer.accept(
                new Identifier(TheStaffAwakens.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath()),
                () -> JsonBuilder.create()
                        .add("parent", "item/generated")
                        .addObject("textures", JsonBuilder.create()
                                .add("layer0", TheStaffAwakens.MOD_ID + ":item/" + staffId.getPath())
                                .add("layer1", TheStaffAwakens.MOD_ID + ":item/cores/" + coreId.getPath() + "_shifted")
                                .build())
                        .build()
        );
    }

    private void staffModelWithAir(ItemModelGenerator itemModelGenerator, StaffItem item, Identifier staffId) {
        itemModelGenerator.writer.accept(
                new Identifier(TheStaffAwakens.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath()),
                () -> JsonBuilder.create()
                        .add("parent", "item/generated")
                        .addObject("textures", JsonBuilder.create()
                                .add("layer0", TheStaffAwakens.MOD_ID + ":item/" + staffId.getPath())
                                .build())
                        .build()
        );
    }
}
