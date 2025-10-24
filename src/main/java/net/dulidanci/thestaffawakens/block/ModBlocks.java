package net.dulidanci.thestaffawakens.block;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.block.custom.BlueprintPlanksBlock;
import net.dulidanci.thestaffawakens.block.custom.FadingLightBlock;
import net.dulidanci.thestaffawakens.block.custom.StaffUpgradeStationBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block BLUEPRINT_PLANKS = registerBlock("blueprint_planks",
            new BlueprintPlanksBlock(FabricBlockSettings.copyOf(Blocks.AIR).nonOpaque()));

    public static final Block FADING_LIGHT_BLOCK = registerBlock("fading_light_block",
            new FadingLightBlock(FabricBlockSettings.copyOf(Blocks.LIGHT)));

    public static final Block STAFF_UPGRADE_STATION = registerBlock("staff_upgrade_station",
            new StaffUpgradeStationBlock(FabricBlockSettings.copyOf(Blocks.STONECUTTER).nonOpaque().requiresTool()));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(TheStaffAwakens.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(TheStaffAwakens.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        TheStaffAwakens.LOGGER.info("Registering ModBlocks for " + TheStaffAwakens.MOD_ID);
    }
}
