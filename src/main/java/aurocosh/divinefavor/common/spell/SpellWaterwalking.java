package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.Spell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.effect.ModPotionEffects;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import net.minecraft.potion.PotionEffect;

public class SpellWaterwalking extends Spell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellWaterwalking() {
        super(LibSpellNames.WATERWALKING);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.playerIn.getEntityWorld().isRemote)
        //    return true;

        PotionEffect potioneffect = new PotionEffect(ModPotionEffects.waterwalk, NORMAL);
        context.playerIn.addPotionEffect(potioneffect);

        return true;
    }

    @Override
    protected boolean claimCost(SpellContext context) {
        return true;
    }
}