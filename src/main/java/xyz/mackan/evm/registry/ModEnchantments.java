package xyz.mackan.evm.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.mackan.evm.EnchantedVeinMiner;
import xyz.mackan.evm.enchantment.ExcavatorEnchantment;
import xyz.mackan.evm.enchantment.TreeFellerEnchantment;
import xyz.mackan.evm.enchantment.VeinMinerEnchantment;

public class ModEnchantments {
    public static final Enchantment VEIN_MINING = new VeinMinerEnchantment(Enchantment.Rarity.COMMON);
    public static final Enchantment TREE_FELLER = new TreeFellerEnchantment(Enchantment.Rarity.COMMON);
    public static final Enchantment EXCAVATOR = new ExcavatorEnchantment(Enchantment.Rarity.COMMON);

    public static void registerEnchantments() {
        Registry.register(Registries.ENCHANTMENT, new Identifier(EnchantedVeinMiner.MOD_ID, "vein_mining"), VEIN_MINING);
        Registry.register(Registries.ENCHANTMENT, new Identifier(EnchantedVeinMiner.MOD_ID, "tree_feller"), TREE_FELLER);
        Registry.register(Registries.ENCHANTMENT, new Identifier(EnchantedVeinMiner.MOD_ID, "excavator"), EXCAVATOR);
    }
}
