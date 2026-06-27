//   Copyright 2026 Dulidanci
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package net.dulidanci.thestaffawakens.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class StaffWorkbenchBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<StaffWorkbenchBlock> CODEC = simpleCodec(StaffWorkbenchBlock::new);
    private static final Map<Direction.Axis, VoxelShape> SHAPES;

    public StaffWorkbenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected @NonNull BlockState rotate(final BlockState state, final Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected @NonNull VoxelShape getShape(final BlockState state, final @NonNull BlockGetter level, final @NonNull BlockPos pos, final @NonNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING).getAxis());
    }

    public @NonNull MapCodec<StaffWorkbenchBlock> codec() {
        return CODEC;
    }

    static {
        SHAPES = Shapes.rotateHorizontalAxis(Shapes.or(
                // main body shape
                Block.box(0, 3, 0, 16, 12, 16),
                // legs shape
                Shapes.or(
                        Block.box(13, 0, 1, 15, 3, 3),
                        Block.box(13, 0, 13, 15, 3, 15),
                        Block.box(1, 0, 13, 3, 3, 15),
                        Block.box(1, 0, 1, 3, 3, 3)),
                // hands shape
                Shapes.or(
                        Block.box(13, 12, 7, 14, 16, 9),
                        Block.box(13, 15, 6, 14, 18, 7),
                        Block.box(13, 15, 9, 14, 18, 10),
                        Block.box(2, 12, 7, 3, 16, 9),
                        Block.box(2, 15, 6, 3, 18, 7),
                        Block.box(2, 15, 9, 3, 18, 10))
        ));
    }
}
