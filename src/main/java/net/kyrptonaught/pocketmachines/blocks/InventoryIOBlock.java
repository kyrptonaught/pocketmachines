package net.kyrptonaught.pocketmachines.blocks;

import net.kyrptonaught.pocketmachines.inventory.PocketInventory;
import net.kyrptonaught.pocketmachines.util.PocketMachineHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class InventoryIOBlock extends BaseIOBlock implements InventoryProvider {

    public InventoryIOBlock(Settings settings) {
        super(settings, "inventoryio");
    }


    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        Direction ioside = state.get(IOSIDE);
        PocketInventory pocketInventory = PocketMachineHelper.getInv((World) world, pos);
        return pocketInventory.createIOPocketInventory(ioside);
    }
}
