package net.kyrptonaught.pocketmachines.items;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.blocks.PocketMachineBlock;
import net.kyrptonaught.pocketmachines.inventory.PocketMachine;
import net.kyrptonaught.pocketmachines.registry.ModDimensions;
import net.kyrptonaught.pocketmachines.util.PocketMachineHelper;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Translocator extends Item {
    public Translocator(Settings settings) {
        super(settings);
        Registry.register(Registry.ITEM, new Identifier(PocketMachinesMod.MOD_ID, "translocator"), this);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient) {
            PlayerEntity playerEntity = context.getPlayer();
            if (context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof PocketMachineBlock) {
                CompoundTag tag = playerEntity.getMainHandStack().getOrCreateTag();
                tag.putLong("prevPos", playerEntity.getBlockPos().asLong());
                tag.putFloat("prevPitch", playerEntity.pitch);
                tag.putFloat("prevYaw", playerEntity.yaw);
                tag.putInt("prevDim", Registry.DIMENSION_TYPE.getRawId(playerEntity.dimension));
                PocketMachine machine = PocketMachineHelper.getMachine(context.getWorld(), context.getBlockPos());
                BlockPos pocketMachinePos = new BlockPos(machine.pos.getX() * 9 + 4.5, machine.pos.getY() * 9 + 1, machine.pos.getZ() * 9 + 4.5);
                if (playerEntity.dimension != ModDimensions.pm)
                    FabricDimensions.teleport(playerEntity, ModDimensions.pm, (entity, serverWorld, direction, v, v1) -> {
                        entity.refreshPositionAndAngles(pocketMachinePos.getX(), pocketMachinePos.getY(), pocketMachinePos.getZ(), 0, 0);
                        return new BlockPattern.TeleportTarget(new Vec3d(pocketMachinePos), Vec3d.ZERO, 0);
                    });
                else
                    playerEntity.refreshPositionAndAngles(pocketMachinePos.getX(), pocketMachinePos.getY(), pocketMachinePos.getZ(), 0, 0);
            } else use(context.getWorld(), playerEntity, context.getHand());
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            CompoundTag tag = player.getMainHandStack().getOrCreateTag();
            if (tag.contains("prevPos")) {
                BlockPos pos = BlockPos.fromLong(tag.getLong("prevPos"));
                float pitch = tag.getFloat("prevPitch");
                float yaw = tag.getFloat("prevYaw");
                int dim = tag.getInt("prevDim");
                if (dim != Registry.DIMENSION_TYPE.getRawId(player.dimension)) {
                    FabricDimensions.teleport(player, Registry.DIMENSION_TYPE.get(dim), (entity, serverWorld, direction, v, v1) -> {
                        entity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, pitch);
                        return new BlockPattern.TeleportTarget(new Vec3d(pos), Vec3d.ZERO, 0);
                    });
                } else player.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, pitch);
                tag.remove("prevPos");
                tag.remove("prevDim");
            }
        }
        return TypedActionResult.success(player.getMainHandStack());
    }
}
