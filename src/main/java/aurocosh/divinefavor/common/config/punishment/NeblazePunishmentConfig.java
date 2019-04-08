package aurocosh.divinefavor.common.config.punishment;

import aurocosh.divinefavor.common.config.IntervalConfig;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

public class NeblazePunishmentConfig {
    @Config.Name("Blocks to melt")
    public IntervalConfig blocksToMelt = new IntervalConfig(3, 7);
    @Config.Name("Blocks to ignite")
    public IntervalConfig blocksToIgnite = new IntervalConfig(20, 60);
    @Config.Name("Mobs to spawn")
    public IntervalConfig mobsToSpawn = new IntervalConfig(3, 5);
    @Config.Name("Spawn radius")
    public int spawnRadius = 10;
    @Config.Name("Ignition time seconds")
    public int ignitionTimeSeconds = 10;
    @Config.Name("Ignition radius")
    public int ignitionRadius = 40;

    @Config.Name("Summoned enemies")
    public Map<String, Double> summonedEnemies = new HashMap<String, Double>() {
        {
            put("minecraft:zombie_pigman", 0.15d);
            put("minecraft:blaze", 0.25d);
            put("minecraft:wither_skeleton", 0.35d);
            put("minecraft:ghast", 0.25d);
            put("minecraft:wither", 0.0001d);
        }
    };
}
