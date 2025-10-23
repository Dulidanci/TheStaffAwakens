package net.dulidanci.thestaffawakens.item.custom;

import net.dulidanci.thestaffawakens.item.cores.CoreTemplate;
import net.dulidanci.thestaffawakens.item.staffs.StaffTemplate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StaffItem extends Item {
    private final CoreTemplate core;
    private final StaffTemplate staff;

    public StaffItem(Settings settings, CoreTemplate core, StaffTemplate staff) {
        super(settings);
        this.core = core;
        this.staff = staff;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        core.activateCore(user);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public CoreTemplate getCore() {
        return core;
    }

    public StaffTemplate getStaff() {
        return staff;
    }
}
