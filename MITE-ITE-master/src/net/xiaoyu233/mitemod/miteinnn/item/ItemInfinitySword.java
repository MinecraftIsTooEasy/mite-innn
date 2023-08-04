package net.xiaoyu233.mitemod.miteinnn.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteinnn.util.ColorText;
import net.xiaoyu233.mitemod.miteite.item.Materials;

import java.util.List;

public class ItemInfinitySword extends ItemSword {
    public ItemInfinitySword(int par1) {
        super(par1, Materials.infinity);
    }

    @Override
    public Class[] getSimilarClasses() {
        return ItemTool.weapon_classes;
    }

    @Override
    public int getNumComponentsForDurability() {
        return 24;
    }

    @Override
    public float getBaseDamageVsEntity() {
        return 2147483647.0F;
    }

    @Override
    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        info.add(ColorText.makeSANIC("无穷尽的伤害"));
    }

}