package xyz.mackan.evm.behavior;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.mackan.evm.EnchantedVeinMiner;
import xyz.mackan.evm.util.BlockExplorer;
import xyz.mackan.evm.util.BlockHelper;
import xyz.mackan.evm.util.ToolHelper;
import xyz.mackan.evm.util.TreeDetector;

import java.util.ArrayList;
import java.util.List;

public class VeinMiningBehavior {


    //world, player, pos, state, entity
    public static boolean onBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (player.isCreative()) return true;
        ItemStack tool = player.getMainHandStack();

        if (tool.isEmpty()) return true;

        // Check if the block is an ore and tool is a vein miner pickaxe
        boolean isOre = BlockHelper.isOre(state);
        boolean isVeinPick = ToolHelper.isVeinMinePickaxe(tool);

        // Check if the block is a log and tool is a tree feller axe
        boolean isLog = BlockHelper.isLog(state);
        boolean isTreeAxe = ToolHelper.isTreeFellerAxe(tool);

        // Check if the block is excavatable and tool is an excavator shovel
        boolean isExcavatable = BlockHelper.isExcavatable(state);
        boolean isExcavator = ToolHelper.isExcavatorShovel(tool);



        // Ensure only one tool type is processed at a time
        if ((isOre && isVeinPick) || (isLog && isTreeAxe) || (isExcavatable && isExcavator)) {

            List<BlockPos> vein = new ArrayList<>();

            if (isLog) {
                TreeDetector.Tree tree = TreeDetector.findTree(world, pos);

                vein = tree.logs;

                breakBlocks(world, player, tree.leaves, tool, false, false);

                EnchantedVeinMiner.LOGGER.info("Found tree. Logs:" + tree.logs + ", Leaves: " + tree.leaves + ", attachements: " + tree.attachments);
            } else {
                // TODO: Make this configurable
                vein = BlockExplorer.findAdjacentBlocks(world, pos, 250);
            }

            breakBlocks(world, player, vein, tool, true, true);


            return true;
        }

        return true;
    }

    public static void breakBlocks(World world, PlayerEntity player, List<BlockPos> positions, ItemStack tool,
                                   boolean damageTool, boolean checkSuitable) {
        for (BlockPos pos : positions) {
            if (world.isAir(pos)) continue;

            BlockState blockState = world.getBlockState(pos);
            if (checkSuitable && !tool.isSuitableFor(blockState)) continue;

            // Break the block as if the player mined it
            world.breakBlock(pos, true, player);

            // Reduce durability (1 per block, adjust for enchantments)
            if (damageTool) tool.damage(1, player, (p) -> p.sendToolBreakStatus(player.getActiveHand()));

            // Stop if the tool breaks
            if (tool.isEmpty()) {
                break;
            }
        }
    }
}
