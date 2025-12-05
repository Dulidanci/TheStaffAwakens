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
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
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

public class StaffWorkbenchBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, InventoryChangedListener {
    private final SimpleInventory inventory = new SimpleInventory(5);
    private static final int STAFF_SLOT = 0;
    private static final int CORE_SLOT = 1;
    private static final int BASE_SLOT = 2;
    private static final int UPGRADE_ITEM_SLOT = 3;
    private static final int UPGRADED_BASE_SLOT = 4;
    private boolean hasStaff;
    private boolean canUpgrade;
    private ItemStack renderStack = ItemStack.EMPTY;
    private int mana;
    private int recharge;
    private int level;
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> StaffWorkbenchBlockEntity.this.mana;
                case 1 -> StaffWorkbenchBlockEntity.this.recharge;
                case 2 -> StaffWorkbenchBlockEntity.this.level;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    StaffWorkbenchBlockEntity.this.mana = value;
                    break;
                case 1:
                    StaffWorkbenchBlockEntity.this.recharge = value;
                    break;
                case 2:
                    StaffWorkbenchBlockEntity.this.level = value;
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };

    public StaffWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STAFF_WORKBENCH_BLOCK_ENTITY, pos, state);
        this.hasStaff = false;
        this.canUpgrade = false;
        inventory.addListener(this);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory.heldStacks;
    }

    public ItemStack getRenderStack() {
        return renderStack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.setStack(slot, stack);
    }

    public void modifyInventoryBeforeBreaking() {
        if (!inventory.getStack(STAFF_SLOT).isEmpty()) inventory.getStack(STAFF_SLOT).decrement(1);
        if (!inventory.getStack(UPGRADED_BASE_SLOT).isEmpty()) inventory.getStack(UPGRADED_BASE_SLOT).decrement(1);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        updateStaff();
        markDirty();
    }

    public void updateStaff() {
        ItemStack coreStack = this.inventory.getStack(CORE_SLOT);
        ItemStack baseStack = this.inventory.getStack(BASE_SLOT);
        ItemStack upgradeStack = this.inventory.getStack(UPGRADE_ITEM_SLOT);

        if (baseStack.isEmpty() || !(baseStack.getItem() instanceof StaffItem staffItem)) {
            if (!this.inventory.getStack(STAFF_SLOT).equals(ItemStack.EMPTY))
                    this.inventory.setStack(STAFF_SLOT, ItemStack.EMPTY);
            if (!this.inventory.getStack(UPGRADED_BASE_SLOT).equals(ItemStack.EMPTY))
                    this.inventory.setStack(UPGRADED_BASE_SLOT, ItemStack.EMPTY);
            canUpgrade = false;
            mana = 0;
            level = 0;
        } else {

            StaffTypes staffType = staffItem.getStaff().getType();
            Map<Item, StaffTypes> upgrades = staffType.getUpgrades();
            StaffTypes upgradedStaff = upgrades.get(upgradeStack.getItem());

            if (coreStack.isEmpty() || !(coreStack.getItem() instanceof BlockItem blockItem)) {
                if (!this.inventory.getStack(STAFF_SLOT).isOf(baseStack.getItem()))
                        this.inventory.setStack(STAFF_SLOT, baseStack);
                mana = 0;
                if (upgradedStaff != null) {
                    if (!this.inventory.getStack(UPGRADED_BASE_SLOT).isOf(upgradedStaff.getItem()))
                            this.inventory.setStack(UPGRADED_BASE_SLOT, new ItemStack(upgradedStaff.getItem()));
                    canUpgrade = true;
                    level = upgradedStaff.getUpgradeCost();
                } else {
                    if (!this.inventory.getStack(UPGRADED_BASE_SLOT).isOf(ItemStack.EMPTY.getItem()))
                            this.inventory.setStack(UPGRADED_BASE_SLOT, ItemStack.EMPTY);
                    canUpgrade = false;
                    level = 0;
                }
            } else {
                CoreTypes coreType = CoreTypes.getCoreFromBlock(blockItem.getBlock());
                ItemStack staffStack = ModItems.isExistingStaff(staffType, coreType) ? new ItemStack(ModItems.createStaffFromComponents(staffType, coreType)) : baseStack;
                if (!this.inventory.getStack(STAFF_SLOT).isOf(staffStack.getItem()))
                        this.inventory.setStack(STAFF_SLOT, staffStack);
                mana = coreType.getManaCost();
                if (upgradedStaff != null) {
                    ItemStack upgradedStaffStack = ModItems.isExistingStaff(upgradedStaff, coreType) ? new ItemStack(ModItems.createStaffFromComponents(upgradedStaff, coreType)) : new ItemStack(upgradedStaff.getItem());
                    if (!this.inventory.getStack(UPGRADED_BASE_SLOT).isOf(upgradedStaffStack.getItem()))
                            this.inventory.setStack(UPGRADED_BASE_SLOT, upgradedStaffStack);
                    canUpgrade = true;
                    level = upgradedStaff.getUpgradeCost();
                } else {
                    if (!this.inventory.getStack(UPGRADED_BASE_SLOT).isOf(ItemStack.EMPTY.getItem()))
                            this.inventory.setStack(UPGRADED_BASE_SLOT, ItemStack.EMPTY);
                    canUpgrade = false;
                    level = 0;
                }
            }
        }
        markDirty();
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
        this.inventory.setStack(BASE_SLOT, new ItemStack(((StaffItem) player.getStackInHand(hand).getItem()).getStaff().getType().getItem()));

        if (((StaffItem) player.getStackInHand(hand).getItem()).getCore().getType() != CoreTypes.AIR) {
            if (inventory.getStack(CORE_SLOT).getItem() != ((StaffItem) player.getStackInHand(hand).getItem()).getCore().getType().getBlock().asItem()) {
                if (world != null) {
                    ItemScatterer.spawn(world, pos.add(0, 1, 0), DefaultedList.ofSize(1, inventory.getStack(CORE_SLOT)));

                    this.inventory.setStack(CORE_SLOT, new ItemStack(((StaffItem) player.getStackInHand(hand).getItem()).getCore().getType().getBlock().asItem()));
                }
            } else {
                if (inventory.getStack(CORE_SLOT).getCount() == 64) {
                    if (world != null) {
                        ItemScatterer.spawn(world, pos.add(0, 1, 0), DefaultedList.ofSize(1, new ItemStack(inventory.getStack(CORE_SLOT).getItem(), 1)));
                    }
                } else {
                    inventory.getStack(CORE_SLOT).increment(1);
                }
            }
        }
        this.inventory.setStack(STAFF_SLOT, player.getStackInHand(hand));
        player.setStackInHand(hand, ItemStack.EMPTY);
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
        if (inventory.getStack(STAFF_SLOT).getItem() instanceof StaffItem staffItem && !staffItem.getCore().getType().equals(CoreTypes.AIR)) {
            inventory.getStack(CORE_SLOT).decrement(1);
            if (inventory.getStack(CORE_SLOT).isEmpty()) {
                inventory.setStack(CORE_SLOT, ItemStack.EMPTY);
            }
        }
        this.inventory.setStack(BASE_SLOT, ItemStack.EMPTY);
        this.inventory.setStack(STAFF_SLOT, ItemStack.EMPTY);
    }

    public boolean canUpgradeStaff() {
        return inventory.getStack(UPGRADED_BASE_SLOT) != ItemStack.EMPTY;
    }

    public void attemptUpgradingStaff() {
        if (canUpgradeStaff()) {
            upgradeStaff();
        }
    }

    private void upgradeStaff() {
        inventory.setStack(BASE_SLOT, ((StaffItem) (inventory.getStack(UPGRADED_BASE_SLOT).getItem())).getStaff().getType().getItem().getDefaultStack());
        inventory.getStack(UPGRADE_ITEM_SLOT).decrement(1);
        if (inventory.getStack(UPGRADE_ITEM_SLOT).isEmpty()) {
            inventory.setStack(UPGRADE_ITEM_SLOT, ItemStack.EMPTY);
        }
    }

    public int getUpgradeCost() {
        if (!inventory.getStack(UPGRADED_BASE_SLOT).isEmpty() && inventory.getStack(UPGRADED_BASE_SLOT).getItem() instanceof StaffItem staffItem) {
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
//        nbt.putBoolean("canUpgrade", canUpgrade);
        Inventories.writeNbt(nbt, inventory.getHeldStacks());

        NbtCompound itemNbt = new NbtCompound();
        inventory.getStack(STAFF_SLOT).writeNbt(itemNbt);
        nbt.put("DisplayedStaff", itemNbt);

        nbt.putInt("Mana", this.mana);
        nbt.putInt("Recharge", this.recharge);
        nbt.putInt("Level", this.level);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.hasStaff = nbt.getBoolean("hasStaff");
//        this.canUpgrade = nbt.getBoolean("canUpgrade");
        Inventories.readNbt(nbt, inventory.getHeldStacks());

        renderStack = ItemStack.fromNbt(nbt.getCompound("DisplayedStaff"));

        this.mana = nbt.getInt("Mana");
        this.recharge = nbt.getInt("Recharge");
        this.level = nbt.getInt("Level");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StaffWorkbenchScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
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

    public boolean canUpgrade() {
        return canUpgrade;
    }

    public boolean shouldRenderStats() {
        return !inventory.getStack(STAFF_SLOT).isEmpty();
    }

    public boolean shouldRenderUpgrades() {
        return !inventory.getStack(STAFF_SLOT).isEmpty() && !inventory.getStack(UPGRADE_ITEM_SLOT).isEmpty();
    }

    public boolean hasCore() {
        return !inventory.getStack(CORE_SLOT).isEmpty();
    }

    public boolean hasCorrectCore() {
        return inventory.getStack(STAFF_SLOT).getItem() instanceof StaffItem staffItem && !staffItem.getCore().getType().equals(CoreTypes.AIR);
    }

    public boolean maxLevel() {
        return inventory.getStack(STAFF_SLOT).getItem() instanceof StaffItem staffItem && staffItem.getStaff().getType().getUpgrades().isEmpty();
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
