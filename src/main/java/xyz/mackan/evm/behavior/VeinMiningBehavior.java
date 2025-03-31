package xyz.mackan.evm.behavior;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.mackan.evm.registry.ModEnchantments;
import xyz.mackan.evm.util.BlockExplorer;

import java.util.Set;

public class VeinMiningBehavior {

    public static boolean isOre(BlockState state) {
        return state.isIn(ConventionalBlockTags.ORES);
    }

    public static boolean isLog(BlockState state) {
        return state.isIn(BlockTags.LOGS);
    }

    public static boolean isExcavatable(BlockState state) {
        return isOre(state) || state.getBlock() == Blocks.CLAY;
    }

    public static boolean isVeinMinePickaxe(ItemStack tool) {
        return (tool.isIn(ItemTags.PICKAXES)) && EnchantmentHelper.getLevel(ModEnchantments.VEIN_MINING, tool) > 0;
    }

    public static boolean isTreeFellerAxe(ItemStack tool) {
        return (tool.isIn(ItemTags.AXES)) && EnchantmentHelper.getLevel(ModEnchantments.TREE_FELLER, tool) > 0;
    }

    public static boolean isExcavatorShovel(ItemStack tool) {
        return (tool.isIn(ItemTags.SHOVELS)) && EnchantmentHelper.getLevel(ModEnchantments.EXCAVATOR, tool) > 0;
    }

    //world, player, pos, state, entity
    public static boolean onBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (player.isCreative()) return true;

        ItemStack tool = player.getMainHandStack();

        if (tool.isEmpty()) return true;

        if (isOre(state) && !isVeinMinePickaxe(tool)) return true;
        if (isLog(state) && !isTreeFellerAxe(tool)) return true;
        if (isExcavatable(state) && !isExcavatorShovel(tool)) return true;

        if (!isOre(state) && !isLog(state) && !isExcavatable(state)) return true;


        Set<BlockPos> vein = BlockExplorer.findAdjacentBlocks(world, pos);

        breakBlocks(world, player, vein, tool);

        return true;
    }

    public static void breakBlocks(World world, PlayerEntity player, Set<BlockPos> positions, ItemStack tool) {
        for (BlockPos pos : positions) {
            if (world.isAir(pos)) continue;

            BlockState blockState = world.getBlockState(pos);
            if (!tool.isSuitableFor(blockState)) continue;

            // Break the block as if the player mined it
            world.breakBlock(pos, true, player);

            // Reduce durability (1 per block, adjust for enchantments)
            tool.damage(1, player, (p) -> p.sendToolBreakStatus(player.getActiveHand()));

            // Stop if the tool breaks
            if (tool.isEmpty()) {
                break;
            }
        }
    }
}
