package xyz.mackan.evm.behavior;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.mackan.evm.EnchantedVeinMiner;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class VeinMiningBehavior {

    public static boolean isOre(BlockState state) {
        // TODO: Change this to the correct tag
        return state.getBlock() == Registries.BLOCK.get(new Identifier("minecraft", "coal_ore"));
        //return state.isIn(BlockTags.COAL_ORES);  // Checks if the block is an ore (part of Minecraft's ore tag)
    }

    //world, player, pos, state, entity
    public static void onBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        EnchantedVeinMiner.LOGGER.info("Block broken");

        // Only trigger the behavior if the player is not in creative mode
        if (player.isCreative()) return;

        // Get the block type
        Block block = state.getBlock();
        if (block == null) return;

        EnchantedVeinMiner.LOGGER.info("Mining vein");


        // Start vein mining from the broken block
        mineOreVein(player, world, pos, block);
    }

    public static void mineOreVein(PlayerEntity player, World world, BlockPos startPos, Block block) {
        // Use a set to track all the positions we've already mined
        Set<BlockPos> minedBlocks = new HashSet<>();
        // Stack for DFS
        Stack<BlockPos> toMine = new Stack<>();
        toMine.push(startPos);

        while (!toMine.isEmpty()) {
            EnchantedVeinMiner.LOGGER.info("Trying to add block...");

            BlockPos currentPos = toMine.pop();
            if (minedBlocks.contains(currentPos)) continue; // Skip if already mined

            BlockState state = world.getBlockState(currentPos);

            EnchantedVeinMiner.LOGGER.info(String.format("Block state %s", state));

            if (state.getBlock() == block && isOre(state)) {
                // Mine the connected ore block
                world.breakBlock(currentPos, true);
                minedBlocks.add(currentPos);

                EnchantedVeinMiner.LOGGER.info(String.format("Adding block %s", currentPos));


                // Add neighboring blocks to the stack
                for (Direction direction : Direction.values()) {
                    BlockPos neighborPos = currentPos.offset(direction);
                    if (!minedBlocks.contains(neighborPos)) {
                        toMine.push(neighborPos);
                    }
                }
            }
        }
    }
}
