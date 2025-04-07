package xyz.mackan.evm.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class BlockExplorer {
    public static Set<BlockPos> findAdjacentBlocks(World world, BlockPos startPos, int limit)  {
        Set<BlockPos> visited = new HashSet<>();
        Stack<BlockPos> stack = new Stack<>();

        BlockState startBlockState = world.getBlockState(startPos);
        stack.push(startPos);

        while (!stack.isEmpty()) {
            BlockPos pos = stack.pop();

            if (visited.contains(pos)) {
                continue;
            }

            visited.add(pos);

            for (BlockPos neighbor : getAdjacentPositions(pos)) {
                if (visited.size() >= limit) {
                    break;
                }

                if (world.getBlockState(neighbor).equals(startBlockState)) {
                    stack.push(neighbor);
                }
            }
        }

        return visited;
    }

    private static Iterable<BlockPos> getAdjacentPositions(BlockPos pos) {
        return Set.of(
                pos.up(), pos.down(), pos.north(), pos.south(), pos.west(), pos.east()
        );
    }
}
