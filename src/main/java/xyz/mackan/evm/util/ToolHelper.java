package xyz.mackan.evm.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import xyz.mackan.evm.registry.ModEnchantments;

public class ToolHelper {
    public static boolean isVeinMinePickaxe(ItemStack tool) {
        return (tool.isIn(ItemTags.PICKAXES)) && EnchantmentHelper.getLevel(ModEnchantments.VEIN_MINING, tool) > 0;
    }

    public static boolean isTreeFellerAxe(ItemStack tool) {
        return (tool.isIn(ItemTags.AXES)) && EnchantmentHelper.getLevel(ModEnchantments.TREE_FELLER, tool) > 0;
    }

    public static boolean isExcavatorShovel(ItemStack tool) {
        return (tool.isIn(ItemTags.SHOVELS)) && EnchantmentHelper.getLevel(ModEnchantments.EXCAVATOR, tool) > 0;
    }
}
