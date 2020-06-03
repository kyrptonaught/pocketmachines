package net.kyrptonaught.pocketmachines.Items;

import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.Util.DimensionalBlockPos;
import net.kyrptonaught.pocketmachines.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Wrench extends Item {
    public Wrench(Settings settings) {
        super(settings);
        Registry.register(Registry.ITEM, new Identifier(PocketMachinesMod.MOD_ID, "wrench"), this);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient) {
            PlayerEntity playerEntity = context.getPlayer();
            BlockPos pos = context.getBlockPos();
            World world = context.getWorld();
            Block hitBlock = world.getBlockState(pos).getBlock();
            if (hitBlock instanceof PocketMachineBlock) {
                activatePocketMachine(world, pos);
            } else if (hitBlock instanceof BaseIOBlock) {
                CompoundTag tag = playerEntity.getMainHandStack().getOrCreateTag();
                tag.putLong("prevPos", pos.asLong());
            } else if (hitBlock instanceof PMWallBlock) {
                CompoundTag tag = playerEntity.getMainHandStack().getOrCreateTag();
                if (tag.contains("prevPos")) {
                    BlockPos oldPos = BlockPos.fromLong(tag.getLong("prevPos"));
                    BlockState IOSIDE = world.getBlockState(oldPos);
                    String id = PocketMachinesMod.getPocketMachineID(world, oldPos);
                    BlockPos machinePos = PocketMachinesMod.getMachine(id).pos;
                    if (isValidMove(pos, oldPos, IOSIDE.get(PMIOBlock.IOSIDE), machinePos)) {
                        world.setBlockState(oldPos, PocketMachinesMod.pmwallBlock.getDefaultState());
                        world.setBlockState(pos, IOSIDE);
                        ((PocketMachineBaseBlockEntity) world.getBlockEntity(pos)).pocketMachineID = id;
                        tag.remove("prevPos");
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    public void activatePocketMachine(World world, BlockPos pos) {
        String pocketMachineID = PocketMachinesMod.getPocketMachineID(world, pos);
        if (!pocketMachineID.equals("")) {
            System.out.println("Machine already activated: " + pocketMachineID);
            return;
        }
        pocketMachineID = PocketMachinesMod.generatePocketMachineID();
        PocketMachinesMod.generatePocketMachine(world, pocketMachineID);
        ((PocketMachineBaseBlockEntity) world.getBlockEntity(pos)).pocketMachineID = pocketMachineID;
        PocketMachinesMod.getMachine(pocketMachineID).pocketMachineBlock = new DimensionalBlockPos(world.getDimension().getType(), pos);
        System.out.println("Generated Machine: " + pocketMachineID);
    }

    public Boolean isValidMove(BlockPos newPos, BlockPos oldPos, Direction IOSIDE, BlockPos pocketMachinePos) {
        pocketMachinePos = new BlockPos(pocketMachinePos.getX() * 9, pocketMachinePos.getY() * 9, pocketMachinePos.getZ() * 9);
        if (!newPos.isWithinDistance(pocketMachinePos.add(4, 4, 4), 6))
            return false;
        switch (IOSIDE) {
            case EAST:
            case WEST:
                return newPos.getX() == oldPos.getX();
            case NORTH:
            case SOUTH:
                return newPos.getZ() == oldPos.getZ();
            case UP:
            case DOWN:
                return newPos.getY() == oldPos.getY();
        }
        return false;
    }
}