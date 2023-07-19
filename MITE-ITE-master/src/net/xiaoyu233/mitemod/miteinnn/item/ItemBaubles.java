package net.xiaoyu233.mitemod.miteinnn.item;

import net.minecraft.*;

public class ItemBaubles extends Item implements IDamageableItem{
    private final MobEffectList potionEffect;
    public ItemBaubles(int id, MobEffectList mobEffectList) {
        super(id,null);
        this.potionEffect = mobEffectList;
        this.setMaxStackSize(1);
        this.setMaxDamage(10240);
        this.setMaterial(Material.mithril);
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
    public int getRepairCost() {
        return 2;
    };
    @Override
    public Material getMaterialForDurability()
    {
        return Material.mithril;
    }
    @Override
    public Material getMaterialForRepairs()
    {
        return Material.mithril;
    }
    @Override
    public int getNumComponentsForDurability() {
        return 1;
    }
}
