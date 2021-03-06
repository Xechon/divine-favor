package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.entity.projectile.EntityStoneball
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.util.SoundCategory
import net.minecraft.world.World
import java.util.*

class SpellTalismanStoneballThrow(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        throwStoneball(context.world, context.player)
    }

    fun throwStoneball(worldIn: World, playerIn: EntityPlayer): Boolean {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (UtilRandom.random.nextFloat() * 0.4f + 0.8f))
        if (worldIn.isRemote)
            return true
        val entityStoneball = EntityStoneball(worldIn, playerIn)
        entityStoneball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0f, 1.0f, 1.0f)
        worldIn.spawnEntity(entityStoneball)
        return true
    }
}
