package net.kyrptonaught.pocketmachines.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyrptonaught.pocketmachines.registry.ModDimensions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class PocketDimension extends Dimension {
    private static final Vec3d FOG_COLOR = new Vec3d(0.54, 0.44, 0.16);

    public PocketDimension(World world, DimensionType type) {
        // The third argument indicates how visually bright light level 0 is, with 0 being no extra brightness and 1 being like night vision.
        // The overworld and the end set this to 0, and the Nether sets this to 0.1. We want our dimension to be a bit brighter.
        super(world, type, 0.5f);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return ModDimensions.VOID_CHUNK_GENERATOR.create(world,
                BiomeSourceType.FIXED.applyConfig(BiomeSourceType.FIXED.getConfig(world.getLevelProperties())
                        .setBiome(Biomes.PLAINS)),
                ModDimensions.VOID_CHUNK_GENERATOR.createSettings());
    }

    // The following 2 methods relate to the dimension's spawn point.
    // You can return null if you don't want the player to be able to respawn in these dimensions.

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean checkMobSpawnValidity) {
        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int x, int z, boolean checkMobSpawnValidity) {
        return null;
    }

    @Override
    public float getSkyAngle(long worldTime, float tickDelta) {
        return 0;
    }

    @Override
    public boolean hasVisibleSky() {
        return true;
    }

    // Fog color RGB
    @Environment(EnvType.CLIENT)
    @Override
    public Vec3d getFogColor(float skyAngle, float tickDelta) {
        return FOG_COLOR;
    }

    @Override
    public boolean canPlayersSleep() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isFogThick(int x, int z) {
        return false;
    }

    @Override
    public DimensionType getType() {
        return ModDimensions.pm;
    }
}