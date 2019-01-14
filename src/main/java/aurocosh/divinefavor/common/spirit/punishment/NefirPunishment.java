package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.lib.DistributedRandomList;
import aurocosh.divinefavor.common.lib.SuccessCounter;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.MultiBlockInstance;
import aurocosh.divinefavor.common.potions.base.effect.PotionEffectCurse;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class NefirPunishment extends SpiritPunishment {
    public static int IGNITION_TIME_SECONDS = 20;

    public static int MOBS_TO_SUMMON = 4;
    public static int SPAWN_ATTEMPTS = MOBS_TO_SUMMON * 10;
    private final int SPAWN_RADIUS = 10;

    public static int MIN_BLOCKS_TO_MELT = 3;
    public static int MAX_BLOCKS_TO_MELT = 3;

    private static final DistributedRandomList<Class<? extends EntityLiving>> possibleEnemies = new DistributedRandomList<>();

    static {
        possibleEnemies.add(EntityPigZombie.class, 0.15d);
        possibleEnemies.add(EntityBlaze.class, 0.25d);
        possibleEnemies.add(EntityWitherSkeleton.class, 0.35d);
        possibleEnemies.add(EntityGhast.class, 0.25d);
        possibleEnemies.add(EntityWither.class, 0.0001d);
    }

    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        player.setFire(IGNITION_TIME_SECONDS);
        smeltPartsOfAltar(player, world, instance);
        spawnEnemies(player, world);
    }

    private void smeltPartsOfAltar(EntityPlayer player, World world, MultiBlockInstance instance) {
        int blocksToSmelt = UtilRandom.nextInt(MIN_BLOCKS_TO_MELT, MAX_BLOCKS_TO_MELT);
        List<BlockPos> solidsPositions = Vector3i.convert(new ArrayList<>(instance.positionsOfSolids));
        List<BlockPos> selectedPositions = UtilRandom.selectRandom(solidsPositions, blocksToSmelt);
        for (BlockPos position : selectedPositions) {
            if (UtilBlock.canBreakBlock(player, world, position, false)) {
                IBlockState oldState = world.getBlockState(position);
                IBlockState newState = Blocks.LAVA.getDefaultState();
                world.setBlockState(position, newState);
                world.notifyBlockUpdate(position, oldState, newState, 3);
            }
        }
    }

    private void spawnEnemies(EntityPlayer player, World world) {
        BlockPos playerPosition = player.getPosition();
        SuccessCounter counter = new SuccessCounter(MOBS_TO_SUMMON, SPAWN_ATTEMPTS);
        while (counter.attemptOnceMore())
            if (spawnNetherMob(world, playerPosition))
                counter.registerSuccess();
    }

    private boolean spawnNetherMob(World world, BlockPos pos) {
        BlockPos spawnPos = UtilCoordinates.getRandomNeighbour(pos, SPAWN_RADIUS, 0, SPAWN_RADIUS);
        spawnPos = UtilCoordinates.findPlaceToSpawn(spawnPos, world, SPAWN_RADIUS);
        if (spawnPos == null)
            return false;

        try {
            Class<? extends EntityLiving> clazz = possibleEnemies.getRandom();
            Constructor<? extends EntityLiving> constructor = clazz.getConstructor(World.class);
            EntityLiving entityLiving = constructor.newInstance(world);
            entityLiving.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0.0F);
            world.spawnEntity(entityLiving);
        }
        catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}