package net.kyrptonaught.pocketmachines.Util;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FakeWorld extends ServerWorld {
    World realWorld;
    BlockPos fakePos;

    protected FakeWorld(ServerWorld realWorld) {
        super(realWorld.getServer(),realWorld.getServer().getWorkerExecutor(),realWorld.getSaveHandler(),realWorld.getLevelProperties(), realWorld.getDimension().getType(), realWorld.getProfiler(), null);
       // super(realWorld.getLevelProperties(), realWorld.getDimension().getType(), (world, dimension) -> realWorld.getChunkManager(), realWorld.getProfiler(), false);
    }
}
