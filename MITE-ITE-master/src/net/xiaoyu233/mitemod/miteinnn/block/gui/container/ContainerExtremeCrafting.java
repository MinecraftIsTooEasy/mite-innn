package net.xiaoyu233.mitemod.miteinnn.block.gui.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteinnn.block.crafting.ExtremeCraftingManager;
import net.xiaoyu233.mitemod.miteinnn.block.crafting.SlotCrafting;
import net.xiaoyu233.mitemod.miteinnn.block.tileentity.TileEntityDireCrafting;
import net.xiaoyu233.mitemod.miteinnn.block.tileentity.inventory.InventoryDireCraftResult;
import net.xiaoyu233.mitemod.miteinnn.block.tileentity.inventory.InventoryDireCrafting;
import net.xiaoyu233.mitemod.miteite.block.Blocks;

public class ContainerExtremeCrafting extends Container {
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;

    public ContainerExtremeCrafting(EntityPlayer player, TileEntityDireCrafting table) {
        super(player);
        this.worldObj = table.getWorldObj();
        this.posX = table.xCoord;
        this.posY = table.yCoord;
        this.posZ = table.zCoord;
//        System.out.println("ContainerExtremeCrafting ；" + table);
//        System.out.println("ContainerExtremeCrafting");
        craftMatrix = new InventoryDireCrafting(this, table);
        craftResult = new InventoryDireCraftResult(table);
        this.addSlotToContainer(new SlotCrafting(player, this.craftMatrix, this.craftResult, 0, 210, 80));
        int wy;
        int ex;

        for (wy = 0; wy < 9; ++wy) {
            for (ex = 0; ex < 9; ++ex) {
                this.addSlotToContainer(new Slot(this.craftMatrix, ex + wy * 9, 12 + ex * 18, 8 + wy * 18));
            }
        }

        for (wy = 0; wy < 3; ++wy) {
            for (ex = 0; ex < 9; ++ex) {
                this.addSlotToContainer(new Slot(player.inventory, ex + wy * 9 + 9, 39 + ex * 18, 174 + wy * 18));
            }
        }

        for (ex = 0; ex < 9; ++ex) {
            this.addSlotToContainer(new Slot(player.inventory, ex, 39 + ex * 18, 232));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return this.worldObj.getBlock(this.posX, this.posY, this.posZ) == Blocks.direCrafting && player.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
    }

    @Override
    public void onCraftMatrixChanged(IInventory matrix) {
        this.craftResult.setInventorySlotContents(0, ExtremeCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNumber);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotNumber == 0) {
                if (!this.mergeItemStack(itemstack1, 82, 118, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (slotNumber >= 82 && slotNumber < 109) {
                if (!this.mergeItemStack(itemstack1, 109, 118, false)) {
                    return null;
                }
            } else if (slotNumber >= 109 && slotNumber < 118) {
                if (!this.mergeItemStack(itemstack1, 82, 109, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 82, 118, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}
