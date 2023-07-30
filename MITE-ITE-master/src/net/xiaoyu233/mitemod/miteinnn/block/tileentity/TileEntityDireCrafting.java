package net.xiaoyu233.mitemod.miteinnn.block.tileentity;

import net.minecraft.*;

import java.util.Arrays;

public class TileEntityDireCrafting extends TileEntity implements IInventory {
    private ItemStack result;
    private final ItemStack[] matrix = new ItemStack[81];

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.result = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Result"));
        for(int x = 0;x < matrix.length;x++){
            matrix[x] = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Craft" + x));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if(result != null) {
            NBTTagCompound produce = new NBTTagCompound();
            result.writeToNBT(produce);
            tag.setTag("Result", produce);
        } else {
            tag.removeTag("Result");
        }


        for(int x = 0;x < matrix.length;x++){
            if(matrix[x] != null){
                NBTTagCompound craft = new NBTTagCompound();
                matrix[x].writeToNBT(craft);
                tag.setTag("Craft" + x, craft);
            }
            else
                tag.removeTag("Craft" + x);
        }
    }

    @Override
    public int getSizeInventory() {
        return matrix.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot){
        if(slot == 0){
            return result;
        } else if(slot <= matrix.length){
            return matrix[slot - 1];
        } else {
            return null;
        }

    }

    @Override
    public ItemStack decrStackSize(int slot, int decrement){
        if(slot == 0){
            if(result != null){
                for(int x = 1;x <= matrix.length;x++){
                    decrStackSize(x, 1);
                }
                if(result.stackSize <= decrement) {
                    ItemStack craft = result;
                    result = null;
                    return craft;
                }
                ItemStack split = result.splitStack(decrement);
                if(result.stackSize <= 0)
                    result = null;
                return split;
            }
            else
                return null;
        } else if(slot <= matrix.length){
            if(matrix[slot - 1] != null){
                if(matrix[slot - 1].stackSize <= decrement){
                    ItemStack ingredient = matrix[slot - 1];
                    matrix[slot - 1] = null;
                    return ingredient;
                }
                ItemStack split = matrix[slot - 1].splitStack(decrement);
                if(matrix[slot - 1].stackSize <= 0)
                    matrix[slot - 1] = null;
                return split;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.matrix[par1] != null) {
            ItemStack stack = this.matrix[par1];
            this.matrix[par1] = null;
            return stack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack){
        if(slot == 0){
            result = stack;
        } else if(slot <= matrix.length){
            matrix[slot - 1] = stack;
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return false;
    }

    @Override
    public void destroyInventory() {
        Arrays.fill(this.matrix, null);
    }
}
