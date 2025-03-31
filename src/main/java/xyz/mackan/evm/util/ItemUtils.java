package xyz.mackan.evm.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import xyz.mackan.evm.registry.ModEnchantments;

import java.util.HashMap;
import java.util.Map;

public class ItemUtils {
    public static ItemStack createVeinMinerEnchantmentBook() {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);

        Map<Enchantment, Integer> enchantments = new HashMap<>();

        // Add your custom enchantment with a level
        enchantments.put(ModEnchantments.VEIN_MINING, 1);

        EnchantmentHelper.set(enchantments, book);

        return book;
    }
}
