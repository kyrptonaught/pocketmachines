package net.kyrptonaught.pocketmachines.util;

import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.blocks.PocketMachineBaseBlockEntity;
import net.kyrptonaught.pocketmachines.inventory.PocketInventory;
import net.kyrptonaught.pocketmachines.inventory.PocketMachine;
import net.kyrptonaught.pocketmachines.registry.ModDimensions;
import net.kyrptonaught.pocketmachines.util.spoofing.EmptyInventory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class PocketMachineHelper {
    public static String getPocketMachineID(World world, BlockPos pos) {
        return ((PocketMachineBaseBlockEntity) world.getBlockEntity(pos)).pocketMachineID;
    }

    public static PocketMachine getMachine(World world, BlockPos pos) {
        return getMachine(getPocketMachineID(world, pos));
    }

    public static PocketMachine getMachine(String pocketMachineID) {
        return PocketMachinesMod.CMAN.getMachine(pocketMachineID);
    }

    public static PocketInventory getInv(World world, BlockPos pos) {
        return getInv(getPocketMachineID(world, pos));
    }

    public static PocketInventory getInv(String pocketMachineID) {
        PocketMachine machine = getMachine(pocketMachineID);
        if (machine == null) return new EmptyInventory();
        return machine.inventory;
    }

    public static void activatePocketMachine(World world, BlockPos pos) {
        String pocketMachineID = getPocketMachineID(world, pos);
        if (!pocketMachineID.equals("")) {
            System.out.println("Machine already activated: " + pocketMachineID);
            return;
        }
        pocketMachineID = generatePocketMachineID();
        generatePocketMachine(world, pocketMachineID);
        ((PocketMachineBaseBlockEntity) world.getBlockEntity(pos)).pocketMachineID = pocketMachineID;
        getMachine(pocketMachineID).pocketMachineBlock = new DimensionalBlockPos(world.getDimension().getType(), pos);
        System.out.println("Generated Machine: " + pocketMachineID);
    }

    public static String generatePocketMachineID() {
        String random = RandomStringUtils.randomAlphanumeric(10);
        if (PocketMachinesMod.CMAN.containsID(random))
            return generatePocketMachineID(); //could end badly
        return random;
    }

    public static void generatePocketMachine(World world, String pocketMachineID) {
        ServerWorld pmWorld = ((ServerWorld) world).getServer().getWorld(ModDimensions.pm);
        Structure cube = pmWorld.getStructureManager().getStructure(new Identifier(PocketMachinesMod.MOD_ID, "pocketmachine"));
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setChunkPosition(null);
        PMIOStructureProcessor pmioStructureProcessor = new PMIOStructureProcessor();
        structurePlacementData.addProcessor(pmioStructureProcessor);
        BlockPos pos = PocketMachinesMod.CMAN.createInvAndGivePos(pocketMachineID);
        cube.place(pmWorld, pos.up(pos.getY() * 8).south(pos.getZ() * 8).east(pos.getX() * 8), structurePlacementData);
        List<BlockPos> pmioBlocks = pmioStructureProcessor.pmioBlocks;
        for (int i = 0; i < pmioBlocks.size(); i++)
            ((PocketMachineBaseBlockEntity) pmWorld.getBlockEntity(pmioBlocks.get(i))).pocketMachineID = pocketMachineID;
    }
}
