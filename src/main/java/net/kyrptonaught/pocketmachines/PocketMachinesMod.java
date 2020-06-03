package net.kyrptonaught.pocketmachines;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.kyrptonaught.pocketmachines.Inventory.ChannelManager;
import net.kyrptonaught.pocketmachines.Inventory.PocketInventory;
import net.kyrptonaught.pocketmachines.Inventory.PocketMachine;
import net.kyrptonaught.pocketmachines.Items.IOConfigurator;
import net.kyrptonaught.pocketmachines.Items.Translocator;
import net.kyrptonaught.pocketmachines.Items.Wrench;
import net.kyrptonaught.pocketmachines.Util.EmptyInventory;
import net.kyrptonaught.pocketmachines.Util.PMIOStructureProcessor;
import net.kyrptonaught.pocketmachines.blocks.*;
import net.kyrptonaught.pocketmachines.dimension.Dimensions;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class PocketMachinesMod implements ModInitializer {
    public static final String MOD_ID = "pocketmachines";
    public static Block pocketBlock, pmwallBlock, pmioBlock, ioextensionBlock, redstoneIO;
    public static List<Block> ioblocks = new ArrayList<>();
    public static Item translocator, wrench,ioconfig;
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, MOD_ID), () -> new ItemStack(pocketBlock));
    private static ChannelManager CMAN; //lol

    @Override
    public void onInitialize() {
        Dimensions.register();
        pocketBlock = new PocketMachineBlock(Block.Settings.of(Material.METAL).strength(2.5f, 2.5f));
        pmwallBlock = new PMWallBlock(Block.Settings.copy(Blocks.BEDROCK));
        pmioBlock = new PMIOBlock(Block.Settings.copy(Blocks.BEDROCK));
        redstoneIO = new RedstoneIOBlock(Block.Settings.copy(Blocks.BEDROCK));
        ioextensionBlock = new IOExtension(Block.Settings.copy(Blocks.BEDROCK));
        translocator = new Translocator(new Item.Settings().group(GROUP).maxCount(1));
        wrench = new Wrench(new Item.Settings().group(GROUP).maxCount(1));
        ioconfig = new IOConfigurator(new Item.Settings().group(GROUP).maxCount(1));
        ioblocks.add(pmioBlock);
        ioblocks.add(redstoneIO);

        ServerStartCallback.EVENT.register(server -> {
            CMAN = server.getWorld(Dimensions.pm).getPersistentStateManager().getOrCreate(() -> new ChannelManager(MOD_ID), MOD_ID);
            server.getWorld(Dimensions.pm).setChunkForced(0, 0, true);//todo load other chunks
        });
    }

    public static String getPocketMachineID(World world, BlockPos pos) {
        return ((PocketMachineBaseBlockEntity) world.getBlockEntity(pos)).pocketMachineID;
    }

    public static PocketMachine getMachine(World world, BlockPos pos) {
        return getMachine(getPocketMachineID(world, pos));
    }

    public static PocketMachine getMachine(String pocketMachineID) {
        return CMAN.getMachine(pocketMachineID);
    }

    public static PocketInventory getInv(World world, BlockPos pos) {
        return getInv(getPocketMachineID(world, pos));
    }

    public static PocketInventory getInv(String pocketMachineID) {
        PocketMachine machine = getMachine(pocketMachineID);
        if(machine == null) return new EmptyInventory();
        return machine.inventory;
    }

    public static String generatePocketMachineID() {
        String random = RandomStringUtils.randomAlphanumeric(10);
        if (CMAN.containsID(random))
            return generatePocketMachineID(); //could end badly
        return random;
    }

    public static void generatePocketMachine(World world, String pocketMachineID) {
        ServerWorld pmWorld = ((ServerWorld) world).getServer().getWorld(Dimensions.pm);
        Structure cube = pmWorld.getStructureManager().getStructure(new Identifier(PocketMachinesMod.MOD_ID, "pocketmachine"));
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setChunkPosition(null);
        PMIOStructureProcessor pmioStructureProcessor = new PMIOStructureProcessor();
        structurePlacementData.addProcessor(pmioStructureProcessor);
        BlockPos pos = CMAN.createInvAndGivePos(pocketMachineID);
        cube.place(pmWorld, pos.up(pos.getY() * 8).south(pos.getZ() * 8).east(pos.getX() * 8), structurePlacementData);
        List<BlockPos> pmioBlocks = pmioStructureProcessor.pmioBlocks;
        for (int i = 0; i < pmioBlocks.size(); i++)
            ((PocketMachineBaseBlockEntity) pmWorld.getBlockEntity(pmioBlocks.get(i))).pocketMachineID = pocketMachineID;
    }
}