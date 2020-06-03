package net.kyrptonaught.pocketmachines.blocks;

import net.kyrptonaught.pocketmachines.Inventory.PocketInventory;
import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PMIOBlock extends BaseIOBlock implements InventoryProvider {

    public PMIOBlock(Settings settings) {
        super(settings, "pmio");
    }


    @Override
    public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos) {
        Direction ioside = state.get(IOSIDE);
        PocketInventory pocketInventory = PocketMachinesMod.getInv((World) world, pos);
        return pocketInventory.createIOPocketInventory(ioside);
    }
}
