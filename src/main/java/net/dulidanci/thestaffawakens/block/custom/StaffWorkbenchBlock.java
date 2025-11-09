package net.dulidanci.thestaffawakens.block.custom;

import com.mojang.serialization.MapCodec;
import net.dulidanci.thestaffawakens.block.entity.StaffWorkbenchBlockEntity;
import net.dulidanci.thestaffawakens.item.custom.StaffItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StaffWorkbenchBlock extends BlockWithEntity {
    protected static final VoxelShape MAIN_BODY_SHAPE;
    protected static final VoxelShape LEGS_SHAPE;
    protected static final VoxelShape HAND_N_AND_S_SHAPE;
    protected static final VoxelShape HAND_E_AND_W_SHAPE;
    protected static final VoxelShape NORTH_AND_SOUTH_SHAPE;
    protected static final VoxelShape EAST_AND_WEST_SHAPE;
    public static final DirectionProperty FACING;

    public StaffWorkbenchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH, SOUTH -> {
                return NORTH_AND_SOUTH_SHAPE;
            }
            case EAST, WEST -> {
                return EAST_AND_WEST_SHAPE;
            }
            default -> {
                return MAIN_BODY_SHAPE;
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StaffWorkbenchBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof StaffWorkbenchBlockEntity staffWorkbench) {
                staffWorkbench.modifyInventoryBeforeBreaking();
                ItemScatterer.spawn(world, pos, staffWorkbench);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof StaffWorkbenchBlockEntity staffWorkbenchBlockEntity) {

                if (player.getStackInHand(hand).getItem() instanceof StaffItem && !staffWorkbenchBlockEntity.hasStaff()) {
                    staffWorkbenchBlockEntity.insertStaff(player, hand);

                } else if (player.isSneaking() && player.getStackInHand(hand).isEmpty() && staffWorkbenchBlockEntity.hasStaff()) {
                    staffWorkbenchBlockEntity.removeStaff(player, hand);

                } else {
                    player.openHandledScreen(staffWorkbenchBlockEntity);
                }

                staffWorkbenchBlockEntity.markDirty();
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        return validateTicker(type, ModBlockEntities.STAFF_UPGRADE_STATION_BLOCK_ENTITY,
//                ((world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1)));
//    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        MAIN_BODY_SHAPE = Block.createCuboidShape(0, 3, 0, 16, 12, 16);
        LEGS_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(13, 0, 1, 15, 3, 3),
                Block.createCuboidShape(13, 0, 13, 15, 3, 15),
                Block.createCuboidShape(1, 0, 13, 3, 3, 15),
                Block.createCuboidShape(1, 0, 1, 3, 3, 3));
        HAND_E_AND_W_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(13, 12, 7, 14, 16, 9),
                Block.createCuboidShape(13, 15, 6, 14, 18, 7),
                Block.createCuboidShape(13, 15, 9, 14, 18, 10),
                Block.createCuboidShape(2, 12, 7, 3, 16, 9),
                Block.createCuboidShape(2, 15, 6, 3, 18, 7),
                Block.createCuboidShape(2, 15, 9, 3, 18, 10));
        HAND_N_AND_S_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(7, 12, 2, 9, 16, 3),
                Block.createCuboidShape(6, 15, 2, 7, 18, 3),
                Block.createCuboidShape(9, 15, 2, 10, 18, 3),
                Block.createCuboidShape(7, 12, 13, 9, 16, 14),
                Block.createCuboidShape(6, 15, 13, 7, 18, 14),
                Block.createCuboidShape(9, 15, 13, 10, 18, 14));
        NORTH_AND_SOUTH_SHAPE = VoxelShapes.union(MAIN_BODY_SHAPE, LEGS_SHAPE, HAND_E_AND_W_SHAPE);
        EAST_AND_WEST_SHAPE = VoxelShapes.union(MAIN_BODY_SHAPE, LEGS_SHAPE, HAND_N_AND_S_SHAPE);
    }
}
