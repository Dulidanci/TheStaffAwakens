package net.dulidanci.thestaffawakens.render.screen;

import net.dulidanci.thestaffawakens.block.entity.StaffWorkbenchBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class StaffWorkbenchScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final StaffWorkbenchBlockEntity blockEntity;

    public StaffWorkbenchScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }

    public StaffWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory,
                                       BlockEntity blockEntity) {
        super(ModScreenHandlers.STAFF_WORKBENCH_SCREEN_HANDLER, syncId);
        checkSize((Inventory) blockEntity, 5);
        this.inventory = (Inventory) blockEntity;
        playerInventory.onOpen(playerInventory.player);
        this.blockEntity = (StaffWorkbenchBlockEntity) blockEntity;

        this.addSlot(new Slot(inventory, 0, 0, 29) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 1, 9, 52));
        this.addSlot(new Slot(inventory, 2, 40, 29) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 3, 35, 52));
        this.addSlot(new Slot(inventory, 4, 84, 52) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return false;
            }
        });
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

//    @Override
//    public void onContentChanged(Inventory inventory) {
//        if (inventory instanceof StaffWorkbenchBlockEntity staffWorkbenchBlockEntity) {
//            staffWorkbenchBlockEntity.updateStaff();
//            staffWorkbenchBlockEntity.markDirty();
//            staffWorkbenchBlockEntity.sync();
//        }
//        super.onContentChanged(inventory);
//    }
}
