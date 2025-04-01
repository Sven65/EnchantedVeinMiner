package xyz.mackan.evm.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.rmi.registry.Registry;
import java.util.List;

public class RedeemableEnchantedBook extends Item {
    private final String redeemsToId;

    public RedeemableEnchantedBook(String redeemsToId) {
         super(new Item.Settings().maxCount(1));
         this.redeemsToId = redeemsToId;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);

            NbtList storedEnchantments = new NbtList();
            NbtCompound enchantmentEntry = new NbtCompound();

            enchantmentEntry.putString("id", redeemsToId);
            enchantmentEntry.putInt("lvl", 1);

            storedEnchantments.add(enchantmentEntry);

            enchantedBook.getOrCreateNbt().put("StoredEnchantments", storedEnchantments);

            player.setStackInHand(hand, enchantedBook);

            player.sendMessage(Text.literal("Your item transforms!"), true);
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        Identifier enchantmentIdentifier = new Identifier(this.redeemsToId);

        // Fetch the Enchantment from the registry
        Enchantment enchantment = Registries.ENCHANTMENT.get(enchantmentIdentifier);

        if (enchantment != null) {
            // Return the enchantment's name
            String enchantmentName = enchantment.getName(1).getString();
            tooltip.add(Text.literal(String.format("§7Redeems to an enchanted book with the %s enchantment", enchantmentName)));
        }
    }
}
