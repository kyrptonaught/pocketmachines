package net.kyrptonaught.pocketmachines.util.spoofing;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.*;

public class FakeBlockView implements BlockView {
    World realWorld;
    BlockPos fakePos;

    public FakeBlockView(World realWorld, BlockPos fakePos) {
        this.realWorld = realWorld;
        this.fakePos = fakePos;
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {
        return realWorld.getBlockEntity(pos);
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        if (pos == fakePos) return Blocks.GRASS_BLOCK.getDefaultState();
        return realWorld.getBlockState(pos);
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return realWorld.getFluidState(pos);
    }

    public void updateNeighbors(BlockPos pos, Block block) {
        realWorld.updateNeighbors(pos, block);
    }

   public int getStrongRedstonePower(BlockPos pos, Direction direction) {
        return realWorld.getStrongRedstonePower(pos, direction);
   }

    @Override
    public int getHeight() {
        return realWorld.getHeight();
    }

    @Override
    public int getBottomY() {
        return realWorld.getBottomY();
    }
}
