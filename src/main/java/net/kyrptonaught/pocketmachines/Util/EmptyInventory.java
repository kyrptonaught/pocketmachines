package net.kyrptonaught.pocketmachines.Util;

import net.kyrptonaught.pocketmachines.Inventory.PocketInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

public class EmptyInventory extends PocketInventory implements SidedInventory {

    @Override
    public int[] getInvAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}
