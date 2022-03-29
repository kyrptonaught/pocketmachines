package net.kyrptonaught.pocketmachines.inventory;


import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.minecraft.nbt.NbtCompound;
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

    public ChannelManager() {
        super();
    }

    public static PersistentState fromNbt(NbtCompound tag) {
        ChannelManager cman = new ChannelManager();
        NbtCompound machines = tag.getCompound("machines");
        for (String key : machines.getKeys()) {
            {
                NbtCompound machineTag = machines.getCompound(key);
                PocketMachine machine = new PocketMachine();
                machine.fromTag(machineTag);
                cman.pocketMachines.put(key, machine);
                cman.usedPos.add(machine.pos);
            }
        }
        String savedVersion = tag.getString("saveVersion");
        if (!savedVersion.equals(cman.saveVersion))
            System.out.println("[" + PocketMachinesMod.MOD_ID + "]: savefile outdated");
        return cman;
    }

    public NbtCompound writeNbt(NbtCompound tag) {
        NbtCompound machines = new NbtCompound();
        for (String key : pocketMachines.keySet()) {
            machines.put(key, pocketMachines.get(key).toTag(new NbtCompound()));
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