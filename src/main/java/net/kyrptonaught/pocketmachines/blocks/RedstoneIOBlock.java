package net.kyrptonaught.pocketmachines.blocks;

import net.kyrptonaught.pocketmachines.Inventory.PocketMachine;
import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.Util.FakeBlockView;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class RedstoneIOBlock extends BaseIOBlock {
    public RedstoneIOBlock(Settings settings) {
        super(settings, "redstoneio");
        this.setDefaultState(this.stateManager.getDefaultState().with(IOSIDE, Direction.NORTH).with(IODIR, INOUTDIR.OUTPUT));

    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClient()) {
            neighborUpdate(state, (World) world, pos, null, neighborPos, false);
        }
        return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        PocketMachine machine = PocketMachinesMod.getMachine(world, pos);
        if (machine == null) return;
        if (machine.redstoneSignalDir[state.get(IOSIDE).ordinal()] != INOUTDIR.INPUT) {
            ServerWorld machineWorld = ((ServerWorld) world).getServer().getWorld(machine.pocketMachineBlock.dimensionType);
            BlockPos ioPos = machine.pocketMachineBlock.pos;
            BlockPos updatePos = ioPos.offset(state.get(IOSIDE));
            BlockState updateState = machineWorld.getBlockState(updatePos);
            updateState.neighborUpdate(machineWorld, updatePos, updateState.getBlock(), ioPos, false);
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction facing) {
        return getWeakRedstonePower(state, view, pos, facing);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction facing) {
        PocketMachine machine = PocketMachinesMod.getMachine((World) view, pos);
        if (machine == null) return 0;
        if (machine.redstoneSignalDir[state.get(IOSIDE).ordinal()] != INOUTDIR.OUTPUT) {
            ServerWorld machineWorld = ((ServerWorld) view).getServer().getWorld(machine.pocketMachineBlock.dimensionType);
            BlockPos ioPos = machine.pocketMachineBlock.pos;

            FakeBlockView fakeView = new FakeBlockView(machineWorld, ioPos);
            fakeView.updateNeighbors(ioPos, Blocks.GRASS_BLOCK);
            return fakeView.getStrongRedstonePower(ioPos.offset(state.get(IOSIDE)), state.get(IOSIDE));
        }
        return 0;
    }
}