//package net.xiaoyu233.mitemod.miteite.trans.container;
//
//
//import net.minecraft.*;
//import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//import org.spongepowered.asm.mixin.Shadow;
//
//@Mixin(MITEContainerCrafting.class)
//public class MITEContainerCraftingMixin extends Container {
//
//    public MITEContainerCraftingMixin(EntityPlayer player) {
//        super(player);
//    }
//
////    public boolean isNEW(){
////        return false;
////    }
//
//    @Overwrite
//    public void onCraftMatrixChanged(IInventory par1IInventory) {
////        this.current_crafting_result = CraftingManager.getInstance().findMatchingRecipe(this.craft_matrix, this.player.worldObj, this.player);
////        if (!CraftingResult.haveEquivalentItemStacks(this.current_crafting_result, this.previous_crafting_result)) {
////            this.player.clearCrafting();
////            this.getCraftingSlot().setInitialItemStack_(this.player, ReflectHelper.dyCast(this));
////        }
////        this.previous_crafting_result = this.current_crafting_result;
//
//
//    }
//
////    @Overwrite
////    public void onContainerClosed(EntityPlayer entity_player) {
////            this.player.clearCrafting();
////            super.onContainerClosed(entity_player);
////            if (!this.world.isRemote) {
////                for(int var2 = 0; var2 < this.craft_matrix.getSizeInventory(); ++var2) {
////                    ItemStack var3 = this.craft_matrix.getStackInSlotOnClosing(var2);
////                    if (var3 != null) {
////                        entity_player.dropPlayerItem(var3);
////                    }
////                }
////            }
////            this.craft_result.setInventorySlotContents(0, (ItemStack)null);
////    }
//
//
//    @Shadow
//    private SlotResult getCraftingSlot() {
//        return null;
//    }
//    @Shadow
//    public CraftingResult current_crafting_result;
//    @Shadow
//    private CraftingResult previous_crafting_result;
//    @Shadow
//    public InventoryCrafting craft_matrix;
//    @Shadow
//    public IInventory craft_result;
//
//    @Shadow
//    public boolean canInteractWith(EntityPlayer entityPlayer) {
//        return false;
//    }
//}
