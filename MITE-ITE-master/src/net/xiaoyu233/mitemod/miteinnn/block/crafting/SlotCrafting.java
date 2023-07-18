package net.xiaoyu233.mitemod.miteinnn.block.crafting;

import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;
import net.minecraft.Slot;

public class SlotCrafting extends Slot {
    private final IInventory craftMatrix;
    private EntityPlayer thePlayer;
    private int amountCrafted;
    public SlotCrafting(EntityPlayer player, IInventory iInventory, IInventory inventory, int slot_index, int display_x, int display_y) {
        super(inventory, slot_index, display_x, display_y);
        this.thePlayer = player;
        this.craftMatrix = iInventory;
    }
    @Override
    public boolean isItemValid(ItemStack p_75214_1_)
    {
        return false;
    }
    @Override
    public ItemStack decrStackSize(int par1) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(par1, this.getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack, int par2) {
        this.amountCrafted += par2;
        this.onCrafting(par1ItemStack);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
        this.onCrafting(itemStack);

        for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i) {
            ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);

            if (itemstack1 != null) {
                this.craftMatrix.decrStackSize(i, 1);

                if (itemstack1.getItem().hasContainerItem()) {
                    ItemStack itemstack2 = new ItemStack(itemstack1.getItem().getContainerItem());

                    if (itemstack2 != null && itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage()) {
                        continue;
                    }

                    if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !this.thePlayer.inventory.addItemStackToInventory(itemstack2)) {
                        if (this.craftMatrix.getStackInSlot(i) == null) {
                            this.craftMatrix.setInventorySlotContents(i, itemstack2);
                        } else {
                            this.thePlayer.dropPlayerItemWithRandomChoice(itemstack2, false);
                        }
                    }
                }
            }
        }
    }

}
