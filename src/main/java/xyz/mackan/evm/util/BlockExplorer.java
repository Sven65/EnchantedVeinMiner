package xyz.mackan.evm.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.mackan.evm.EnchantedVeinMiner;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BlockExplorer {
    public static Set<BlockPos> findAdjacentBlocks(World world, BlockPos startPos)  {
        Set<BlockPos> visited = new HashSet<>();
        Stack<BlockPos> stack = new Stack<>();

        BlockState startBlockState = world.getBlockState(startPos);

        EnchantedVeinMiner.LOGGER.info(String.format("startBlockState %s", startBlockState));


        stack.push(startPos);

        while (!stack.isEmpty()) {
            BlockPos pos = stack.pop();

            if (visited.contains(pos)) {
                continue;
            }

            visited.add(pos);

            for (BlockPos neighbor : getAdjacentPositions(pos)) {
                EnchantedVeinMiner.LOGGER.info(String.format("Checking block at %s", neighbor));
                boolean isSameBlock = world.getBlockState(neighbor).equals(startBlockState);
                EnchantedVeinMiner.LOGGER.info(String.format("Is same block %s", isSameBlock));

                EnchantedVeinMiner.LOGGER.info(String.format("block state %s", world.getBlockState(neighbor)));


                if (isSameBlock) {
                    EnchantedVeinMiner.LOGGER.info(String.format("ADDING at %s", neighbor));

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
