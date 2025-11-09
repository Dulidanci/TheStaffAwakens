package net.dulidanci.thestaffawakens.block.entity;

import net.dulidanci.thestaffawakens.block.ModBlockEntities;
import net.dulidanci.thestaffawakens.item.ModItems;
import net.dulidanci.thestaffawakens.item.cores.CoreTypes;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.dulidanci.thestaffawakens.item.staffs.StaffTypes;
import net.dulidanci.thestaffawakens.render.screen.StaffWorkbenchScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
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
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class StaffWorkbenchBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private static final int STAFF_SLOT = 0;
    private static final int CORE_SLOT = 1;
    private static final int BASE_SLOT = 2;
    private static final int UPGRADE_ITEM_SLOT = 3;
    private static final int UPGRADED_BASE_SLOT = 4;
    private boolean hasStaff;

    public StaffWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STAFF_WORKBENCH_BLOCK_ENTITY, pos, state);
        this.hasStaff = false;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public ItemStack getRenderStack() {
        return inventory.get(STAFF_SLOT);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        markDirty();
    }

    public void modifyInventoryBeforeBreaking() {
        if (!inventory.get(STAFF_SLOT).isEmpty()) inventory.get(STAFF_SLOT).decrement(1);
        if (!inventory.get(UPGRADED_BASE_SLOT).isEmpty()) inventory.get(UPGRADED_BASE_SLOT).decrement(1);
    }

    @Override
    public void markDirty() {
        updateStaff();
        if (world != null) world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    private void updateStaff() {
        ItemStack coreStack = this.inventory.get(CORE_SLOT);
        ItemStack baseStack = this.inventory.get(BASE_SLOT);
        ItemStack upgradeStack = this.inventory.get(UPGRADE_ITEM_SLOT);
        if (baseStack.isEmpty() || !(baseStack.getItem() instanceof StaffItem staffItem)) {
            this.inventory.set(STAFF_SLOT, ItemStack.EMPTY);
            this.inventory.set(UPGRADED_BASE_SLOT, ItemStack.EMPTY);
            return;
        }
        if (coreStack.isEmpty() || !(coreStack.getItem() instanceof BlockItem blockItem)) {
            this.inventory.set(STAFF_SLOT, baseStack);
            this.inventory.set(UPGRADED_BASE_SLOT, baseStack);
            return;
        }
        StaffTypes staffType = staffItem.getStaff().getType();
        CoreTypes coreType = CoreTypes.getCoreFromBlock(blockItem.getBlock());
        ItemStack staffStack = ModItems.isExistingStaff(staffType, coreType) ? new ItemStack(ModItems.createStaffFromComponents(staffType, coreType)) : baseStack;
        this.inventory.set(STAFF_SLOT, staffStack);
        this.inventory.set(UPGRADED_BASE_SLOT, staffStack);
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

        ItemStack displayedStaff = ItemStack.fromNbt(nbt.getCompound("DisplayedStaff"));
        inventory.set(STAFF_SLOT, displayedStaff);
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

    public void insertStaff(PlayerEntity player, Hand hand) {
        hasStaff = true;
        this.setStack(BASE_SLOT, new ItemStack(((StaffItem) player.getStackInHand(hand).getItem()).getStaff().getType().getItem()));
        this.setStack(CORE_SLOT, new ItemStack(((StaffItem) player.getStackInHand(hand).getItem()).getCore().getType().getBlock().asItem()));
        this.setStack(STAFF_SLOT, player.getStackInHand(hand));
        player.setStackInHand(hand, ItemStack.EMPTY);
        markDirty();
        sync();
    }

    public void removeStaff(PlayerEntity player, Hand hand) {
        hasStaff = false;
        player.setStackInHand(hand, this.getStack(STAFF_SLOT));
        this.setStack(BASE_SLOT, ItemStack.EMPTY);
        inventory.get(CORE_SLOT).decrement(1);
        this.setStack(STAFF_SLOT, ItemStack.EMPTY);
        markDirty();
        sync();
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return false;
    }

    public boolean hasStaff() {
        return hasStaff;
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
