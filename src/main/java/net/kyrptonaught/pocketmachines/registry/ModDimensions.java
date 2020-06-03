package net.kyrptonaught.pocketmachines.registry;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.dimension.FabricChunkGeneratorType;
import net.kyrptonaught.pocketmachines.dimension.PocketDimension;
import net.kyrptonaught.pocketmachines.dimension.VoidChunkGenerator;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;

public class ModDimensions {
    public static ChunkGeneratorType<ChunkGeneratorConfig, VoidChunkGenerator> VOID_CHUNK_GENERATOR;

    public static final FabricDimensionType pm = FabricDimensionType.builder()
            .defaultPlacer((oldEntity, destinationWorld, portalDir, horizontalOffset, verticalOffset) -> new BlockPattern.TeleportTarget(new Vec3d(destinationWorld.getTopPosition(Heightmap.Type.WORLD_SURFACE, BlockPos.ORIGIN)), oldEntity.getVelocity(), (int) oldEntity.yaw))
            .factory(PocketDimension::new)
            .skyLight(false)
            .buildAndRegister(new Identifier(PocketMachinesMod.MOD_ID, "pm"));

    public static void register() {
        VOID_CHUNK_GENERATOR = FabricChunkGeneratorType.register(new Identifier(PocketMachinesMod.MOD_ID, "pocketdimension"), VoidChunkGenerator::new, ChunkGeneratorConfig::new, false);
    }
}
