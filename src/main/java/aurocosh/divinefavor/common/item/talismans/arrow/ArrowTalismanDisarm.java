package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ArrowTalismanDisarm extends ItemArrowTalisman {
    public ArrowTalismanDisarm(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            moveStackToMainInventory(player, player.inventory.currentItem);
            moveStackToMainInventory(player, InventoryIndexes.Offhand.getValue());
        }
        return true;
    }

    private void moveStackToMainInventory(EntityPlayer player, int slot) {
        ItemStack stack = player.inventory.getStackInSlot(slot);
        if (stack.isEmpty())
            return;
        SlotData slotData = UtilPlayer.findStackInMainInventory(player, ItemStack::isEmpty);
        if (slotData.isValid())
            UtilPlayer.swapStacks(player, slot, slotData.slotIndex);
    }
}
