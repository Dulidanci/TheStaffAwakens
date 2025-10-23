package net.dulidanci.thestaffawakens.block.custom;

import net.dulidanci.thestaffawakens.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FadingLightBlock extends LightBlock {
    public FadingLightBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int previousLightLevel = state.get(LightBlock.LEVEL_15);
        if (previousLightLevel - 1 >= 0) {
            world.setBlockState(pos, ModBlocks.FADING_LIGHT_BLOCK.getDefaultState().with(LightBlock.LEVEL_15, previousLightLevel - 1));
            world.scheduleBlockTick(pos, ModBlocks.FADING_LIGHT_BLOCK, 800);
        } else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.scheduleBlockTick(pos, ModBlocks.FADING_LIGHT_BLOCK, 800);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return context.isHolding(ModBlocks.FADING_LIGHT_BLOCK.asItem()) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }


}