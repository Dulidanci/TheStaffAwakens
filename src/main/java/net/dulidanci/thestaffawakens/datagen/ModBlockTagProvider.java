package net.dulidanci.thestaffawakens.datagen;

import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.dulidanci.thestaffawakens.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.CAN_TELEPORT_INTO)
                .add(Blocks.LAVA)
                .add(Blocks.WATER)
                .add(Blocks.AIR)
                .add(Blocks.CAVE_AIR)
                .add(Blocks.VOID_AIR)
                .add(Blocks.SNOW)
                .add(Blocks.SCAFFOLDING);

        getOrCreateTagBuilder(ModTags.REPLACEABLE_BY_BUILDING_PREVIEW)
                .add(Blocks.AIR)
                .add(Blocks.CAVE_AIR)
                .add(ModBlocks.BLUEPRINT_PLANKS);

        getOrCreateTagBuilder(ModTags.REPLACEABLE_BY_LIGHT)
                .add(Blocks.AIR)
                .add(Blocks.CAVE_AIR)
                .add(Blocks.WATER);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.STAFF_UPGRADE_STATION);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.STAFF_UPGRADE_STATION);
    }
}
