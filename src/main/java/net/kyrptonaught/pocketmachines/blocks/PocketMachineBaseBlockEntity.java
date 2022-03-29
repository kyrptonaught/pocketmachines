package net.kyrptonaught.pocketmachines.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class PocketMachineBaseBlockEntity extends BlockEntity  {
    public String pocketMachineID = "";

    public PocketMachineBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PocketMachineBaseBlockEntity(BlockPos pos, BlockState state) {
        super(PocketMachineBlock.blockEntity, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        pocketMachineID = nbt.getString("pocketmachineid");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("pocketmachineid", pocketMachineID);
    }

    /*
    @Override
    public void fromClientTag(NbtCompound tag) {
        pocketMachineID = tag.getString("pocketmachineid");
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        return toTag(tag);
    }

     */
}
