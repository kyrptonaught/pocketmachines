package net.kyrptonaught.pocketmachines.Inventory;

import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.InventoryListener;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.util.math.Direction;


public class IOPocketInventory extends BasicInventory implements SidedInventory {
    PocketInventory parentInventory;
    Direction ioside;

    public IOPocketInventory(PocketInventory parent, Direction ioside) {
        super();
        this.parentInventory = parent;
        this.ioside = ioside;
    }

    @Override
    public int[] getInvAvailableSlots(Direction side) {
        return parentInventory.getInvAvailableSlots(ioside);
    }

    public void addListener(InventoryListener inventoryListener) {
        parentInventory.addListener(inventoryListener);
    }

    public void removeListener(InventoryListener inventoryListener) {
        parentInventory.removeListener(inventoryListener);
    }

    public ItemStack getInvStack(int slot) {
        return parentInventory.getInvStack(slot);
    }

    public ItemStack takeInvStack(int slot, int amount) {
        return parentInventory.takeInvStack(slot, amount);
    }

    public ItemStack poll(Item item, int count) {
        return parentInventory.poll(item, count);
    }

    public ItemStack add(ItemStack itemStack) {
        return parentInventory.add(itemStack);
    }

    public ItemStack removeInvStack(int slot) {
        return parentInventory.removeInvStack(slot);
    }

    public void setInvStack(int slot, ItemStack stack) {
        parentInventory.setInvStack(slot, stack);
    }

    public boolean isInvEmpty() {
        return parentInventory.isInvEmpty();
    }

    public void markDirty() {
        parentInventory.markDirty();
    }

    public void clear() {
        parentInventory.clear();
    }

    public void provideRecipeInputs(RecipeFinder recipeFinder) {
        parentInventory.provideRecipeInputs(recipeFinder);
    }

    public String toString() {
        return parentInventory.toString();
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
        return true;
    }
}
