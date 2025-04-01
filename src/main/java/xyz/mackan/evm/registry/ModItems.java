package xyz.mackan.evm.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.mackan.evm.EnchantedVeinMiner;
import xyz.mackan.evm.items.RedeemableEnchantedBook;


public class ModItems {
    public static final Item VEIN_MINING_REDEEMABLE = new RedeemableEnchantedBook(String.format("%s:vein_mining", EnchantedVeinMiner.MOD_ID));
    public static final Item TREE_FELLER_REDEEMABLE = new RedeemableEnchantedBook(String.format("%s:tree_feller", EnchantedVeinMiner.MOD_ID));
    public static final Item EXCAVATOR_REDEEMABLE = new RedeemableEnchantedBook(String.format("%s:excavator", EnchantedVeinMiner.MOD_ID));
    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(EnchantedVeinMiner.MOD_ID, "vein_mining_redeemable"), VEIN_MINING_REDEEMABLE);
        Registry.register(Registries.ITEM, new Identifier(EnchantedVeinMiner.MOD_ID, "tree_feller_redeemable"), TREE_FELLER_REDEEMABLE);
        Registry.register(Registries.ITEM, new Identifier(EnchantedVeinMiner.MOD_ID, "excavator_redeemable"), EXCAVATOR_REDEEMABLE);
    }
}
