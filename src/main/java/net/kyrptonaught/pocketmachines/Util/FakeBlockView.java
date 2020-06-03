package net.kyrptonaught.pocketmachines.Util;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.level.LevelProperties;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class FakeBlockView implements IWorld {
    World realWorld;
    BlockPos fakePos;

    public FakeBlockView(World realWorld, BlockPos fakePos) {
        this.realWorld = realWorld;
        this.fakePos = fakePos;
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {

        return realWorld.getBlockEntity(pos);
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        if (pos == fakePos) return Blocks.GRASS_BLOCK.getDefaultState();
        return realWorld.getBlockState(pos);
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return realWorld.getFluidState(pos);
    }

    @Override
    public long getSeed() {
        return realWorld.getSeed();
    }

    @Override
    public TickScheduler<Block> getBlockTickScheduler() {
        return realWorld.getBlockTickScheduler();
    }

    @Override
    public TickScheduler<Fluid> getFluidTickScheduler() {
        return realWorld.getFluidTickScheduler();
    }

    @Override
    public World getWorld() {
        return realWorld;
    }

    @Override
    public LevelProperties getLevelProperties() {
        return realWorld.getLevelProperties();
    }

    @Override
    public LocalDifficulty getLocalDifficulty(BlockPos pos) {
        return realWorld.getLocalDifficulty(pos);
    }

    @Override
    public ChunkManager getChunkManager() {
        return realWorld.getChunkManager();
    }

    @Override
    public Random getRandom() {
        return realWorld.getRandom();
    }

    @Override
    public void updateNeighbors(BlockPos pos, Block block) {
        realWorld.updateNeighbors(pos, block);
    }

    @Override
    public BlockPos getSpawnPos() {
        return realWorld.getSpawnPos();
    }

    @Override
    public void playSound(PlayerEntity player, BlockPos blockPos, SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch) {
        realWorld.playSound(player, blockPos, soundEvent, soundCategory, volume, pitch);
    }

    @Override
    public void addParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        realWorld.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
    }

    @Override
    public void playLevelEvent(PlayerEntity player, int eventId, BlockPos blockPos, int data) {
        realWorld.playLevelEvent(player, eventId, blockPos, data);
    }

    @Override
    public LightingProvider getLightingProvider() {
        return realWorld.getLightingProvider();
    }

    @Override
    public WorldBorder getWorldBorder() {
        return realWorld.getWorldBorder();
    }

    @Override
    public List<Entity> getEntities(Entity except, Box box, Predicate<? super Entity> predicate) {
        return realWorld.getEntities(except, box, predicate);
    }

    @Override
    public <T extends Entity> List<T> getEntities(Class<? extends T> entityClass, Box box, Predicate<? super T> predicate) {
        return realWorld.getEntities(entityClass, box, predicate);
    }

    @Override
    public List<? extends PlayerEntity> getPlayers() {
        return realWorld.getPlayers();
    }

    @Override
    public boolean setBlockState(BlockPos pos, BlockState state, int flags) {
        return realWorld.setBlockState(pos, state, flags);
    }

    @Override
    public boolean removeBlock(BlockPos pos, boolean move) {
        return realWorld.removeBlock(pos, move);
    }

    @Override
    public boolean breakBlock(BlockPos pos, boolean drop, Entity breakingEntity) {
        return realWorld.breakBlock(pos, drop, breakingEntity);
    }

    @Override
    public boolean testBlockState(BlockPos blockPos, Predicate<BlockState> state) {
        return realWorld.testBlockState(blockPos, state);
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create) {
        return realWorld.getChunk(chunkX, chunkZ, leastStatus, create);
    }

    @Override
    public int getTopY(Heightmap.Type heightmap, int x, int z) {
        return realWorld.getTopY(heightmap, x, z);
    }

    @Override
    public int getAmbientDarkness() {
        return realWorld.getAmbientDarkness();
    }

    @Override
    public BiomeAccess getBiomeAccess() {
        return realWorld.getBiomeAccess();
    }

    @Override
    public Biome getGeneratorStoredBiome(int biomeX, int biomeY, int biomeZ) {
        return realWorld.getGeneratorStoredBiome(biomeX, biomeY, biomeZ);
    }

    @Override
    public boolean isClient() {
        return realWorld.isClient();
    }

    @Override
    public int getSeaLevel() {
        return realWorld.getSeaLevel();
    }

    @Override
    public Dimension getDimension() {
        return realWorld.getDimension();
    }
}
