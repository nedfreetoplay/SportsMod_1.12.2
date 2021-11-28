package net.sports.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.lwjgl.util.vector.Quaternion;

public class EntityBall extends Entity {

    private Quaternion rotation;
    protected float gravity;
    protected boolean dead;

    public EntityBall(World worldIn) {
        super(worldIn);
        this.preventEntitySpawning = true;
    }

    public EntityBall(World worldIn, double x, double y, double z) {
        super(worldIn);
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.preventEntitySpawning = true;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    @Override
    protected void entityInit() {
        setEntityBoundingBox(new AxisAlignedBB(0, 0, 0, 1, 1, 1));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }


}
