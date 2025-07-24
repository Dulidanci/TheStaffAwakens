package net.dulidanci.staffmod.item;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.block.ModBlocks;
import net.dulidanci.staffmod.item.custom.DynamicStaffItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup STAFF_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(StaffMod.MOD_ID, "staff"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.staff"))
                   .icon(() -> new ItemStack(ModItems.REGULAR_STAFF)).entries((displayContext, entries) -> {
                       entries.add(ModItems.REGULAR_STAFF);
                       entries.add(ModItems.LOG_STAFF);

                       for (DynamicStaffItem dynamicStaffItem : ModItems.DYNAMIC_STAFFS) {
                           entries.add(dynamicStaffItem);
                       }

                       entries.add(ModBlocks.STAFF_UPGRADE_STATION);
                       entries.add(ModBlocks.BLUEPRINT_PLANKS);
                       entries.add(ModBlocks.FADING_LIGHT_BLOCK);

                   }).build());

    public static void registerItemGroups() {
        StaffMod.LOGGER.info("Registering ModItemGroups for " + StaffMod.MOD_ID);
    }
}
