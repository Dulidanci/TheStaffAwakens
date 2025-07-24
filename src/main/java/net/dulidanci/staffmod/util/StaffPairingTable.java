package net.dulidanci.staffmod.util;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.*;

public class StaffPairingTable {
    private static final Map<Item, Item> table = new HashMap<>(Map.ofEntries(
            Map.entry(Items.AIR, ModItems.REGULAR_STAFF),
            Map.entry(Items.NETHERRACK, ModItems.REGULAR_STAFF_WITH_NETHERRACK_CORE),
            Map.entry(Items.END_STONE, ModItems.REGULAR_STAFF_WITH_END_STONE_CORE),
            Map.entry(Items.TNT, ModItems.REGULAR_STAFF_WITH_TNT_CORE),
            Map.entry(Items.ANVIL, ModItems.REGULAR_STAFF_WITH_ANVIL_CORE),
            Map.entry(Items.MAGMA_BLOCK, ModItems.REGULAR_STAFF_WITH_MAGMA_BLOCK_CORE),

            Map.entry(Items.OAK_PLANKS, ModItems.REGULAR_STAFF_WITH_OAK_PLANKS_CORE),
            Map.entry(Items.SPRUCE_PLANKS, ModItems.REGULAR_STAFF_WITH_SPRUCE_PLANKS_CORE),
            Map.entry(Items.BIRCH_PLANKS, ModItems.REGULAR_STAFF_WITH_BIRCH_PLANKS_CORE),
            Map.entry(Items.JUNGLE_PLANKS, ModItems.REGULAR_STAFF_WITH_JUNGLE_PLANKS_CORE),
            Map.entry(Items.ACACIA_PLANKS, ModItems.REGULAR_STAFF_WITH_ACACIA_PLANKS_CORE),
            Map.entry(Items.DARK_OAK_PLANKS, ModItems.REGULAR_STAFF_WITH_DARK_OAK_PLANKS_CORE),
            Map.entry(Items.MANGROVE_PLANKS, ModItems.REGULAR_STAFF_WITH_MANGROVE_PLANKS_CORE),
            Map.entry(Items.CHERRY_PLANKS, ModItems.REGULAR_STAFF_WITH_CHERRY_PLANKS_CORE),
            Map.entry(Items.BAMBOO_PLANKS, ModItems.REGULAR_STAFF_WITH_BAMBOO_PLANKS_CORE),
            Map.entry(Items.CRIMSON_PLANKS, ModItems.REGULAR_STAFF_WITH_CRIMSON_PLANKS_CORE),
            Map.entry(Items.WARPED_PLANKS, ModItems.REGULAR_STAFF_WITH_WARPED_PLANKS_CORE),

            Map.entry(Items.GLOWSTONE, ModItems.REGULAR_STAFF_WITH_GLOWSTONE_CORE),
            Map.entry(Items.BONE_BLOCK, ModItems.REGULAR_STAFF_WITH_BONE_BLOCK_CORE),
            Map.entry(Items.BEEHIVE, ModItems.REGULAR_STAFF_WITH_BEEHIVE_CORE),
            Map.entry(Items.LAPIS_BLOCK, ModItems.REGULAR_STAFF_WITH_LAPIS_LAZULI_CORE),
            Map.entry(Items.BELL, ModItems.REGULAR_STAFF_WITH_BELL_CORE),
            Map.entry(Items.TARGET, ModItems.REGULAR_STAFF_WITH_TARGET_CORE)
    ));

    public static void register() {
        StaffMod.LOGGER.info("Registering StaffPairingTable for " + StaffMod.MOD_ID);
    }

    public static Item getStaffCorrespondingToBlock(Item core) {
        for (Map.Entry<Item, Item> entry : table.entrySet()) {
            if (entry.getKey() == core) {
                return entry.getValue();
            }
        }
        return ModItems.REGULAR_STAFF;
    }

    public static ItemStack getCoreCorrespondingToStaff(Item staff) {
        for (Map.Entry<Item, Item> entry : table.entrySet()) {
            if (entry.getValue() == staff) {
                return new ItemStack(entry.getKey());
            }
        }
        return ItemStack.EMPTY;
    }
}
