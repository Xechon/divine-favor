package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntitySpellArrow extends EntityArrow {
    private static final DataParameter<Integer> DESPAWN_DELAY = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ENTITY_IGNORE_DELAY = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.VARINT);
    private static final DataParameter<NBTTagCompound> TALISMAN_DATA_COMMON = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.COMPOUND_TAG);

    private static final String TAG_COLOR = "Color";
    private static final String TAG_ARROW_TYPE = "ArrowType";
    private static final String TAG_TALISMAN = "Talisman";
    private static final String TAG_IGNORE_DELAY = "IgnoreDelay";
    private static final String TAG_ANTI_GRAV = "AntiGrav";
    private static final String TAG_TALISMAN_DATA_COMMON = "TalismanDataCommon";
    private static final String TAG_TALISMAN_DATA_SERVER = "TalismanDataServer";
    private double gravityValue = 0.05000000074505806D;

    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.VARINT);
    private static final DataParameter<String> TALISMAN_ID = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> HAS_ANTI_GRAVITY = EntityDataManager.createKey(EntitySpellArrow.class, DataSerializers.BOOLEAN);

    private ItemArrowTalisman talisman;
    private EntityLivingBase shooter;
    private boolean hasAntiGrav = false;
    private int entityIgnoreTicks = 0;
    private NBTTagCompound talismanDataServer = new NBTTagCompound();
    private NBTTagCompound talismanDataCommon = new NBTTagCompound();

    public EntitySpellArrow(World worldIn) {
        super(worldIn);
    }

    public EntitySpellArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntitySpellArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    public void setSpell(ItemArrowTalisman talisman, EntityLivingBase shooter) {
        this.talisman = talisman;
        this.shooter = shooter;
        setColor(talisman.getColor());
        setArrowType(talisman.getArrowType().getValue());
        setTalismanId(talisman.getRegistryName().toString());
    }

    protected void entityInit() {
        super.entityInit();
        dataManager.register(COLOR, -1);
        dataManager.register(TYPE, ArrowType.WOODEN_ARROW.getValue());
        dataManager.register(TALISMAN_ID, "");
        dataManager.register(HAS_ANTI_GRAVITY, true);
        dataManager.register(DESPAWN_DELAY, 1200);
        dataManager.register(ENTITY_IGNORE_DELAY, 0);
        dataManager.register(TALISMAN_DATA_COMMON, new NBTTagCompound());
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();
        if (hasAntiGrav)
            this.motionY += gravityValue;
        if (world.isRemote) {
            if (!inGround)
                spawnPotionParticles(2);
            else if (timeInGround % 5 == 0)
                spawnPotionParticles(1);
            if (talisman != null)
                talisman.spawnParticles(this);
        }
        if (talisman != null)
            talisman.onUpdate(this);
    }

    private void spawnPotionParticles(int particleCount) {
        int i = getColor();
        if (i != -1 && particleCount > 0) {
            double d0 = (double) (i >> 16 & 255) / 255.0D;
            double d1 = (double) (i >> 8 & 255) / 255.0D;
            double d2 = (double) (i >> 0 & 255) / 255.0D;

            for (int j = 0; j < particleCount; ++j)
                world.spawnParticle(EnumParticleTypes.SPELL_MOB, posX + (rand.nextDouble() - 0.5D) * (double) width, posY + rand.nextDouble() * (double) height, posZ + (rand.nextDouble() - 0.5D) * (double) width, d0, d1, d2);
        }
    }


