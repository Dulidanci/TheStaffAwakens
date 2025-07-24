package net.dulidanci.staffmod.item.staffs;

import net.dulidanci.staffmod.item.ModItems;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public enum StaffTypes {
    REGULAR(RegularStaff::new, () -> ModItems.REGULAR_STAFF),
    LOG(LogStaff::new, () -> ModItems.LOG_STAFF);

    private final Supplier<StaffTemplate> staffSupplier;
    private final Supplier<Item> itemSupplier;

    StaffTypes(Supplier<StaffTemplate> supplier, Supplier<Item> item) {
        this.staffSupplier = supplier;
        this.itemSupplier = item;
    }

    public StaffTemplate createStaff() {
        return staffSupplier.get();
    }

    public Item getItem() {
        return itemSupplier.get();
    }
}
