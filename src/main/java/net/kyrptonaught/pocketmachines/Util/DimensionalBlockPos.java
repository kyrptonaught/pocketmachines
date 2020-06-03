package net.kyrptonaught.pocketmachines.Util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;


public class DimensionalBlockPos {
    public DimensionType dimensionType;
    public BlockPos pos;
    public DimensionalBlockPos(DimensionType dimension, BlockPos pos) {
       this.pos = pos;
        this.dimensionType = dimension;
    }

    public static DimensionalBlockPos fromTag(CompoundTag tag) {
        CompoundTag innerTag = tag.getCompound("machineBlockPos");
        return new DimensionalBlockPos(Registry.DIMENSION_TYPE.get(innerTag.getInt("dimID")), BlockPos.fromLong(innerTag.getLong("pos")));
    }

    public CompoundTag toTag(CompoundTag tag) {
        CompoundTag innerTag = new CompoundTag();
        innerTag.putInt("dimID", Registry.DIMENSION_TYPE.getRawId(this.dimensionType));
        innerTag.putLong("pos", pos.asLong());
        tag.put("machineBlockPos", innerTag);
        return tag;
    }
}
