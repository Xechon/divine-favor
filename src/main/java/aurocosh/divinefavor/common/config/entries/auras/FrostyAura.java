package aurocosh.divinefavor.common.config.entries.auras;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class FrostyAura {
    @Config.Name("Time in snow biome")
    public int timeInSnowBiome = UtilTick.INSTANCE.minutesToTicks(0.6f);
}
