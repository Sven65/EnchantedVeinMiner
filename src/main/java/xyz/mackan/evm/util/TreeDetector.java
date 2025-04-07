package xyz.mackan.evm.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.*;

public class TreeDetector {
    public static Set<BlockPos> findLogsAndBranches(World world, BlockPos startPos, int limit, int searchRadius) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();

        // Start with the initial log block (either part of trunk or branch)
        Block blockType = world.getBlockState(startPos).getBlock();
        visited.add(startPos);
        queue.add(startPos);

        while (!queue.isEmpty() && visited.size() < limit) {
            BlockPos current = queue.poll();

            // Check adjacent positions for trunk/branch logs and leaves
            for (BlockPos neighbor : getAdjacentPositionsIncludingDiagonals(current)) {
                if (visited.contains(neighbor)) continue;

                BlockState neighborState = world.getBlockState(neighbor);

                // If it's a log block, it's part of the trunk or branch
                if (neighborState.getBlock() == blockType) {
                    visited.add(neighbor);
                    queue.add(neighbor);

                    // If we've reached the limit, break early
                    if (visited.size() >= limit) break;
                } else if (BlockHelper.isLeaf(neighborState)) {
                    // If it's leaves, check for logs within the crown
                    Set<BlockPos> logsInCrown = findLogsSurroundedByLeaves(world, neighbor, searchRadius);
                    visited.addAll(logsInCrown);
                    if (visited.size() >= limit) break;
                }
            }
        }

        return visited;
    }

    // Function to find logs surrounded by leaves within a radius
    public static Set<BlockPos> findLogsSurroundedByLeaves(World world, BlockPos startPos, int searchRadius) {
        Set<BlockPos> logsFound = new HashSet<>();

        // Scan within the radius around the leaf block for logs
        for (BlockPos pos : getAdjacentPositionsInRadius(startPos, searchRadius)) {
            BlockState state = world.getBlockState(pos);
            if (BlockHelper.isLog(state)) {
                // If we find a log, check if it's surrounded by leaves
                if (isMajoritySurroundedByLeaves(world, pos)) {
                    logsFound.add(pos);
                }
            }
        }

        return logsFound;
    }

    // Check if a log is surrounded by leaves (including diagonals)
    private static boolean isMajoritySurroundedByLeaves(World world, BlockPos logPos) {
        int leafCount = 0;

        for (BlockPos adj : getAdjacentPositionsIncludingDiagonals(logPos)) {
            BlockState state = world.getBlockState(adj);
            if (BlockHelper.isLeaf(state)) {
                leafCount++;
            }
        }

        return leafCount >= 3;
    }

    // Check for trunk logs (vertical alignment) and branch logs (horizontal/angled)
    private static boolean isTrunkLog(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return BlockHelper.isLog(state) && (isVerticallyAligned(world, pos) || isBranchLog(world, pos));
    }

    // Check if a log is vertically aligned (for trunk detection)
    private static boolean isVerticallyAligned(World world, BlockPos pos) {
        return BlockHelper.isLog(world.getBlockState(pos.up())) || BlockHelper.isLog(world.getBlockState(pos.down()));
    }

    // Check if a log is part of a branch (not vertical, but close to trunk)
    private static boolean isBranchLog(World world, BlockPos pos) {
        // Convert Iterable to a Set to be able to use stream()
        Set<BlockPos> adjacentPositions = new HashSet<>();
        for (BlockPos adj : getAdjacentPositions(pos)) {
            adjacentPositions.add(adj);
        }

        return adjacentPositions.stream()
                .anyMatch(adj -> BlockHelper.isLog(world.getBlockState(adj)));
    }

    // Get all 26 positions in a 3x3x3 cube around the given block (including diagonals and vertical diagonals)
    private static Set<BlockPos> getAdjacentPositionsIncludingDiagonals(BlockPos pos) {
        Set<BlockPos> positions = new HashSet<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    // Add only non-central blocks (skip the position itself)
                    if (x != 0 || y != 0 || z != 0) {
                        positions.add(pos.add(x, y, z));
                    }
                }
            }
        }
        return positions;
    }

    // Get adjacent positions in a 3x3 grid (or larger radius if needed)
    private static Iterable<BlockPos> getAdjacentPositions(BlockPos pos) {
        return Set.of(
                pos.up(), pos.down(), pos.north(), pos.south(), pos.west(), pos.east()
        );
    }

    // Get all positions in a radius around the given block
    private static Set<BlockPos> getAdjacentPositionsInRadius(BlockPos pos, int radius) {
        Set<BlockPos> positions = new HashSet<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    positions.add(pos.add(x, y, z));
                }
            }
        }
        return positions;
    }
}