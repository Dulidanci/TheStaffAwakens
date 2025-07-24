package net.dulidanci.staffmod.datagen;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.block.ModBlocks;
import net.dulidanci.staffmod.item.ModItems;
import net.dulidanci.staffmod.item.custom.DynamicStaffItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
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
            registerBuiltinModel(itemModelGenerator, dynamicStaffItem);
        }
    }

    private void registerBuiltinModel(ItemModelGenerator itemModelGenerator, Item item) {
        itemModelGenerator.writer.accept(
                new Identifier(StaffMod.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath()),
                () -> JsonBuilder.create()
                        .add("parent", "builtin/entity")
                        .build()
        );
    }
}
