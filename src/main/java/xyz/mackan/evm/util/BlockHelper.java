package xyz.mackan.evm.util;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;

public class BlockHelper {
    public static boolean isOre(BlockState state) {
        return state.isIn(ConventionalBlockTags.ORES);
    }

    public static boolean isLog(BlockState state) {
        return state.isIn(BlockTags.LOGS);
    }

    public static boolean isExcavatable(BlockState state) {
        return isOre(state) || state.getBlock() == Blocks.CLAY;
    }

    public static boolean isLeaf(BlockState state) {
        return state.isIn(BlockTags.LEAVES);
    }
}
