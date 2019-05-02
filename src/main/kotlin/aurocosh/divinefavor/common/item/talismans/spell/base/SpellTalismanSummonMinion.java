package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLiving;

import java.util.EnumSet;

public class SpellTalismanSummonMinion<T extends EntityLiving & IMinion> extends SpellTalismanSummonEntity<T> {
    public SpellTalismanSummonMinion(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options, Class<? extends T> clazz) {
        super(name, spirit, favorCost, options, clazz);
    }

    @Override
    protected void postProcessEntity(T entityLiving, TalismanContext context) {
        entityLiving.getMinionData().setOwner(context.getPlayer());
    }
}