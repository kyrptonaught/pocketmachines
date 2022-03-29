package net.kyrptonaught.pocketmachines.registry;


import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.dimension.VoidChunkGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;

public class ModDimensions {
    public static HashMap<Identifier, RegistryKey<World>> dims = new HashMap<>();

    private static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(PocketMachinesMod.MOD_ID, "pocketdimension"));
    private static RegistryKey<World> WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, DIMENSION_KEY.getValue());
    private static final RegistryKey<DimensionType> DIMENSION_TYPE_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(PocketMachinesMod.MOD_ID, "pocketdimension_type"));

    public static RegistryKey<World> getPocketDimension(){
      return WORLD_KEY;
    }

    public static void register() {
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(PocketMachinesMod.MOD_ID, "pocketdimension"), VoidChunkGenerator.CODEC);
        WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, new Identifier(PocketMachinesMod.MOD_ID, "pocketdimension"));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            for (RegistryKey<World> registryKey : server.getWorldRegistryKeys()) {
                dims.put(registryKey.getValue(), registryKey);
            }
        });
    }
}
