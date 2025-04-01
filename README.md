# EnchantedVeinMiner

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.20.x-brightgreen)
![Mod Loader](https://img.shields.io/badge/Loader-Fabric-orange)
![License](https://img.shields.io/badge/License-MIT-blue)


EnchantedVeinMiner is a lightweight Fabric mod that adds vein mining capabilities through an enchantment. Mine entire veins of ore with a single block break when using tools with the Vein Mining enchantment!

1. **Vein Mining** - For pickaxes
   - Mine entire veins of ore at once
   - Works on all blocks with the Ore tag

2. **Tree Feller** - For axes
   - Cut down entire trees at once
   - Works on all blocks with the Log tag

3. **Excavator** - For shovels
   - Clear large areas of clay and diggable ores
   - Works on clay and other blocks with the Ore tag

### Dependencies

- Fabric API (required)

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for the appropriate Minecraft version
2. Download the latest version of EnchantedVeinMiner from the [Releases](https://github.com/xyz/mackan/enchanted-vein-miner/releases) page
3. Place the downloaded .jar file into your `mods` folder
4. Launch Minecraft with the Fabric profile


## How to Obtain

These enchantments can be obtained through:

- Enchanting table (random selection)
- Enchanted books from loot, villager trading, or fishing
- Craftable redeemable enchanted books

## How It Works

1. Enchant your tool with the appropriate enchantment
2. Mine a block as normal
3. All connected blocks of the same type will be mined automatically
4. Each mined block consumes 1 durability from your tool


## For Developers

If you want to include EnchantedVeinMiner in your mod pack or develop an addon:

```gradle
repositories {
    maven { url "https://maven.example.com/repository/xyz/mackan" }
}

dependencies {
    modImplementation "xyz.mackan:enchanted-vein-miner:1.0.0"
}
```

## License

This mod is available under the MIT License. See the LICENSE file for details.

## Support

If you encounter any issues or have suggestions:
- Open an issue on our [GitHub Issue Tracker](https://github.com/xyz/mackan/enchanted-vein-miner/issues)
---

<div align="center">
<i>Mining made magical!</i>
</div>