//    protected void arrowHit(EntityLivingBase living) {
//        if (talisman != null && shooter != null)
//            talisman.getSpell().cast(living, shooter, this);
//    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        boolean hit = true;
        if (talisman != null && shooter != null) {
            Entity entity = raytraceResultIn.entityHit;
            EntityLivingBase living = entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null;
            hit = talisman.cast(living, shooter, this, raytraceResultIn.getBlockPos(), raytraceResultIn.sideHit);
            if (hit && talisman.isBreakOnHit())
                setDead();
        }
        if (hit)
            super.onHit(raytraceResultIn);
    }

    @Nullable
    @Override
    protected Entity findEntityOnPath(Vec3d start, Vec3d end) {
        if (entityIgnoreTicks > 0) {
            entityIgnoreTicks--;
            return null;
        }
        else
            return super.findEntityOnPath(start, end);
    }

    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }

    public boolean isInGround() {
        return inGround;
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 0) {
            int i = getColor();

            if (i != -1) {
                double d0 = (double) (i >> 16 & 255) / 255.0D;
                double d1 = (double) (i >> 8 & 255) / 255.0D;
                double d2 = (double) (i >> 0 & 255) / 255.0D;

                for (int j = 0; j < 20; ++j)
                    world.spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, posX + (rand.nextDouble() - 0.5D) * (double) width, posY + rand.nextDouble() * (double) height, posZ + (rand.nextDouble() - 0.5D) * (double) width, d0, d1, d2);
            }
        }
        else
            super.handleStatusUpdate(id);
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (TALISMAN_ID.equals(key))
            talisman = getTalisman();
        else if (DESPAWN_DELAY.equals(key))
            setDespawnDelay(dataManager.get(DESPAWN_DELAY));
        else if (ENTITY_IGNORE_DELAY.equals(key))
            entityIgnoreTicks = dataManager.get(ENTITY_IGNORE_DELAY);
        else if (HAS_ANTI_GRAVITY.equals(key))
            hasAntiGrav = dataManager.get(HAS_ANTI_GRAVITY);
        else if (TALISMAN_DATA_COMMON.equals(key))
            talismanDataCommon = dataManager.get(TALISMAN_DATA_COMMON);
    }

    private ItemArrowTalisman getTalisman() {
        Item item = Item.getByNameOrId(dataManager.get(TALISMAN_ID));
        if (item instanceof ItemArrowTalisman)
            return (ItemArrowTalisman) item;
        return null;
    }

    public void setHasAntiGravity(boolean hasAntiGravity) {
        hasAntiGrav = hasAntiGravity;
        this.dataManager.set(HAS_ANTI_GRAVITY, hasAntiGravity);
    }

    public void setDespawnDelay(int delay) {
        timeInGround = 1200 - delay;
        dataManager.set(DESPAWN_DELAY, delay);
    }

    public void setEntityIgnoreDelay(int delay) {
        entityIgnoreTicks = delay;
        dataManager.set(ENTITY_IGNORE_DELAY, delay);
    }

    public NBTTagCompound getTalismanDataServer() {
        return talismanDataServer;
    }

    public void setTalismanDataServer(NBTTagCompound compound) {
        talismanDataServer = compound == null ? new NBTTagCompound() : compound;
    }

    public NBTTagCompound getTalismanDataCommon() {
        return talismanDataCommon;
    }

    public void setTalismanDataCommon(NBTTagCompound compound) {
        talismanDataCommon = compound == null ? new NBTTagCompound() : compound;
        dataManager.set(TALISMAN_DATA_COMMON, talismanDataCommon);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        boolean collide = talisman == null || talisman.onCollideWithPlayer(this, player);
        if (collide)
            super.onCollideWithPlayer(player);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(TAG_COLOR, getColor());
        compound.setInteger(TAG_ARROW_TYPE, getArrowType().getValue());
        compound.setString(TAG_TALISMAN, getTalismanId());
        compound.setBoolean(TAG_ANTI_GRAV, hasAntiGrav);
        compound.setInteger(TAG_IGNORE_DELAY, entityIgnoreTicks);
        compound.setTag(TAG_TALISMAN_DATA_COMMON, talismanDataCommon);
        compound.setTag(TAG_TALISMAN_DATA_SERVER, talismanDataServer);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        setColor(compound.getInteger(TAG_COLOR));
        setArrowType(compound.getInteger(TAG_ARROW_TYPE));
        String talismanId = compound.getString(TAG_TALISMAN);
        setHasAntiGravity(compound.getBoolean(TAG_ANTI_GRAV));
        setTalismanId(talismanId);
        setDespawnDelay(timeInGround + 1200);
        setEntityIgnoreDelay(compound.getInteger(TAG_IGNORE_DELAY));
        setTalismanDataCommon(compound.getCompoundTag(TAG_TALISMAN_DATA_COMMON));
        setTalismanDataServer(compound.getCompoundTag(TAG_TALISMAN_DATA_SERVER));

        Item item = Item.getByNameOrId(talismanId);
        if (item instanceof ItemArrowTalisman)
            talisman = (ItemArrowTalisman) item;
    }

    public String getTalismanId() {
        return dataManager.get(TALISMAN_ID);
    }

    public ArrowType getArrowType() {
        return ArrowType.get(dataManager.get(TYPE));
    }

    public int getColor() {
        return dataManager.get(COLOR);
    }

    private void setTalismanId(String string) {
        dataManager.set(TALISMAN_ID, string);
    }

    private void setArrowType(int value) {
        dataManager.set(TYPE, ArrowType.get(value).getValue());
    }

    private void setColor(int color) {
        dataManager.set(COLOR, color);
    }
}