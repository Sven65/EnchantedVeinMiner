package xyz.mackan.evm;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.mackan.evm.behavior.VeinMiningBehavior;
import xyz.mackan.evm.registry.ModEnchantments;
import xyz.mackan.evm.registry.ModItems;

public class EnchantedVeinMiner implements ModInitializer {
	public static final String MOD_ID = "enchanted-vein-miner";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModEnchantments.registerEnchantments();
		ModItems.registerItems();

		PlayerBlockBreakEvents.BEFORE.register(VeinMiningBehavior::onBlockBreak);
	}
}