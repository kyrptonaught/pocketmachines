package net.kyrptonaught.pocketmachines.inventory;

import net.kyrptonaught.pocketmachines.blocks.BaseIOBlock;
import net.kyrptonaught.pocketmachines.registry.ModDimensions;
import net.kyrptonaught.pocketmachines.util.DimensionalBlockPos;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PocketMachine {
    public PocketInventory inventory = new PocketInventory();
    public BaseIOBlock.INOUTDIR[] redstoneSignalDir = new BaseIOBlock.INOUTDIR[]{BaseIOBlock.INOUTDIR.INPUT, BaseIOBlock.INOUTDIR.INPUT, BaseIOBlock.INOUTDIR.INPUT, BaseIOBlock.INOUTDIR.INPUT, BaseIOBlock.INOUTDIR.INPUT, BaseIOBlock.INOUTDIR.INPUT};
    public BlockPos[] redstoneOutputPos = new BlockPos[6];
    public BlockPos pos;
    public String machineID = "";
    public DimensionalBlockPos pocketMachineBlock;

    public BlockPos getIOBlockForSide(World world, Direction side) {
        if (world.getRegistryKey() != ModDimensions.getPocketDimension()) {
            System.out.println("WRONG DIMMENSION FOR IO");
            return null;
        }

        if (redstoneOutputPos[side.ordinal()] != null)
            return redstoneOutputPos[side.ordinal()];
        BlockPos pocketMachinePos = new BlockPos(pos.getX() * 9 + 4, pos.getY() * 9 + 4, pos.getZ() * 9 + 4);
        Direction.Axis axis = side.getAxis();
        BlockPos bottomLeft = new BlockPos(axis == Direction.Axis.X ? 0 : -3, axis == Direction.Axis.Y ? 0 : -3, axis == Direction.Axis.Z ? 0 : -3);
        pocketMachinePos = pocketMachinePos.offset(side, 4).add(bottomLeft);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                BlockPos testPos = new BlockPos(0, 0, 0);
                switch (axis) {
                    case X:
                        testPos = new BlockPos(0, i, j);
                        break;
                    case Z:
                        testPos = new BlockPos(i, j, 0);
                        break;
                    case Y:
                        testPos = new BlockPos(i, 0, j);
                        break;
                }
                if (testPos(world, pocketMachinePos.add(testPos), side))
                    return pocketMachinePos.add(testPos);
            }
        return null;
    }

    public Boolean testPos(World world, BlockPos pocketMachinePos, Direction side) {
        if (world.getBlockState(pocketMachinePos).getBlock() instanceof BaseIOBlock) {
            if (world.getBlockState(pocketMachinePos).get(BaseIOBlock.IOSIDE).getOpposite() == side) {
                redstoneOutputPos[side.ordinal()] = new BlockPos(pocketMachinePos);
                return true;
            }
        }
        return false;
    }

    public void fromTag(NbtCompound tag) {
        pos = BlockPos.fromLong(tag.getLong("chunkPos"));
        redstoneSignalDir = Arrays.stream(tag.getIntArray("redstoneSignalDirs")).mapToObj(item -> BaseIOBlock.INOUTDIR.values()[item]).toArray(BaseIOBlock.INOUTDIR[]::new);
        pocketMachineBlock = DimensionalBlockPos.fromTag(tag);
        machineID = tag.getString("machineID");
        inventory = PocketInventory.fromTag(tag);
    }

    public NbtCompound toTag(NbtCompound tag) {
        tag.putLong("chunkPos", pos.asLong());
        tag.putIntArray("redstoneSignalDirs", Arrays.stream(redstoneSignalDir).map(Enum::ordinal).collect(Collectors.toList()));
        pocketMachineBlock.toTag(tag);
        tag.putString("machineID", machineID);
        PocketInventory.toTag(tag, inventory);
        return tag;
    }

}
