package net.xiaoyu233.mitemod.miteinnn.item;

import net.minecraft.*;

public class ItemBaubles extends Item implements IDamageableItem{
    private final MobEffectList potionEffect;
    public ItemBaubles(int id, MobEffectList mobEffectList) {
        super(id,null);
        this.potionEffect = mobEffectList;
        this.setMaxStackSize(1);
        this.setMaxDamage(2048);
        this.setMaterial(Material.iron);
        this.setCreativeTab(CreativeModeTab.tabCombat);
    }

    public MobEffectList getPotionEffectByBaubles() {
        return potionEffect;
    }
    @Override
    public boolean isHarmedByAcid() {
        return false;
    }
    @Override
    public boolean isHarmedByFire() {
        return false;
    }
    @Override
    public boolean isHarmedByLava() {
        return false;
    }

    @Override
    public int getNumComponentsForDurability() {
        return 1;
    }
}
