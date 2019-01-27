package aurocosh.divinefavor.common.item.talismans.arrow.base;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.util.UtilCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

public class ArrowTalismanCurse extends ItemArrowTalisman {
    private final ModPotion potion;
    private final int duration;

    public ArrowTalismanCurse(String name, ModFavor favor, int favorCost, int color, ModPotion potion, int duration) {
        super(name, favor, favorCost, color, 0, ArrowOptions.REQUIRES_TARGET, ArrowType.CURSED_ARROW);
        this.potion = potion;
        this.duration = duration;
    }

    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        UtilCurses.applyCurse(target, shooter, new ModEffect(potion, duration));
    }
}