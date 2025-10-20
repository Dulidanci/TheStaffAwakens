package net.dulidanci.staffmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.dulidanci.staffmod.block.entity.StaffUpgradeStationBlockEntity;
import net.dulidanci.staffmod.item.custom.StaffItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StaffUpgradeStationBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = createCuboidShape(0, 3, 0, 16, 12, 16);

    public StaffUpgradeStationBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StaffUpgradeStationBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof StaffUpgradeStationBlockEntity staffUpgradeStation) {
                staffUpgradeStation.modifyInventoryBeforeBreaking();

                ItemScatterer.spawn(world, pos, staffUpgradeStation);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            StaffUpgradeStationBlockEntity blockEntity = (StaffUpgradeStationBlockEntity) world.getBlockEntity(pos);
            if (player.getStackInHand(hand).getItem() instanceof StaffItem) {
                if (blockEntity.attemptInsertingStaff(player, hand)) {
                    return ActionResult.SUCCESS;
                }
            }
            if (player.isSneaking() && player.getStackInHand(hand).isEmpty()) {
                if (blockEntity.attemptRemovingStaff(player, hand)) {
                    return ActionResult.SUCCESS;
                }
            }

            if (blockEntity != null) {
                player.openHandledScreen(blockEntity);
            }
            blockEntity.markDirty();
        }
        return ActionResult.SUCCESS;
    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        return validateTicker(type, ModBlockEntities.STAFF_UPGRADE_STATION_BLOCK_ENTITY,
//                ((world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1)));
//    }
}
