package net.kyrptonaught.pocketmachines.registry;

import net.kyrptonaught.pocketmachines.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static Block pocketMachineBlock, wallBlock, inventoryIOBlock, ioextensionBlock, redstoneIOBlock;
    public static List<Block> ioblocks = new ArrayList<>();

    public static void register() {
        pocketMachineBlock = new PocketMachineBlock(Block.Settings.of(Material.METAL).strength(2.5f, 2.5f));
        wallBlock = new WallBlock(Block.Settings.copy(Blocks.BEDROCK));
        inventoryIOBlock = new InventoryIOBlock(Block.Settings.copy(Blocks.BEDROCK));
        redstoneIOBlock = new RedstoneIOBlock(Block.Settings.copy(Blocks.BEDROCK));
        ioextensionBlock = new IOExtensionBlock(Block.Settings.copy(Blocks.BEDROCK));
        ioblocks.add(inventoryIOBlock);
        ioblocks.add(redstoneIOBlock);
    }
}
