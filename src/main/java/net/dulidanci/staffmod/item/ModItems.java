package net.dulidanci.staffmod.item;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.cores.CoreTypes;
import net.dulidanci.staffmod.item.custom.*;
import net.dulidanci.staffmod.item.staffs.StaffTypes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModItems {
    public static final ArrayList<Item> STAFFS = new ArrayList<>();
    public static final ArrayList<EmptyStaffItem> EMPTY_STAFFS = new ArrayList<>();
    public static final ArrayList<DynamicStaffItem> DYNAMIC_STAFFS = new ArrayList<>();
    public static final Map<StaffTypes, ArrayList<CoreTypes>> STAFF_COMPONENTS_COMPATIBILITY = new HashMap<>();

    public static final Item REGULAR_STAFF = registerItem("regular_staff",
            new EmptyStaffItem(new FabricItemSettings().maxCount(1)));

    public static final Item REGULAR_STAFF_WITH_NETHERRACK_CORE = registerItem("regular_staff_with_netherrack_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.NETHERRACK.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_END_STONE_CORE = registerItem("regular_staff_with_end_stone_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.END_STONE.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_TNT_CORE = registerItem("regular_staff_with_tnt_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.TNT.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_ANVIL_CORE = registerItem("regular_staff_with_anvil_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.ANVIL.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_MAGMA_BLOCK_CORE = registerItem("regular_staff_with_magma_block_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.MAGMA_BLOCK.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_ACACIA_PLANKS_CORE = registerItem("regular_staff_with_acacia_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.ACACIA_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_BAMBOO_PLANKS_CORE = registerItem("regular_staff_with_bamboo_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.BAMBOO_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_BIRCH_PLANKS_CORE = registerItem("regular_staff_with_birch_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.BIRCH_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_CHERRY_PLANKS_CORE = registerItem("regular_staff_with_cherry_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.CHERRY_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_CRIMSON_PLANKS_CORE = registerItem("regular_staff_with_crimson_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.CRIMSON_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_DARK_OAK_PLANKS_CORE = registerItem("regular_staff_with_dark_oak_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.DARK_OAK_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_JUNGLE_PLANKS_CORE = registerItem("regular_staff_with_jungle_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.JUNGLE_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_MANGROVE_PLANKS_CORE = registerItem("regular_staff_with_mangrove_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.MANGROVE_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_OAK_PLANKS_CORE = registerItem("regular_staff_with_oak_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.OAK_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_SPRUCE_PLANKS_CORE = registerItem("regular_staff_with_spruce_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.SPRUCE_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_WARPED_PLANKS_CORE = registerItem("regular_staff_with_warped_planks_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.WARPED_PLANKS.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_GLOWSTONE_CORE = registerItem("regular_staff_with_glowstone_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.GLOWSTONE.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_LAPIS_LAZULI_CORE = registerItem("regular_staff_with_lapis_lazuli_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.LAPIS_LAZULI.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_BONE_BLOCK_CORE = registerItem("regular_staff_with_bone_block_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.BONE_BLOCK.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_BEEHIVE_CORE = registerItem("regular_staff_with_beehive_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.BEEHIVE.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_BELL_CORE = registerItem("regular_staff_with_bell_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.BELL.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item REGULAR_STAFF_WITH_TARGET_CORE = registerItem("regular_staff_with_target_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.TARGET.createCore(), StaffTypes.REGULAR.createStaff()));

    public static final Item LOG_STAFF = registerItem("log_staff",
            new EmptyStaffItem(new FabricItemSettings().maxCount(1)));

    public static final Item LOG_STAFF_WITH_TNT_CORE = registerItem("log_staff_with_tnt_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.TNT.createCore(), StaffTypes.LOG.createStaff()));

    public static final Item LOG_STAFF_WITH_MAGMA_BLOCK_CORE = registerItem("log_staff_with_magma_block_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.MAGMA_BLOCK.createCore(), StaffTypes.LOG.createStaff()));

    public static final Item LOG_STAFF_WITH_BONE_BLOCK_CORE = registerItem("log_staff_with_bone_block_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.BONE_BLOCK.createCore(), StaffTypes.LOG.createStaff()));

    public static final Item LOG_STAFF_WITH_BEEHIVE_CORE = registerItem("log_staff_with_beehive_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.BEEHIVE.createCore(), StaffTypes.LOG.createStaff()));

    public static final Item LOG_STAFF_WITH_NETHERRACK_CORE = registerItem("log_staff_with_netherrack_core",
            new DynamicStaffItem(new FabricItemSettings().maxCount(1), CoreTypes.NETHERRACK.createCore(), StaffTypes.LOG.createStaff()));

    private static Item registerItem(String name, Item item) {
        if (item instanceof EmptyStaffItem emptyStaffItem) {
            STAFFS.add(emptyStaffItem);
            EMPTY_STAFFS.add(emptyStaffItem);
        } else if (item instanceof DynamicStaffItem dynamicStaffItem) {
            STAFFS.add(dynamicStaffItem);
            DYNAMIC_STAFFS.add(dynamicStaffItem);
            STAFF_COMPONENTS_COMPATIBILITY.putIfAbsent(dynamicStaffItem.getStaff().getType(), new ArrayList<>());
            STAFF_COMPONENTS_COMPATIBILITY.get(dynamicStaffItem.getStaff().getType()).add(dynamicStaffItem.getCore().getType());
        }
        return Registry.register(Registries.ITEM, new Identifier(StaffMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        StaffMod.LOGGER.info("Registering ModItems for " + StaffMod.MOD_ID);
    }
}
