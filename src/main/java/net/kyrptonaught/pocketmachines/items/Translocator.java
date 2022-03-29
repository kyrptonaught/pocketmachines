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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
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
                NbtCompound tag = playerEntity.getMainHandStack().getOrCreateSubNbt(PocketMachinesMod.MOD_ID);
                tag.putLong("prevPos", playerEntity.getBlockPos().asLong());
                tag.putFloat("prevPitch", playerEntity.getPitch());
                tag.putFloat("prevYaw", playerEntity.getYaw());
                tag.putString("prevDim", playerEntity.world.getRegistryKey().getValue().toString());
                PocketMachine machine = PocketMachineHelper.getMachine(context.getWorld(), context.getBlockPos());
                BlockPos pocketMachinePos = new BlockPos(machine.pos.getX() * 9 + 4.5, machine.pos.getY() * 9 + 1, machine.pos.getZ() * 9 + 4.5);
                if (playerEntity.world.getRegistryKey() != ModDimensions.getPocketDimension()) {
                    ServerWorld serverWorld = (ServerWorld) playerEntity.getEntityWorld();
                    FabricDimensions.teleport(playerEntity, serverWorld.getServer().getWorld(ModDimensions.getPocketDimension()), new TeleportTarget(new Vec3d(pocketMachinePos.getX(),pocketMachinePos.getY(),pocketMachinePos.getZ()), Vec3d.ZERO, 0,0));
                }
                else
                    playerEntity.refreshPositionAndAngles(pocketMachinePos.getX(), pocketMachinePos.getY(), pocketMachinePos.getZ(), 0, 0);
            } else use(context.getWorld(), playerEntity, context.getHand());
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            NbtCompound tag = player.getMainHandStack().getOrCreateSubNbt(PocketMachinesMod.MOD_ID);
            if (tag.contains("prevPos")) {
                BlockPos pos = BlockPos.fromLong(tag.getLong("prevPos"));
                float pitch = tag.getFloat("prevPitch");
                float yaw = tag.getFloat("prevYaw");
                RegistryKey<World> dim = ModDimensions.dims.get(new Identifier(tag.getString("prevDim")));
                if (dim != player.world.getRegistryKey()) {
                    FabricDimensions.teleport(player, world.getServer().getWorld(dim), new TeleportTarget(new Vec3d(pos.getX(),pos.getY(),pos.getZ()), Vec3d.ZERO, 0,0));
                } else player.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, pitch);
                tag.remove("prevPos");
                tag.remove("prevDim");
            }
        }
        return TypedActionResult.success(player.getMainHandStack());
    }
}
