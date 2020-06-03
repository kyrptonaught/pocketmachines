package net.kyrptonaught.pocketmachines.blocks;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;

public class PocketMachineBaseBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    public String pocketMachineID = "";

    public PocketMachineBaseBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    public PocketMachineBaseBlockEntity() {
        this(PocketMachineBlock.blockEntity);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        pocketMachineID = tag.getString("pocketmachineid");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putString("pocketmachineid", pocketMachineID);
        return tag;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }
}
