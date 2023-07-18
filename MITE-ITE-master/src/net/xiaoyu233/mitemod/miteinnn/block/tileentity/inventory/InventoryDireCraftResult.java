package net.xiaoyu233.mitemod.miteinnn.block.tileentity.inventory;

import net.minecraft.ItemStack;
import net.minecraft.wc;
import net.xiaoyu233.mitemod.miteinnn.block.tileentity.TileEntityDireCrafting;

public class InventoryDireCraftResult extends wc{
    private TileEntityDireCrafting craft;

    public InventoryDireCraftResult(TileEntityDireCrafting table){
        craft = table;
    }
    @Override
    public ItemStack getStackInSlot(int par1) {
        return craft.getStackInSlot(0);
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        ItemStack stack = craft.getStackInSlot(0);
        if (stack != null) {
            ItemStack itemstack = stack;
            craft.setInventorySlotContents(0, null);
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        craft.setInventorySlotContents(0, par2ItemStack);
    }
}
