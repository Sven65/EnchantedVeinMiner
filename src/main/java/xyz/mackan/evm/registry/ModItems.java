package xyz.mackan.evm.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.mackan.evm.EnchantedVeinMiner;
import xyz.mackan.evm.items.VeinMinerEnchantedBook;

public class ModItems {
    public static final Item VEIN_MINER_ENCHANTED_BOOK = new VeinMinerEnchantedBook(new Item.Settings());

    public static void registerItems() {
        // Register your custom item
        Registry.register(Registries.ITEM, new Identifier(EnchantedVeinMiner.MOD_ID, "vein_miner_enchanted_book"), VEIN_MINER_ENCHANTED_BOOK);
    }

}
