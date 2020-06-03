package net.kyrptonaught.pocketmachines.blocks;

import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WallBlock extends Block {
    public WallBlock(Block.Settings settings) {
        super(settings);
        Registry.register(Registry.BLOCK, new Identifier(PocketMachinesMod.MOD_ID, "wall"), this);
        Registry.register(Registry.ITEM, new Identifier(PocketMachinesMod.MOD_ID, "wall"), new BlockItem(this, new Item.Settings().group(PocketMachinesMod.GROUP)));
    }
}