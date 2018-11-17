package aurocosh.divinefavor.block;

import aurocosh.divinefavor.ModBlocks;
import net.minecraft.block.material.Material;

public class BlockFastFurnace extends DivineFavorBlock {
    public BlockFastFurnace() {
        super(ModBlocks.FAST_FURNACE,Material.IRON);
        setHarvestLevel("pickaxe",1);
    }
}
