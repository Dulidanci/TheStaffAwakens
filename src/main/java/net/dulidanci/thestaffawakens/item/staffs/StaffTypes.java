package net.dulidanci.thestaffawakens.item.staffs;

import net.dulidanci.thestaffawakens.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.*;
import java.util.function.Supplier;

public enum StaffTypes {
    PERFECTED(PerfectedStaff::new, () -> ModItems.PERFECTED_STAFF, 1, Map::of),
    LOG(LogStaff::new, () -> ModItems.LOG_STAFF, 0, () -> Map.of(Items.PURPUR_BLOCK, PERFECTED));

    private final Supplier<StaffTemplate> staffSupplier;
    private final Supplier<Item> itemSupplier;
    private final Supplier<Map<Item, StaffTypes>> upgrades;
    private final int upgradeCost;

    StaffTypes(Supplier<StaffTemplate> supplier, Supplier<Item> item, int cost, Supplier<Map<Item, StaffTypes>> upgrades) {
        this.staffSupplier = supplier;
        this.itemSupplier = item;
        this.upgrades = upgrades;
        this.upgradeCost = cost;
    }

    public StaffTemplate createStaff() {
        return staffSupplier.get();
    }

    public Item getItem() {
        return itemSupplier.get();
    }

    public Map<Item, StaffTypes> getUpgrades() {
        return upgrades.get();
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }
}
