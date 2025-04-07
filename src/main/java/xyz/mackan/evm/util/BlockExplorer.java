package xyz.mackan.evm.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.mackan.evm.EnchantedVeinMiner;

import java.util.*;

public class BlockExplorer {
    public static Set<BlockPos> findAdjacentBlocks(World world, BlockPos startPos, int limit, int jumpDistance) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();

        Block blockType = world.getBlockState(startPos).getBlock();
        visited.add(startPos);
        queue.add(startPos);

        while (!queue.isEmpty() && visited.size() < limit) {
            BlockPos current = queue.poll();

            for (BlockPos neighbor : getAdjacentPositions(current)) {
                if (visited.contains(neighbor)) continue;

                BlockState neighborState = world.getBlockState(neighbor);

                if (neighborState.getBlock() == blockType) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    if (visited.size() >= limit) break;
                } else if (jumpDistance > 0) {
                    BlockPos match = findMatchingBlock(world, neighbor, blockType, visited, jumpDistance);
                    if (match != null && visited.add(match)) {
                        queue.add(match);
                        if (visited.size() >= limit) break;
                    }
                }
            }
        }

        return visited;
    }

    private static BlockPos findMatchingBlock(World world, BlockPos start, Block targetBlock, Set<BlockPos> visited, int maxDistance) {
        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> seen = new HashSet<>();
        queue.add(start);
        seen.add(start);

        int distance = 0;

        while (!queue.isEmpty() && distance < maxDistance) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                BlockPos pos = queue.poll();
                for (BlockPos adj : getAdjacentPositions(pos)) {
                    if (visited.contains(adj) || !seen.add(adj)) continue;

                    BlockState state = world.getBlockState(adj);
                    if (state.getBlock() == targetBlock) return adj;

                    queue.add(adj);
                }
            }
            distance++;
        }

        return null;
    }

    private static Iterable<BlockPos> getAdjacentPositions(BlockPos pos) {
        return Set.of(
                pos.up(), pos.down(), pos.north(), pos.south(), pos.west(), pos.east()
        );
    }
}
