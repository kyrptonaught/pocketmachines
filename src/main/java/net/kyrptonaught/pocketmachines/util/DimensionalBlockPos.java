package net.kyrptonaught.pocketmachines.util;

import net.kyrptonaught.pocketmachines.registry.ModDimensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;


public class DimensionalBlockPos {
    public RegistryKey<World> dimensionType;
    public BlockPos pos;

    public DimensionalBlockPos(RegistryKey<World> dimension, BlockPos pos) {
        this.pos = pos;
        this.dimensionType = dimension;
    }

    public static DimensionalBlockPos fromTag(NbtCompound tag) {
        NbtCompound innerTag = tag.getCompound("machineBlockPos");
        return new DimensionalBlockPos(ModDimensions.dims.get(new Identifier(innerTag.getString("dimID"))), BlockPos.fromLong(innerTag.getLong("pos")));
    }

    public NbtCompound toTag(NbtCompound tag) {
        NbtCompound innerTag = new NbtCompound();
        innerTag.putString("dimID", this.dimensionType.getValue().toString());
        innerTag.putLong("pos", pos.asLong());
        tag.put("machineBlockPos", innerTag);
        return tag;
    }
}
