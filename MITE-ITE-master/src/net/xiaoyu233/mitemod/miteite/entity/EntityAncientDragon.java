package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.IBossbarEntity;
import net.minecraft.World;

public class EntityAncientDragon extends EntityMonster implements IBossbarEntity {
    public EntityAncientDragon(World par1World) {
        super(par1World);
        this.setSize(6.0F, 10.0F);
    }

//    @Override
//    public boolean canBeDisarmed() {
//        return false;
//    }
//
//    @Override
//    public boolean canCatchFire() {
//        return false;
//    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.followRange, 16.0D);
        this.setEntityAttribute(GenericAttributes.maxHealth, 600.0D);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.23000000417232513D);
        this.setEntityAttribute(GenericAttributes.attackDamage, 15.0D);
    }
}
