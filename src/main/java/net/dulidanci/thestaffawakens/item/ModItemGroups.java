package net.dulidanci.thestaffawakens.item;

import net.dulidanci.thestaffawakens.TheStaffAwakens;
import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup STAFF_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(TheStaffAwakens.MOD_ID, "staffs"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.staffs"))
                    .icon(() -> new ItemStack(ModItems.PERFECTED_STAFF))
                    .entries((displayContext, entries) -> {
                       for (StaffItem staffItem : ModItems.STAFFS) {
                           entries.add(staffItem);
                       }

//                       entries.add(ModBlocks.STAFF_UPGRADE_STATION);
                       entries.add(ModBlocks.BLUEPRINT_PLANKS);
                       entries.add(ModBlocks.FADING_LIGHT_BLOCK);

                    }).build());

    public static void registerItemGroups() {
        TheStaffAwakens.LOGGER.info("Registering ModItemGroups for " + TheStaffAwakens.MOD_ID);
    }
}
