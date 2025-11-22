package net.dulidanci.thestaffawakens.block.entity;

import net.dulidanci.thestaffawakens.block.ModBlockEntities;
import net.dulidanci.thestaffawakens.item.ModItems;
import net.dulidanci.thestaffawakens.item.cores.CoreTypes;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.dulidanci.thestaffawakens.item.staffs.StaffTypes;
import net.dulidanci.thestaffawakens.render.screen.StaffWorkbenchScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class StaffWorkbenchBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private static final int STAFF_SLOT = 0;
    private static final int CORE_SLOT = 1;
    private static final int BASE_SLOT = 2;
    private static final int UPGRADE_ITEM_SLOT = 3;
    private static final int UPGRADED_BASE_SLOT = 4;
    private boolean hasStaff;
    private ItemStack renderStack = ItemStack.EMPTY;

    public StaffWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STAFF_WORKBENCH_BLOCK_ENTITY, pos, state);
        this.hasStaff = false;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public ItemStack getRenderStack() {
        return renderStack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        updateStaff();
    }

    public void modifyInventoryBeforeBreaking() {
        if (!inventory.get(STAFF_SLOT).isEmpty()) inventory.get(STAFF_SLOT).decrement(1);
        if (!inventory.get(UPGRADED_BASE_SLOT).isEmpty()) inventory.get(UPGRADED_BASE_SLOT).decrement(1);
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    public void sync() {
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public void updateStaff() {
        ItemStack coreStack = this.inventory.get(CORE_SLOT);
        ItemStack baseStack = this.inventory.get(BASE_SLOT);
        ItemStack upgradeStack = this.inventory.get(UPGRADE_ITEM_SLOT);

        if (baseStack.isEmpty() || !(baseStack.getItem() instanceof StaffItem staffItem)) {
            this.inventory.set(STAFF_SLOT, ItemStack.EMPTY);
            this.inventory.set(UPGRADED_BASE_SLOT, ItemStack.EMPTY);
            return;
        }

        StaffTypes staffType = staffItem.getStaff().getType();
        Map<Item, StaffTypes> upgrades = staffType.getUpgrades();
        StaffTypes upgradedStaff = upgrades.get(upgradeStack.getItem());

        if (coreStack.isEmpty() || !(coreStack.getItem() instanceof BlockItem blockItem)) {
            this.inventory.set(STAFF_SLOT, baseStack);
            if (upgradedStaff != null) {
                this.inventory.set(UPGRADED_BASE_SLOT, new ItemStack(upgradedStaff.getItem()));
            } else {
                this.inventory.set(UPGRADED_BASE_SLOT, ItemStack.EMPTY);
            }
        } else {
            CoreTypes coreType = CoreTypes.getCoreFromBlock(blockItem.getBlock());
            ItemStack staffStack = ModItems.isExistingStaff(staffType, coreType) ? new ItemStack(ModItems.createStaffFromComponents(staffType, coreType)) : baseStack;
            this.inventory.set(STAFF_SLOT, staffStack);
            if (upgradedStaff != null) {
                ItemStack upgradedStaffStack = ModItems.isExistingStaff(upgradedStaff, coreType) ? new ItemStack(ModItems.createStaffFromComponents(upgradedStaff, coreType)) : new ItemStack(upgradedStaff.getItem());
                this.inventory.set(UPGRADED_BASE_SLOT, upgradedStaffStack);
            } else {
                this.inventory.set(UPGRADED_BASE_SLOT, ItemStack.EMPTY);
            }
        }
        markDirty();
        sync();
    }

    public boolean attemptInsertingStaff(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).getItem() instanceof StaffItem && !this.hasStaff()) {
            insertStaff(player, hand);
            return true;
        }
        return false;
    }

    private void insertStaff(PlayerEntity player, Hand hand) {
        hasStaff = true;
        this.inventory.set(BASE_SLOT, new ItemStack(((StaffItem) player.getStackInHand(hand).getItem()).getStaff().getType().getItem()));

        if (((StaffItem) player.getStackInHand(hand).getItem()).getCore().getType() != CoreTypes.AIR) {
            if (inventory.get(CORE_SLOT).getItem() != ((StaffItem) player.getStackInHand(hand).getItem()).getCore().getType().getBlock().asItem()) {
                if (world != null) {
                    ItemScatterer.spawn(world, pos.add(0, 1, 0), DefaultedList.ofSize(1, inventory.get(CORE_SLOT)));

                    this.inventory.set(CORE_SLOT, new ItemStack(((StaffItem) player.getStackInHand(hand).getItem()).getCore().getType().getBlock().asItem()));
                }
            } else {
                if (inventory.get(CORE_SLOT).getCount() == 64) {
                    if (world != null) {
                        ItemScatterer.spawn(world, pos.add(0, 1, 0), DefaultedList.ofSize(1, new ItemStack(inventory.get(CORE_SLOT).getItem(), 1)));
                    }
                } else {
                    inventory.get(CORE_SLOT).increment(1);
                }
            }
        }
        this.inventory.set(STAFF_SLOT, player.getStackInHand(hand));
        player.setStackInHand(hand, ItemStack.EMPTY);

        updateStaff();
        markDirty();
        sync();
    }

    public boolean attemptRemovingStaff(PlayerEntity player, Hand hand) {
        if (player.isSneaking() && player.getStackInHand(hand).isEmpty() && this.hasStaff()) {
            removeStaff(player, hand);
            return true;
        }
        return false;
    }

    private void removeStaff(PlayerEntity player, Hand hand) {
        hasStaff = false;
        player.setStackInHand(hand, this.getStack(STAFF_SLOT));
        if (inventory.get(STAFF_SLOT).getItem() instanceof StaffItem staffItem && !staffItem.getCore().getType().equals(CoreTypes.AIR)) {
            inventory.get(CORE_SLOT).decrement(1);
            if (inventory.get(CORE_SLOT).isEmpty()) {
                inventory.set(CORE_SLOT, ItemStack.EMPTY);
            }
        }
        this.inventory.set(BASE_SLOT, ItemStack.EMPTY);
        this.inventory.set(STAFF_SLOT, ItemStack.EMPTY);

        updateStaff();
        markDirty();
        sync();
    }

    public boolean canUpgradeStaff() {
        return inventory.get(UPGRADED_BASE_SLOT) != ItemStack.EMPTY;
    }

    public void attemptUpgradingStaff() {
        if (canUpgradeStaff()) {
            upgradeStaff();
        }
    }

    private void upgradeStaff() {
        inventory.set(BASE_SLOT, ((StaffItem) (inventory.get(UPGRADED_BASE_SLOT).getItem())).getStaff().getType().getItem().getDefaultStack());
        inventory.get(UPGRADE_ITEM_SLOT).decrement(1);
        if (inventory.get(UPGRADE_ITEM_SLOT).isEmpty()) {
            inventory.set(UPGRADE_ITEM_SLOT, ItemStack.EMPTY);
        }

        updateStaff();
        markDirty();
        sync();
    }

    public int getUpgradeCost() {
        if (!inventory.get(UPGRADED_BASE_SLOT).isEmpty() && inventory.get(UPGRADED_BASE_SLOT).getItem() instanceof StaffItem staffItem) {
            return staffItem.getStaff().getType().getUpgradeCost();
        }
        return 0;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.thestaffawakens.staff_workbench");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("hasStaff", hasStaff);
        Inventories.writeNbt(nbt, inventory);

        NbtCompound itemNbt = new NbtCompound();
        inventory.get(STAFF_SLOT).writeNbt(itemNbt);
        nbt.put("DisplayedStaff", itemNbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        hasStaff = nbt.getBoolean("hasStaff");
        Inventories.readNbt(nbt, inventory);

        renderStack = ItemStack.fromNbt(nbt.getCompound("DisplayedStaff"));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StaffWorkbenchScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world != null && this.world.getBlockEntity(pos) != this) {
            return false;
        }
        return player.squaredDistanceTo(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return slot == CORE_SLOT;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return slot == CORE_SLOT;
    }

    public boolean hasStaff() {
        return hasStaff;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt);
        return BlockEntityUpdateS2CPacket.create(this, blockEntity -> nbt);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt);
        return nbt;
    }
}
