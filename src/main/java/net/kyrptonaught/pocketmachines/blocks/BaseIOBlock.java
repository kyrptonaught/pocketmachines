package net.kyrptonaught.pocketmachines.blocks;

import net.kyrptonaught.pocketmachines.PocketMachinesMod;
import net.kyrptonaught.pocketmachines.items.IOConfigurator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BaseIOBlock extends Block implements BlockEntityProvider {
    public static final DirectionProperty IOSIDE = DirectionProperty.of("ioside", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);
    public static final EnumProperty<BaseIOBlock.INOUTDIR> IODIR = EnumProperty.of("iodir", BaseIOBlock.INOUTDIR.class);
    public static BlockEntityType<PocketMachineBaseBlockEntity> blockEntity;

    public BaseIOBlock(Settings settings, String name) {
        super(settings);
        Registry.register(Registry.BLOCK, new Identifier(PocketMachinesMod.MOD_ID, name), this);
        Registry.register(Registry.ITEM, new Identifier(PocketMachinesMod.MOD_ID, name), new BlockItem(this, new Item.Settings().group(PocketMachinesMod.GROUP)));
        blockEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(PocketMachinesMod.MOD_ID, name), BlockEntityType.Builder.create(PocketMachineBaseBlockEntity::new, this).build(null));
        this.setDefaultState(this.stateManager.getDefaultState().with(IOSIDE, Direction.NORTH).with(IODIR, INOUTDIR.IO));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getMainHandStack().getItem() instanceof IOConfigurator) {
            player.getMainHandStack().getItem().useOnBlock(new ItemUsageContext(player, hand, hit));
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IOSIDE, IODIR);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new PocketMachineBaseBlockEntity();
    }

    public enum INOUTDIR implements StringIdentifiable {
        INPUT, OUTPUT, IO;

        public String toString() {
            return this.asString();
        }

        public String asString() {
            return this == INPUT ? "input" : this == OUTPUT ? "output" : "io";
        }

        public INOUTDIR opposite() {
            return this == INPUT ? OUTPUT : this == OUTPUT ? INPUT : IO;
        }
    }
}
