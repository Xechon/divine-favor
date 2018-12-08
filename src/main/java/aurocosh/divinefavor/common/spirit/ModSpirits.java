package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.constants.LibSpiritNames;
import aurocosh.divinefavor.common.registry.base.CommonRegistry;
import aurocosh.divinefavor.common.util.helper_classes.TimePeriod;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class ModSpirits {
    public static ModSpirit allfire;
    public static ModSpirit timber;
    private static Map<ResourceLocation, ModSpirit> spirits = new HashMap<>();

    public static void preInit() {
        allfire = register(
                new SpiritBuilder(LibSpiritNames.ALLFIRE)
                        .addActivityPeriod(new TimePeriod(10, 14, 1000))
                        .create()
        );

        timber = register(
                new SpiritBuilder(LibSpiritNames.TIMBER)
                        .addActivityPeriod(new TimePeriod(6, 12, 1000))
                        .create()
        );
    }

    public static ModSpirit register(ModSpirit spirit) {
        spirits.put(spirit.getRegistryName(), spirit);
        CommonRegistry.register(spirit);
        return spirit;
    }
}