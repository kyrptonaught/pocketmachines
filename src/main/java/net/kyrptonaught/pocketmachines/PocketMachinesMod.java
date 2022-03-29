package net.kyrptonaught.pocketmachines;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.kyrptonaught.pocketmachines.inventory.ChannelManager;
import net.kyrptonaught.pocketmachines.registry.ModBlocks;
import net.kyrptonaught.pocketmachines.registry.ModDimensions;
import net.kyrptonaught.pocketmachines.registry.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PocketMachinesMod implements ModInitializer {
    public static final String MOD_ID = "pocketmachines";
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, MOD_ID), () -> new ItemStack(ModBlocks.pocketMachineBlock));
    public static ChannelManager CMAN; //lol

    @Override
    public void onInitialize() {
        ModDimensions.register();
        ModBlocks.register();
        ModItems.register();
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            CMAN = (ChannelManager) server.getWorld(ModDimensions.getPocketDimension()).getPersistentStateManager().getOrCreate(ChannelManager::fromNbt, ChannelManager::new, MOD_ID);
            server.getWorld(ModDimensions.getPocketDimension()).setChunkForced(0, 0, true);//todo load other chunks
        });
    }
}