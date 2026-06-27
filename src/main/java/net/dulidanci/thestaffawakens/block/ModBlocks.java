//   Copyright 2026 Dulidanci
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package net.dulidanci.thestaffawakens.block;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.block.custom.StaffWorkbenchBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {
    public static final Block BLUEPRINT_PLANKS = registerBlock("blueprint_planks", Block::new,
            BlockBehaviour.Properties.of());
    public static final Block STAFF_WORKBENCH = registerBlock("staff_workbench", StaffWorkbenchBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONECUTTER).noOcclusion().dynamicShape());

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> blockFactory,
                                       BlockBehaviour.Properties properties) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TheStaffAwakens.MOD_ID, name));

        Block block = blockFactory.apply(properties.setId(blockKey));

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TheStaffAwakens.MOD_ID, name));

        BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());

        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    public static void init() {
        TheStaffAwakens.LOGGER.info("Registering ModBlocks!");
    }
}
