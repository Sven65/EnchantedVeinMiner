package xyz.mackan.evm.enchantment;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.item.ToolItem;
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
        Item item = stack.getItem();

        // Only allow for pickaxes
        return item instanceof PickaxeItem;
    }

    // Server-side logic for vein mining (i.e., break adjacent blocks)
    public void applyVeinMining(World world, BlockPos pos, BlockState state) {
        // Logic to break adjacent blocks
    }
}
