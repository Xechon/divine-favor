package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.muliblock.IMultiblockController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBoneKey extends ModItem {
    public ItemBoneKey() {
        super("bone_key","bone_key");
        setMaxStackSize(1);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote)
            return EnumActionResult.PASS;
        if(hand == EnumHand.OFF_HAND)
            return EnumActionResult.PASS;

        TileEntity entity = worldIn.getTileEntity(pos);
        if(!(entity instanceof IMultiblockController))
            return EnumActionResult.PASS;

        IMultiblockController controller = (IMultiblockController) entity;
        controller.tryToFormMultiBlock();
        return EnumActionResult.SUCCESS;
    }
}