package net.kyrptonaught.pocketmachines.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.HashSet;

public class ChannelManager extends PersistentState {

    private final String saveVersion = "1.0";

    private final HashMap<String, PocketMachine> pocketMachines = new HashMap<>();
    private final HashSet<BlockPos> usedPos = new HashSet<>();
    private BlockPos lastUsed = new BlockPos(0, 0, 0);

    public ChannelManager(String key) {
        super(key);
    }

    public void fromTag(CompoundTag tag) {
        pocketMachines.clear();
        CompoundTag machines = tag.getCompound("machines");
        for (String key : machines.getKeys()) {
            {
                CompoundTag machineTag = machines.getCompound(key);
                PocketMachine machine = new PocketMachine();
                machine.fromTag(machineTag);
                pocketMachines.put(key, machine);
                usedPos.add(machine.pos);
            }
        }
        String savedVersion = tag.getString("saveVersion");
    }

    public CompoundTag toTag(CompoundTag tag) {
        CompoundTag machines = new CompoundTag();
        for (String key : pocketMachines.keySet()) {
            machines.put(key, pocketMachines.get(key).toTag(new CompoundTag()));
        }
        tag.put("machines", machines);
        tag.putString("saveVersion", saveVersion);
        return tag;
    }

    public BlockPos createInvAndGivePos(String pocketMachineID) {
        BlockPos pos = getFreeBlockPos();
        PocketMachine machine = new PocketMachine();
        machine.pos = pos;
        machine.machineID = pocketMachineID;
        pocketMachines.put(pocketMachineID, machine);
        usedPos.add(machine.pos);
        return pos;
    }

    public Boolean containsID(String id) {
        return pocketMachines.containsKey(id);
    }

    public BlockPos getFreeBlockPos() {
        BlockPos checkPos = lastUsed;
        while (usedPos.contains(checkPos)) {
            checkPos = checkPos.offset(Direction.UP);
            if ((checkPos.getY() + 2) * 9 >= 255) {
                checkPos = new BlockPos(checkPos.getX(), 0, checkPos.getZ() + 1);
            }
        }
        lastUsed = checkPos;
        return checkPos;
    }

    public PocketMachine getMachine(String channel) {
        return pocketMachines.get(channel);
    }

    @Override
    public boolean isDirty() {
        return true;
    }

}