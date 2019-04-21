package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionGroundFlow extends ModPotionToggle {
    public PotionGroundFlow() {
        super("ground_flow", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            livingBase.removePotionEffect(ModPotions.ground_flow);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        if (livingBase instanceof EntityPlayer)
            UtilPlayer.setAllowFlying((EntityPlayer) livingBase, false);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.world.isRemote)
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        boolean allowFlying = player.getPosition().getY() <= ConfigSpells.groundFlow.yLimit;
        UtilPlayer.setAllowFlying(player, allowFlying);
        if (!allowFlying)
            livingBase.removePotionEffect(ModPotions.ground_flow);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
