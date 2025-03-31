package xyz.mackan.evm.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import xyz.mackan.evm.EnchantedVeinMiner;

public class VeinMinerEnchantment extends Enchantment {
    public VeinMinerEnchantment(Rarity weight) {
        super(weight, EnchantmentTarget.DIGGER, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }


    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        EnchantedVeinMiner.LOGGER.info(String.format("Is item pickaxe? %s", stack.isIn(ItemTags.PICKAXES)));
        return stack.isIn(ItemTags.PICKAXES);
    }
}
