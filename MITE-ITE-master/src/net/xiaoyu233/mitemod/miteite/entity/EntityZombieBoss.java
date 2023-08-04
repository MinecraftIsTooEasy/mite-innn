package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;

import java.util.*;

public class EntityZombieBoss extends EntityZombie {
    private Enchantment[] enhanceSpecialBookList = new Enchantment[]{Enchantment.protection, Enchantment.sharpness, Enchantment.fortune, Enchantment.harvesting, Enchantments.EXTEND, Enchantment.efficiency, Enchantment.vampiric, Enchantment.butchering, Enchantment.featherFalling, Enchantment.smite, Enchantment.unbreaking, Enchantment.arrow_recovery, Enchantment.looting, Enchantment.knockback, Enchantment.aquaAffinity, Enchantment.flame, Enchantment.power, Enchantment.silkTouch, Enchantment.endurance, Enchantment.baneOfArthropods, Enchantment.regeneration, Enchantment.thorns, Enchantment.true_flight, Enchantment.speed, Enchantment.stun, Enchantment.fishing_fortune, Enchantment.fireProtection, Enchantment.blastProtection, Enchantment.projectileProtection, Enchantment.free_action, Enchantment.quickness, Enchantment.punch};
    private Enchantment[] nonLevelsBookList = new Enchantment[]{Enchantments.enchantmentFixed, Enchantments.enchantmentChain, Enchantments.EMERGENCY};
    private int thunderTick = 0;
    private int attackedCounter = 200;
    public Map<String, Float> attackDamageMap = new HashMap<String, Float>();

    public EntityZombieBoss(World par1World) {
        super(par1World);
    }

    protected void addRandomEquipment() {
        this.setCurrentItemOrArmor(0, new ItemStack((Item)Items.VIBRANIUM_WAR_HAMMER, 1).randomizeForMob((EntityInsentient)this, true));
        this.setCurrentItemOrArmor(1, new ItemStack((Item)Items.VIBRANIUM_HELMET, 1).randomizeForMob((EntityInsentient)this, true));
        this.setCurrentItemOrArmor(2, new ItemStack((Item)Items.VIBRANIUM_CHESTPLATE, 1).randomizeForMob((EntityInsentient)this, true));
        this.setCurrentItemOrArmor(3, new ItemStack((Item)Items.VIBRANIUM_LEGGINGS, 1).randomizeForMob((EntityInsentient)this, true));
        this.setCurrentItemOrArmor(4, new ItemStack((Item)Items.VIBRANIUM_BOOTS, 1).randomizeForMob((EntityInsentient)this, true));
        this.addPotionEffect(new MobEffect(1, Integer.MAX_VALUE, 0));
        this.addPotionEffect(new MobEffect(5, Integer.MAX_VALUE, 0));
    }

    public void addPotionEffect(MobEffect par1PotionEffect) {
        if (par1PotionEffect.getPotionID() == 1 || par1PotionEffect.getPotionID() == 5) {
            super.addPotionEffect(par1PotionEffect);
        }
    }


//    @Override
//    public void dropContainedItems() {
//
//    }

    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player) {
            ItemStack stack;
            this.broadcastDamage("僵尸BOSS挑战成功");
            MinecraftServer server = MinecraftServer.F();
            for (Object o : server.getConfigurationManager().playerEntityList) {
                int nums;
                EntityPlayer player = (EntityPlayer)o;
                player.triggerAchievement(Achievements.killZombieBoss);
                if (!this.attackDamageMap.containsKey(player.getEntityName()) || (nums = Math.round(this.attackDamageMap.get(player.getEntityName()).floatValue()) / 10) <= 0) continue;
                this.dropItemStack(new ItemStack(Item.diamond, nums));
            }
            float percent = (float)this.nonLevelsBookList.length / ((float)this.enhanceSpecialBookList.length + (float)this.nonLevelsBookList.length);
            if (this.rand.nextFloat() < percent && this.rand.nextInt(5) == 0) {
                Enchantment dropEnchantment = this.nonLevelsBookList[this.rand.nextInt(this.nonLevelsBookList.length)];
                stack = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, dropEnchantment.getNumLevelsForVibranium()));
                if (this.rand.nextInt(2) == 0) {
                    this.dropItemStack(stack);
                }
                return;
            }
            Enchantment dropEnchantment = this.enhanceSpecialBookList[this.rand.nextInt(this.enhanceSpecialBookList.length)];
            stack = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, dropEnchantment.getNumLevelsForVibranium()));
            if (this.rand.nextInt(2) == 0) {
                this.dropItemStack(stack);
            }
        }
