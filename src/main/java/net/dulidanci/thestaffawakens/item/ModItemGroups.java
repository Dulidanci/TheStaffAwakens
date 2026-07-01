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

package net.dulidanci.thestaffawakens.item;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final ResourceKey<CreativeModeTab> THE_STAFF_AWAKENS_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(TheStaffAwakens.MOD_ID, "the_staff_awakens_tab_key")
    );

    public static final CreativeModeTab THE_STAFF_AWAKENS_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.PERFECTED_STAFF))
            .title(Component.translatable("item_group." + TheStaffAwakens.MOD_ID + ".the_staff_awakens_tab"))
            .displayItems((_, output) -> {
                output.accept(ModItems.PERFECTED_STAFF);
                output.accept(ModItems.LOG_STAFF);
                output.accept(ModItems.PERFECTED_STAFF_WITH_LAPIS_LAZULI_CORE);
                output.accept(ModItems.LOG_STAFF_WITH_LAPIS_LAZULI_CORE);

                output.accept(ModBlocks.BLUEPRINT_PLANKS.asItem());
                output.accept(ModBlocks.STAFF_WORKBENCH.asItem());
            })


            .build();

    public static void init() {
        TheStaffAwakens.LOGGER.info("Registering ModItemGroups!");

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, THE_STAFF_AWAKENS_TAB_KEY, THE_STAFF_AWAKENS_TAB);
    }
}
