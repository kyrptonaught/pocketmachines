package net.kyrptonaught.pocketmachines.util;

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


    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData structurePlacementData) {
        if (structureBlockInfo2.state.getBlock() instanceof BaseIOBlock)
            pmioBlocks.add(structureBlockInfo2.pos);
        return structureBlockInfo2;
    }

    @Override
    protected StructureProcessorType getType() {
        return null;
    }

}
