package net.kyrptonaught.pocketmachines.inventory;

import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.Direction;

public class PocketInventory extends BasicInventory implements SidedInventory {

    public PocketInventory() {
        super(6);
    }

    @Override
    public int[] getInvAvailableSlots(Direction side) {
        return new int[]{side.ordinal()};
    }

    public IOPocketInventory createIOPocketInventory(Direction ioPort) {
        return new IOPocketInventory(this, ioPort);
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
        return true;
    }


    public static CompoundTag toTag(CompoundTag tag, PocketInventory inventory) {
        ListTag listTag = new ListTag();
        for (int i = 0; i < inventory.getInvSize(); ++i) {
            ItemStack itemStack = inventory.getInvStack(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte) i);
                itemStack.toTag(compoundTag);
                listTag.add(compoundTag);
            }
        }
        tag.put("Items", listTag);
        return tag;
    }

    public static PocketInventory fromTag(CompoundTag tag) {
        PocketInventory inventory = new PocketInventory();
        ListTag listTag = tag.getList("Items", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            int j = compoundTag.getByte("Slot") & 255;
            if (j >= 0 && j < inventory.getInvSize()) {
                inventory.setInvStack(j, ItemStack.fromTag(compoundTag));
            }
        }
        return inventory;
    }
}