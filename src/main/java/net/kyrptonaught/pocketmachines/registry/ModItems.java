package net.kyrptonaught.pocketmachines.registry;

import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.items.IOConfigurator;
import net.kyrptonaught.pocketmachines.items.Translocator;
import net.kyrptonaught.pocketmachines.items.Wrench;
import net.minecraft.item.Item;

public class ModItems {
    public static Item translocator, wrench, ioconfig;


    public static void register() {
        translocator = new Translocator(new Item.Settings().group(PocketMachinesMod.GROUP).maxCount(1));
        wrench = new Wrench(new Item.Settings().group(PocketMachinesMod.GROUP).maxCount(1));
        ioconfig = new IOConfigurator(new Item.Settings().group(PocketMachinesMod.GROUP).maxCount(1));
    }
}
