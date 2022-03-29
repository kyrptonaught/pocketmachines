package net.kyrptonaught.pocketmachines.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.inventory.PocketMachine;
import net.kyrptonaught.pocketmachines.registry.ModDimensions;
import net.kyrptonaught.pocketmachines.util.PocketMachineHelper;
import net.kyrptonaught.pocketmachines.util.spoofing.FakeBlockView;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class PocketMachineBlock extends Block implements BlockEntityProvider, InventoryProvider {
    public static BlockEntityType<PocketMachineBaseBlockEntity> blockEntity;

    public PocketMachineBlock(Settings settings) {
        super(settings);
        Registry.register(Registry.BLOCK, new Identifier(PocketMachinesMod.MOD_ID, "pocketmachineblock"), this);
        blockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, PocketMachinesMod.MOD_ID + ":pocketmachineblock", FabricBlockEntityTypeBuilder.create(PocketMachineBaseBlockEntity::new, this).build(null));
        Registry.register(Registry.ITEM, new Identifier(PocketMachinesMod.MOD_ID, "pocketmachineblock"), new BlockItem(this, new Item.Settings().group(PocketMachinesMod.GROUP)));

    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClient()) {
            neighborUpdate(state, (World) world, pos, null, neighborPos, false);
        }
        return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        BlockPos dir = neighborPos.subtract(pos);
        Direction blockDir = Direction.fromVector(dir.getX(), dir.getY(), dir.getZ());
        PocketMachine machine = PocketMachineHelper.getMachine(world, pos);
        if (machine == null) return;
        /*
        if (machine.redstoneSignalDir[blockDir.ordinal()] != BaseIOBlock.INOUTDIR.OUTPUT) {

            ServerWorld machineWorld = ((ServerWorld) world).getServer().getWorld(ModDimensions.getPocketDimension());
            BlockPos ioPos = machine.getIOBlockForSide(machineWorld, blockDir);
            BlockPos updatePos = ioPos.offset(blockDir.getOpposite());
            BlockState updateState = machineWorld.getBlockState(updatePos);

            updateState.neighborUpdate(machineWorld, updatePos, updateState.getBlock(), ioPos, false);
        }
         */
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
        if (!(view instanceof ClientWorld)) {
            PocketMachine machine = PocketMachineHelper.getMachine((World) view, pos);
            if (machine == null) return 0;
            facing = facing.getOpposite();
            /*
            if (machine.redstoneSignalDir[facing.ordinal()] != BaseIOBlock.INOUTDIR.INPUT) {
                ServerWorld machineWorld = ((ServerWorld) view).getServer().getWorld(ModDimensions.getPocketDimension());
                BlockPos ioPos = machine.getIOBlockForSide(machineWorld, facing);

                //return machineWorld.getEmittedRedstonePower(ioPos.offset(facing), facing);
                FakeBlockView fakeView = new FakeBlockView(machineWorld, ioPos);
                fakeView.updateNeighbors(ioPos, Blocks.GRASS_BLOCK);
                return fakeView.getBlockState(ioPos.offset(facing.getOpposite())).getStrongRedstonePower((BlockView) fakeView, ioPos.offset(facing.getOpposite()), facing);
            }

             */
        }
        return 0;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PocketMachineBaseBlockEntity(pos,state);
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return PocketMachineHelper.getInv((World) world, pos);
    }

    public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
        return true;
    }

    public boolean isSimpleFullBlock(BlockState state, BlockView view, BlockPos pos) {
        return false;
    }

    public boolean allowsSpawning(BlockState state, BlockView view, BlockPos pos, EntityType<?> type) {
        return false;
    }
}
