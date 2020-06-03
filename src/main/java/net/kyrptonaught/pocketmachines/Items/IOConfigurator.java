package net.kyrptonaught.pocketmachines.Items;

import net.kyrptonaught.pocketmachines.Inventory.PocketMachine;
import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class IOConfigurator extends Item {

    public IOConfigurator(Item.Settings settings) {
        super(settings);
        Registry.register(Registry.ITEM, new Identifier(PocketMachinesMod.MOD_ID, "ioconfigurator"), this);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient) {
            PlayerEntity playerEntity = context.getPlayer();
            BlockPos pos = context.getBlockPos();
            World world = context.getWorld();
            Block hitBlock = world.getBlockState(pos).getBlock();
            if (playerEntity.isSneaking()) {
                if (hitBlock instanceof BaseIOBlock) {
                    int index = PocketMachinesMod.ioblocks.indexOf(hitBlock) + 1;
                    if (index >= PocketMachinesMod.ioblocks.size()) index = 0;
                    BlockState old = world.getBlockState(pos);
                    setBlockWithData(world, pos, clone(old, PocketMachinesMod.ioblocks.get(index).getDefaultState()));
                }
            } else {
                BlockState state = world.getBlockState(pos).cycle(BaseIOBlock.IODIR);
                setBlockWithData(world, pos, state);
            }
        }
        return ActionResult.SUCCESS;
    }

    public BlockState clone(BlockState old, BlockState newState) {
        return newState.with(BaseIOBlock.IOSIDE, old.get(BaseIOBlock.IOSIDE)).with(BaseIOBlock.IODIR, old.get(BaseIOBlock.IODIR));
    }

    public void setBlockWithData(World world, BlockPos pos, BlockState state) {
        PocketMachine machine = PocketMachinesMod.getMachine(world, pos);
        Direction ioside = state.get(BaseIOBlock.IOSIDE);
        BaseIOBlock.INOUTDIR iodir = state.get(BaseIOBlock.IODIR);
        machine.redstoneSignalDir[ioside.ordinal()] = iodir;
        world.setBlockState(pos, state);
        ((PocketMachineBaseBlockEntity) world.getBlockEntity(pos)).pocketMachineID = machine.machineID;
        world.updateNeighbors(pos, state.getBlock());
    }
}
