package net.kyrptonaught.pocketmachines.inventory;

import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Direction;

public class PocketInventory extends SimpleInventory implements SidedInventory {

    public PocketInventory() {
        super(6);
    }

    public IOPocketInventory createIOPocketInventory(Direction ioPort) {
        return new IOPocketInventory(this, ioPort);
    }

    public static NbtCompound toTag(NbtCompound tag, PocketInventory inventory) {
        NbtList listTag = new NbtList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                NbtCompound compoundTag = new NbtCompound();
                compoundTag.putByte("Slot", (byte) i);
                itemStack.writeNbt(compoundTag);
                listTag.add(compoundTag);
            }
        }
        tag.put("Items", listTag);
        return tag;
    }

    public static PocketInventory fromTag(NbtCompound tag) {
        PocketInventory inventory = new PocketInventory();
        NbtList listTag = tag.getList("Items", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            NbtCompound compoundTag = listTag.getCompound(i);
            int j = compoundTag.getByte("Slot") & 255;
            if (j >= 0 && j < inventory.size()) {
                inventory.setStack(j, ItemStack.fromNbt(compoundTag));
            }
        }
        return inventory;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{side.ordinal()};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }
}