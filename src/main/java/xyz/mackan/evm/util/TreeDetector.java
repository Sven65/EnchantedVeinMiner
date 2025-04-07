package xyz.mackan.evm.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Adapted from TreeCutter.java in the Create mod (MIT License)
 * https://github.com/Creators-of-Create/Create
 */
public class TreeDetector {
    public static final Tree NO_TREE =
            new Tree(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

    public static Tree findTree(World world, BlockPos startPos) {
        List<BlockPos> logs = new ArrayList<>();
        List<BlockPos> leaves = new ArrayList<>();
        List<BlockPos> attachments = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        List<BlockPos> frontier = new LinkedList<>();


        if (!validateCut(world, startPos)) return NO_TREE;

        visited.add(startPos);

        // Start and end positions for the bounding box
        BlockPos min = startPos.offset(Direction.WEST, 1).offset(Direction.DOWN, 1).offset(Direction.NORTH, 1); // Offsets -1, 0, -1
        BlockPos max = startPos.offset(Direction.EAST, 1).offset(Direction.UP, 1).offset(Direction.SOUTH, 1); // Offsets +1, +1, +1

        BlockPos.stream(min, max)
                .forEach(p -> frontier.add(new BlockPos(p)));

        // Find all logs & roots
        boolean hasRoots = false;
        while (!frontier.isEmpty()) {
            BlockPos currentPos = frontier.remove(0);
            if (!visited.add(currentPos))
                continue;

            BlockState currentState = world.getBlockState(currentPos);
            if (BlockHelper.isRoot(currentState))
                hasRoots = true;
            else if (!BlockHelper.isLog(currentState))
                continue;
            logs.add(currentPos);
            forNeighbours(currentPos, visited, SearchDirection.UP, p -> frontier.add(new BlockPos(p)));
        }

        visited.clear();
        visited.addAll(logs);
        frontier.addAll(logs);

        if (hasRoots) {
            while (!frontier.isEmpty()) {
                BlockPos currentPos = frontier.remove(0);
                if (!logs.contains(currentPos) && !visited.add(currentPos))
                    continue;

                BlockState currentState = world.getBlockState(currentPos);
                if (!BlockHelper.isRoot(currentState))
                    continue;
                logs.add(currentPos);
                forNeighbours(currentPos, visited, SearchDirection.DOWN, p -> frontier.add(new BlockPos(p)));
            }

            visited.clear();
            visited.addAll(logs);
            frontier.addAll(logs);
        }

        // Find all leaves
        while (!frontier.isEmpty()) {
            BlockPos prevPos = frontier.remove(0);
            if (!logs.contains(prevPos) && !visited.add(prevPos))
                continue;

            BlockState prevState = world.getBlockState(prevPos);
            int prevLeafDistance = BlockHelper.isLeaf(prevState) ? getLeafDistance(prevState) : 0;

            forNeighbours(prevPos, visited, SearchDirection.BOTH, currentPos -> {
                BlockState state = world.getBlockState(currentPos);
                BlockPos subtract = currentPos.subtract(startPos);
                BlockPos currentPosImmutable = currentPos.toImmutable();


                if (BlockHelper.TREE_ATTACHMENTS.contains(state.getBlock())) {
                    attachments.add(currentPosImmutable);
                    visited.add(currentPosImmutable);
                    return;
                }

                int horizontalDistance = Math.max(Math.abs(subtract.getX()), Math.abs(subtract.getZ()));
                if (horizontalDistance <= nonDecayingLeafDistance(state)) {
                    leaves.add(currentPosImmutable);
                    frontier.add(currentPosImmutable);
                    return;
                }

                if (BlockHelper.isLeaf(state) && getLeafDistance(state) > prevLeafDistance) {
                    leaves.add(currentPosImmutable);
                    frontier.add(currentPosImmutable);
                    return;
                }

            });
        }

        return new Tree(logs, leaves, attachments);
    }

    private static int getLeafDistance(BlockState state) {
        IntProperty distanceProperty = LeavesBlock.DISTANCE;
        for (Property<?> property : state.getProperties()) {
            // Check if the property is an IntProperty and its name is "distance"
            if (property instanceof IntProperty ip && property.getName().equals("distance")) {
                distanceProperty = ip;  // Update distanceProperty to the found one
            }
        }
        return state.get(distanceProperty);
    }


    private static boolean validateCut(World world, BlockPos pos) {
        Set<BlockPos> visited = new HashSet<>();
        List<BlockPos> frontier = new LinkedList<>();
        frontier.add(pos);
        frontier.add(pos.up());
        int posY = pos.getY();

        while (!frontier.isEmpty()) {
            BlockPos currentPos = frontier.remove(0);
            BlockPos belowPos = currentPos.down();

            visited.add(currentPos);
            boolean lowerLayer = currentPos.getY() == posY;

            BlockState currentState = world.getBlockState(currentPos);
            BlockState belowState = world.getBlockState(belowPos);

            if (!BlockHelper.isLog(currentState) && !BlockHelper.isRoot(currentState))
                continue;
            if (!lowerLayer && !pos.equals(belowPos) && (BlockHelper.isLog(belowState) || BlockHelper.isRoot(belowState)))
                return false;

            for (Direction direction : Direction.values()) {
                if (direction == Direction.DOWN)
                    continue;
                if (direction == Direction.UP && !lowerLayer)
                    continue;
                BlockPos offset = currentPos.offset(direction);
                if (visited.contains(offset))
                    continue;
                frontier.add(offset);
            }

        }

        return true;
    }

    private enum SearchDirection {
        UP(0, 1), DOWN(-1, 0), BOTH(-1, 1);

        int minY;
        int maxY;

        private SearchDirection(int minY, int maxY) {
            this.minY = minY;
            this.maxY = maxY;
        }
    }

    private static void forNeighbours(BlockPos pos, Set<BlockPos> visited, SearchDirection direction,
                                      Consumer<BlockPos> acceptor) {
        // Define the min and max Y offsets based on the SearchDirection
        BlockPos min = pos.offset(Direction.WEST, 1).offset(Direction.DOWN, direction.minY)
                .offset(Direction.NORTH, 1);
        BlockPos max = pos.offset(Direction.EAST, 1).offset(Direction.UP, direction.maxY)
                .offset(Direction.SOUTH, 1);

        // Stream over the block positions in the defined range, filtering out visited positions
        BlockPos.stream(min, max)
                .filter(p -> !visited.contains(p))  // Negating visited check
                .forEach(acceptor);  // Accept the valid positions
    }

    private static int nonDecayingLeafDistance(BlockState state) {
        if (state.getBlock().equals(Blocks.RED_MUSHROOM_BLOCK))
            return 2;
        if (state.getBlock().equals(Blocks.BROWN_MUSHROOM_BLOCK))
            return 3;
        if (state.isIn(BlockTags.WART_BLOCKS) || state.getBlock().equals(Blocks.WEEPING_VINES) || state.getBlock().equals(Blocks.WEEPING_VINES_PLANT))
            return 3;
        return -1;
    }

    public static class Tree {
        public final List<BlockPos> logs;
        public final List<BlockPos> leaves;
        public final List<BlockPos> attachments;

        public Tree(List<BlockPos> logs, List<BlockPos> leaves, List<BlockPos> attachments) {
            this.logs = logs;
            this.leaves = leaves;
            this.attachments = attachments;
        }
    }
}