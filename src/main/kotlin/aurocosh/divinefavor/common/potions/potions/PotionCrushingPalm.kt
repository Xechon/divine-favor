package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class PotionCrushingPalm : ModPotionToggleLimited("crushing_palm", 0x7FB8A4) {
    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }
}

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
object CrushingPalm {
    @SubscribeEvent
    fun onPlayerLeftClickBlock(event: PlayerInteractEvent.LeftClickBlock) {
        val world = event.world
        if (world.isRemote)
            return

        val player = event.entityPlayer
        if (!player.isPotionActive(ModPotions.crushing_palm))
            return

        val pos = event.pos
        val state = world.getBlockState(pos)
        val block = state.block
        if (!block.isToolEffective("pickaxe", state))
            return

        val spiritData = player.divinePlayerData.spiritData
        val talisman = ModPotions.crushing_palm.talisman
        val spirit = talisman.spirit
        if (!spiritData.consumeFavor(spirit.id, talisman.favorCost))
            return

        MessageSyncFavor(spirit, spiritData).sendTo(player)

        val stack = player.heldItemMainhand
        UtilBlock.removeBlock(player, world, stack, pos, true, false, true)
    }
}