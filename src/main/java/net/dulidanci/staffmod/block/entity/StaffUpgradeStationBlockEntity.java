package net.dulidanci.staffmod.block.entity;

import net.dulidanci.staffmod.block.ModBlockEntities;
import net.dulidanci.staffmod.item.ModItems;
import net.dulidanci.staffmod.item.custom.StaffItem;
import net.dulidanci.staffmod.screen.StaffUpgradeStationEmptyScreenHandler;
import net.dulidanci.staffmod.screen.StaffUpgradeStationStaffScreenHandler;
import net.dulidanci.staffmod.util.StaffPairingTable;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class StaffUpgradeStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private static final int STAFF_SLOT = 0;
    private static final int CORE_SLOT = 1;
    private boolean hasStaff;

    public StaffUpgradeStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STAFF_UPGRADE_STATION_BLOCK_ENTITY, pos, state);
        this.hasStaff = false;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        markDirty();
    }

    public ItemStack getRenderStack() {
        return inventory.get(STAFF_SLOT);
    }

    public void modifyInventoryBeforeBreaking() {
        if (inventory.get(STAFF_SLOT).getItem() instanceof StaffItem && inventory.get(STAFF_SLOT).getItem() != ModItems.PERFECTED_STAFF) {
            inventory.get(CORE_SLOT).decrement(1);
        }
    }

    @Override
    public void markDirty() {
        if (!this.getStack(STAFF_SLOT).getItem().equals(StaffPairingTable.getStaffCorrespondingToBlock(this.getStack(CORE_SLOT).getItem())) && hasStaff) {
            this.setStack(STAFF_SLOT, new ItemStack(StaffPairingTable.getStaffCorrespondingToBlock(this.getStack(CORE_SLOT).getItem())));
        }
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    public void sync() {
        if (world != null && !world.isClient()) {
            ((ServerWorld) world).getChunkManager().markForUpdate(pos);
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.staffmod.staff_upgrade_station");
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

        ItemStack displayedStaff = ItemStack.fromNbt(nbt.getCompound("DisplayedStaff"));
        inventory.set(STAFF_SLOT, displayedStaff);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        if (hasStaff) {
            return new StaffUpgradeStationStaffScreenHandler(syncId, playerInventory, this);
        }
        return new StaffUpgradeStationEmptyScreenHandler(syncId, playerInventory, this);
    }

//    public void tick(World world, BlockPos pos, BlockState state) {
//        if (world.isClient()) {
//            return;
//        }
//    }

    public boolean attemptInsertingStaff(PlayerEntity player, Hand hand) {
        if (this.getStack(STAFF_SLOT).isEmpty()) {

            hasStaff = true;
            this.setStack(STAFF_SLOT, player.getStackInHand(hand));
            this.setStack(CORE_SLOT, StaffPairingTable.getCoreCorrespondingToStaff(player.getStackInHand(hand).getItem()));
            player.setStackInHand(hand, ItemStack.EMPTY);
            markDirty();
            sync();
            return true;
        }
        return false;
    }

    public boolean attemptRemovingStaff(PlayerEntity player, Hand hand) {
        if (!this.getStack(STAFF_SLOT).isEmpty()) {

            hasStaff = false;
            player.setStackInHand(hand, this.getStack(STAFF_SLOT));
            this.setStack(CORE_SLOT, ItemStack.EMPTY);
            this.setStack(STAFF_SLOT, ItemStack.EMPTY);
            markDirty();
            sync();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
