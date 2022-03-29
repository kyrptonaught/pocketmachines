package net.kyrptonaught.pocketmachines.inventory;

import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.util.math.Direction;


public class IOPocketInventory extends SimpleInventory implements SidedInventory {
    PocketInventory parentInventory;
    Direction ioside;

    public IOPocketInventory(PocketInventory parent, Direction ioside) {
        super();
        this.parentInventory = parent;
        this.ioside = ioside;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return parentInventory.getAvailableSlots(ioside);
    }

    @Override
    public void addListener(InventoryChangedListener inventoryListener) {
        parentInventory.addListener(inventoryListener);
    }

    @Override
    public void removeListener(InventoryChangedListener inventoryListener) {
        parentInventory.removeListener(inventoryListener);
    }

    @Override
    public ItemStack getStack(int slot) {
        return parentInventory.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return parentInventory.removeStack(slot, amount);
    }
   /*
    @Override
    public ItemStack poll(Item item, int count) {
        return parentInventory.(item, count);
    }

    */
    @Override
    public ItemStack addStack(ItemStack itemStack) {
        return parentInventory.addStack(itemStack);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return parentInventory.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        parentInventory.setStack(slot, stack);
    }

    @Override
    public boolean isEmpty() {
        return parentInventory.isEmpty();
    }

    @Override
    public void markDirty() {
        parentInventory.markDirty();
    }

    @Override
    public void clear() {
        parentInventory.clear();
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        parentInventory.provideRecipeInputs(finder);
    }

    @Override
    public String toString() {
        return parentInventory.toString();
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
