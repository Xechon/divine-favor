package aurocosh.divinefavor.common.item.talismans.common;

import aurocosh.divinefavor.common.item.talismans.arrow.*;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowTalismanCurse;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowTalismanCurseCurseTigger;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ItemArrowTalisman;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.util.UtilTick;

import java.awt.*;

public class ModArrowTalismans {
    public static ItemArrowTalisman anti_gravity_arrow;
    public static ItemArrowTalisman blink_arrow;
    public static ItemArrowTalisman fiery_mark;
    public static ItemArrowTalisman hand_swap;
    public static ItemArrowTalisman nether_swap;
    public static ItemArrowTalisman roots;
    public static ItemArrowTalisman zero_g_arrow;

    public static void preInit() {
        anti_gravity_arrow = ModRegistries.items.register(new ArrowTalismanAntiGravityArrow());
        blink_arrow = ModRegistries.items.register(new ArrowTalismanBlinkArrow());
        fiery_mark = ModRegistries.items.register(new ArrowTalismanCurseCurseTigger("fiery_mark", 10, Color.red.getRGB(), ModPotions.fiery_mark, UtilTick.secondsToTicks(10)));
        hand_swap = ModRegistries.items.register(new ArrowTalismanHandSwap());
        nether_swap = ModRegistries.items.register(new ArrowTalismanNetherSwap());
        roots = ModRegistries.items.register(new ArrowTalismanCurse("roots", 10, Color.green.getRGB(), ModPotions.roots, UtilTick.secondsToTicks(15)));
        zero_g_arrow = ModRegistries.items.register(new ArrowTalismanZeroGArrow());

        ModMappers.talismans.register(ModRegistries.items.getValues(ItemArrowTalisman.class));
    }
}