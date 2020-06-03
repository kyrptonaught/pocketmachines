package net.kyrptonaught.pocketmachines.Util;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.kyrptonaught.pocketmachines.blocks.BaseIOBlock;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.ArrayList;
import java.util.List;

public class PMIOStructureProcessor extends StructureProcessor {

    public List<BlockPos> pmioBlocks = new ArrayList<>();

    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData placementData) {
        if (structureBlockInfo2.state.getBlock() instanceof BaseIOBlock)
            pmioBlocks.add(structureBlockInfo2.pos);
        return structureBlockInfo2;
    }

    @Override
    protected StructureProcessorType getType() {
        return null;
    }

    @Override
    protected <T> Dynamic<T> method_16666(DynamicOps<T> dynamicOps) {
        return null;
    }
}