//        this.dropItemStack(new ItemStack(Items.voucherZombieBoss, 1));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int rate = Math.min(200, this.worldObj.getDayOfOverworld()) / 20;
        this.setEntityAttribute(GenericAttributes.attackDamage, 6 + rate * 2);
        this.setEntityAttribute(GenericAttributes.maxHealth, 20 + rate * 20);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.3);
    }

    protected void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
    }

    public boolean isComfortableInLava() {
        return true;
    }

    public boolean getCanSpawnHere(boolean perform_light_check) {
        return true;
    }

    public boolean handleLavaMovement() {
        return false;
    }

    public boolean handleWaterMovement() {
        return false;
    }

    public boolean canBeDisarmed() {
        return false;
    }

    public boolean canCatchFire() {
        return false;
    }

    public boolean isPushedByWater() {
        return false;
    }

    public boolean canNeverPickUpItem(Item item) {
        return true;
    }

    public boolean canBeKnockedBack() {
        return false;
    }

    public EntityDamageResult attackEntityFrom(Damage damage) {
        if (damage.getSource().damageType.equals("player") || damage.getSource().damageType.equals("mob")) {
            if (damage.getSource().getResponsibleEntity() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)damage.getSource().getResponsibleEntity();
                player.removePotionEffect(MobEffectList.damageBoost.id);
                player.bossResetDamageBoostCounter = 200;
                this.attackedCounter = 200;
                damage.setAmount(damage.getAmount() / 5.0f);
                EntityDamageResult originDamage = super.attackEntityFrom(damage);
                try {
                    if (this.attackDamageMap.containsKey(player.getEntityName())) {
                        this.attackDamageMap.put(player.getEntityName(), Float.valueOf(this.attackDamageMap.get(player.getEntityName()).floatValue() + originDamage.getAmountOfHealthLost()));
                    } else {
                        this.attackDamageMap.put(player.getEntityName(), Float.valueOf(originDamage.getAmountOfHealthLost()));
                    }
                }
                catch (Exception e) {
                    Minecraft.setErrorMessage((String)"BOSS伤害计算错误分析");
                    Minecraft.setErrorMessage((String)e.getMessage());
                }
                return originDamage;
            }
            return null;
        }
        return null;
    }

    public void broadcastDamage(String stateMessage) {
        MinecraftServer server = MinecraftServer.F();
        Iterator var4 = server.getConfigurationManager().playerEntityList.iterator();
        ArrayList<Map.Entry<String, Float>> list = new ArrayList<Map.Entry<String, Float>>(this.attackDamageMap.entrySet());
        Collections.sort(list, (e1, e2) -> (int)Math.floor(((Float)e2.getValue()).floatValue() - ((Float)e1.getValue()).floatValue()));
        while (var4.hasNext()) {
            Object o = var4.next();
            EntityPlayer player = (EntityPlayer)o;
            for (int i = 0; i < Math.min(list.size(), 5); ++i) {
                player.sendChatToPlayer(ChatMessage.createFromText((String)("--" + stateMessage + "-伤害排名--")));
                player.sendChatToPlayer(ChatMessage.createFromText((String)"第").appendComponent(ChatMessage.createFromText((String)("§6" + (i + 1)))).appendComponent(ChatMessage.createFromText((String)"§r名: ")).appendComponent(ChatMessage.createFromText((String)("§n" + (String)((Map.Entry)list.get(i)).getKey()))).appendComponent(ChatMessage.createFromText((String)"§r - ")).appendComponent(ChatMessage.createFromText((String)("§b" + String.format("%.2f", ((Map.Entry)list.get(i)).getValue())))).appendComponent(ChatMessage.createFromText((String)"§r点伤害")));
            }
        }
    }

    public EntityDamageResult attackEntityAsMob(Entity target) {
        if (target instanceof EntityPlayer) {
            ((EntityPlayer)target).isAttackByBossCounter = 30;
        }
        return super.attackEntityAsMob(target);
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("attackedCounter", (short)this.attackedCounter);
        par1NBTTagCompound.setShort("thunderTick", (short)this.thunderTick);
        NBTTagList nbtTagList = new NBTTagList();
        for (Map.Entry<String, Float> integerEntry : this.attackDamageMap.entrySet()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("Attacker", integerEntry.getKey());
            compound.setFloat("Damage", integerEntry.getValue().floatValue());
            nbtTagList.appendTag((NBTBase)compound);
        }
        par1NBTTagCompound.setTag("AttackDamageMap", (NBTBase)nbtTagList);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.attackedCounter = par1NBTTagCompound.getShort("attackedCounter");
        this.thunderTick = par1NBTTagCompound.getShort("thunderTick");
        if (par1NBTTagCompound.hasKey("AttackDamageMap")) {
            NBTTagList attackCountMap = par1NBTTagCompound.getTagList("AttackDamageMap");
            int count = attackCountMap.tagCount();
            for (int i = 0; i < count; ++i) {
                NBTTagCompound a = (NBTTagCompound)attackCountMap.tagAt(i);
                this.attackDamageMap.put(a.getString("Attacker"), Float.valueOf(a.getFloat("Damage")));
            }
        }
    }

    public void addThunderAttack(EntityPlayer player, float damage) {
        if (player != null) {
            WorldServer var20 = (WorldServer)this.worldObj;
            var20.addWeatherEffect((Entity)new EntityLightning((World)var20, player.posX, player.posY, player.posZ));
            player.attackEntityFrom(new Damage(DamageSource.divine_lightning, damage));
        }
    }

    public boolean setSurroundingPlayersAsTarget() {
        Object targetPlayer;
        List<Object> entities = Arrays.asList(this.getNearbyEntities(16.0f, 16.0f).stream().filter(entity -> entity instanceof EntityPlayer && !((EntityPlayer)entity).isPlayerInCreative()).toArray());
        if (entities.size() > 0 && (targetPlayer = entities.get(this.rand.nextInt(entities.size()))) instanceof EntityPlayer) {
            this.setTarget((EntityLiving)((EntityPlayer)targetPlayer));
            return true;
        }
        return false;
    }

    public void healAndBroadcast() {
        if (this.getHealth() < this.getMaxHealth()) {
            this.heal(this.getMaxHealth());
            this.broadcastDamage("僵尸BOSS挑战失败");
            this.attackDamageMap.clear();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote) {
            ++this.thunderTick;
            if (this.attackedCounter <= 0) {
                this.healAndBroadcast();
            } else {
                --this.attackedCounter;
            }
            EntityLiving target = this.getTarget();
            if (!(target instanceof EntityPlayer)) {
                this.healAndBroadcast();
            }
            if (this.thunderTick % 20 == 0) {
                if (target != null && target instanceof EntityPlayer && ((EntityPlayer)target).isAttackByBossCounter <= 0) {
                    this.addThunderAttack((EntityPlayer)target, 10.0f);
                    if (this.rand.nextInt(9) == 0) {
                        this.setDead();
                    }
                }
                if (this.thunderTick == 60) {
                    this.setSurroundingPlayersAsTarget();
                    this.thunderTick = 0;
                }
            }
        }
    }
}
