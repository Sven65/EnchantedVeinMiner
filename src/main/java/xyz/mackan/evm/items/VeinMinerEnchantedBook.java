package xyz.mackan.evm.items;

import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import xyz.mackan.evm.registry.ModEnchantments;

public class VeinMinerEnchantedBook extends Item {
    public VeinMinerEnchantedBook(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack bookStack = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(bookStack, new EnchantmentLevelEntry(ModEnchantments.VEIN_MINING, 1));
        return bookStack;
    }
}
