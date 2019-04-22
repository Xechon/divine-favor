package aurocosh.divinefavor.common.network.message.client.particles;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.talismans.spell.highlighters.BlockHighlighter;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;

public class MessageParticlesHighlightBlock extends NetworkWrappedClientMessage {
    public int radius;
    public int particles;
    public int dimensionId;
    public BlockPos position;

    public float minShift;
    public float maxShift;
    public Color3f color3f;
    public String blockName;

    public MessageParticlesHighlightBlock() {
    }

    public MessageParticlesHighlightBlock(int radius, int particles, int dimensionId, BlockPos position, float minShift, float maxShift, Color3f color3f, String blockName) {
        this.radius = radius;
        this.particles = particles;
        this.dimensionId = dimensionId;
        this.position = position;
        this.minShift = minShift;
        this.maxShift = maxShift;
        this.color3f = color3f;
        this.blockName = blockName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        Block block = Block.getBlockFromName(blockName);
        if (block == null)
            return;
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        World world = player.world;
        if (dimensionId == world.provider.getDimension())
            BlockHighlighter.highlightOre(world, position, radius, maxShift, minShift, particles, color3f, pos -> world.getBlockState(pos).getBlock() == block);
    }
}