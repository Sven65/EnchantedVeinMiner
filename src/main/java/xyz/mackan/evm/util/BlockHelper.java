package xyz.mackan.evm.util;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlockHelper {
    private static final List<Block> ROOTS = new ArrayList<>(
            Collections.singletonList(Blocks.MANGROVE_ROOTS)
    );

    public static final List<Block> TREE_ATTACHMENTS = new ArrayList<>(Arrays.asList(
            Blocks.VINE,
            Blocks.WEEPING_VINES,
            Blocks.TWISTING_VINES,
            Blocks.BEEHIVE,
            Blocks.COCOA,
            Blocks.HANGING_ROOTS,
            Blocks.MANGROVE_PROPAGULE,
            Blocks.MOSS_CARPET
    ));

    public static final List<Block> LARGE_MUSHROOM_BLOCKS = new ArrayList<>(Arrays.asList(
            Blocks.BROWN_MUSHROOM_BLOCK,
            Blocks.RED_MUSHROOM_BLOCK,
            Blocks.MUSHROOM_STEM
    ));

    public static boolean isOre(BlockState state) {
        return state.isIn(ConventionalBlockTags.ORES);
    }

    public static boolean isLog(BlockState state) {
        return state.isIn(BlockTags.LOGS) || state.getBlock().equals(Blocks.MANGROVE_ROOTS) || LARGE_MUSHROOM_BLOCKS.contains(state.getBlock());
    }

    public static boolean isExcavatable(BlockState state) {
        return isOre(state) || state.getBlock() == Blocks.CLAY;
    }

    public static boolean isLeaf(BlockState state) {
        return state.isIn(BlockTags.LEAVES);
    }

    public static boolean isRoot(BlockState state) {
        return ROOTS.contains(state.getBlock());
    }
}
