package net.kyrptonaught.pocketmachines.blocks;

import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldAccess;

public class IOExtensionBlock extends Block implements InventoryProvider {
    public static final DirectionProperty FACING = Properties.FACING;

    public IOExtensionBlock(Settings settings) {
        super(settings);
        Registry.register(Registry.BLOCK, new Identifier(PocketMachinesMod.MOD_ID, "ioextension"), this);
        Registry.register(Registry.ITEM, new Identifier(PocketMachinesMod.MOD_ID, "ioextension"), new BlockItem(this, new Item.Settings().group(PocketMachinesMod.GROUP)));
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide().getOpposite();
        return this.getDefaultState().with(FACING, direction);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos dirPos = pos.offset(state.get(FACING));
        if (world.getBlockState(dirPos).getBlock() instanceof InventoryProvider) {
            return ((InventoryProvider) (world.getBlockState(dirPos).getBlock())).getInventory(world.getBlockState(dirPos), world, dirPos);
        }
        return null;
    }
}
