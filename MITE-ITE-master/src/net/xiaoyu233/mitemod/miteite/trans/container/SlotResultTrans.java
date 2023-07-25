package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SlotResult.class)
public class SlotResultTrans extends Slot {
   @Shadow
   private int amountCrafted;
   @Shadow
   private EntityPlayer thePlayer;
   @Shadow
   public CraftingResult crafting_result;

   public SlotResultTrans(IInventory inventory, int slot_index, int display_x, int display_y) {
      super(inventory, slot_index, display_x, display_y);
   }
//   @Shadow
//   @Final
//   private IInventory craftMatrix;
//   @Shadow
//   public int crafting_result_index;
//   @Shadow
//   private void modifyStackForRightClicks(EntityPlayer player) {
//   }
//   @Shadow
//   protected int getMinCraftingResultIndex(EntityPlayer player) {
//      return 1;
//   }
//   @Shadow
//   protected int getMaxCraftingResultIndex(EntityPlayer player) {
//      return 1;
//   }
//
//
//   @Overwrite
//   public void onSlotClicked(EntityPlayer player, int button, Container container) {
//      if (this.getStack() != null) {
//         if (button == 0) {
//            if (!this.canPlayerCraftItem(player)) {
//               return;
//            }
//
//            if (player instanceof ClientPlayer) {
//               ClientPlayer entity_client_player_mp = (ClientPlayer)player;
//               entity_client_player_mp.crafting_proceed = true;
//               entity_client_player_mp.hasCurse(Curse.clumsiness, true);
//            }
//         } else if (button == 1) {
//            this.tryIncrementCraftingResultIndex(player);
//         }
//
//      }
//   }
//
//   @Shadow
//   private void tryIncrementCraftingResultIndex(EntityPlayer player) {
//   }
//   @Shadow
//   public boolean canPlayerCraftItem(EntityPlayer player) {
//      return false;
//   }

//   @Overwrite
//   private void setCraftingResultIndex(int crafting_result_index, EntityPlayer player) {
//      System.out.println("test");
//      if (this.crafting_result != null && this.getHasStack()) {
//         if (this.crafting_result.quality_override == null) {
//            crafting_result_index = MathHelper.clamp_int(crafting_result_index, this.getMinCraftingResultIndex(player), this.getMaxCraftingResultIndex(player));
//         } else {
//            crafting_result_index = this.crafting_result.quality_override.ordinal();
//         }
//
//         if (crafting_result_index != this.crafting_result_index) {
//            this.crafting_result_index = crafting_result_index;
//            player.resetCraftingProgress();
//         }
//
//         this.modifyStackForRightClicks(player);
//      }
////      else {
////         player.clearCrafting();
////      }
//   }

   @Redirect(method = "modifyStackForRightClicks",at = @At(value = "INVOKE",target = "Lnet/minecraft/ItemStack;setQuality(Lnet/minecraft/EnumQuality;)Lnet/minecraft/ItemStack;"))
   private ItemStack redirectRemoveSetQuality(ItemStack caller, EnumQuality quality){
      //Do nothing to remove it!
      return caller;
   }

   @Redirect(method = "canPlayerCraftItem",at = @At(value = "INVOKE",target = "Lnet/minecraft/InventoryCrafting;hasDamagedItem()Z"))
   private boolean removeDamageLimitation(InventoryCrafting caller){
      aah recipe = this.crafting_result.recipe;
      if (recipe instanceof ShapedRecipes){
         return !(((ShapedRecipes) recipe).isExtendsNBT()) && caller.hasDamagedItem();
      }else if (recipe instanceof ShapelessRecipes){
         return !((ShapelessRecipes) recipe).isExtendsNBT() && caller.hasDamagedItem();
      }
      return caller.hasDamagedItem();
   }

//   @Overwrite
//   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
//      int consumption = this.crafting_result.consumption;
//      this.amountCrafted = par2ItemStack.stackSize;
//      this.onCrafting(par2ItemStack);
//      par1EntityPlayer.inventory.addItemStackToInventoryOrDropIt(par2ItemStack.copy());
//      int xp_reclaimed = 0;
//      System.out.println("1213");
//
//
//      for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i) {
//         ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
//         ItemStack itemstack1 = new ItemStack(itemstack.getItem().getContainerItem());
//
//         if (itemstack != null) {
//            this.craftMatrix.decrStackSize(i, 1);
//         }
//
//         if (itemstack1 != null) {
//            if (this.craftMatrix.getStackInSlot(i) == null) {
//               this.craftMatrix.setInventorySlotContents(i, itemstack1);
//            } else if (!this.thePlayer.inventory.addItemStackToInventory(itemstack1)) {
//               this.thePlayer.dropPlayerItemWithRandomChoice(itemstack1, false);
//            }
//         }
//      }
//
////      for(int var3 = 0; var3 < this.craftMatrix.getSizeInventory(); ++var3) {
////         ItemStack var4 = this.craftMatrix.getStackInSlot(var3);
////         if (var4 != null) {
////            Item item = var4.getItem();
////            if (item instanceof ItemCoin) {
////               ItemCoin coin = (ItemCoin)item;
////               xp_reclaimed += coin.getExperienceValue();
////            }
////
////            this.craftMatrix.decrStackSize(var3, consumption);
////            if (var4.getItem().hasContainerItem()) {
////               ItemStack var5 = new ItemStack(var4.getItem().getContainerItem());
////               Item container_item = var5.getItem();
////               if (container_item.getClass() != par2ItemStack.getItem().getClass() && (!var4.getItem().doesContainerItemLeaveCraftingGrid(var4) || !this.thePlayer.inventory.addItemStackToInventory(var5))) {
////                  if (this.craftMatrix.getStackInSlot(var3) == null) {
////                     this.craftMatrix.setInventorySlotContents(var3, var5);
////                  } else {
////                     this.thePlayer.dropPlayerItem(var5);
////                  }
////               }
////            } else if (var4.itemID == Block.workbench.blockID) {
////               this.thePlayer.inventory.addItemStackToInventoryOrDropIt(BlockWorkbench.getBlockComponent(var4.getItemSubtype()));
////            }
////         }
////      }
//
//      if (xp_reclaimed > 0) {
//         par1EntityPlayer.addExperience(xp_reclaimed, true, false);
//      }
//
//   }


   @Overwrite
   protected void onCrafting(ItemStack par1ItemStack) {
      par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
      this.amountCrafted = 0;
      Item item = par1ItemStack.getItem();
      Block block = item instanceof ItemBlock ? ((ItemBlock)item).getBlock() : null;
      if (block instanceof BlockFurnace && ((BlockFurnace)block).isOven()) {
         this.thePlayer.addStat(AchievementList.buildOven, 1);
      } else if (par1ItemStack.itemID == Block.workbench.blockID) {
         Material tool_material = BlockWorkbench.getToolMaterial(par1ItemStack.getItemSubtype());
         if (tool_material.isMetal()) {
            this.thePlayer.addStat(AchievementList.betterTools, 1);
         } else {
            this.thePlayer.addStat(AchievementList.buildWorkBench, 1);
         }
      } else if (block == Block.torchWood) {
         this.thePlayer.addStat(AchievementList.buildTorches, 1);
      } else if (item != Item.pickaxeCopper && item != Item.pickaxeSilver && item != Item.pickaxeGold) {
         if (par1ItemStack.itemID == Block.furnaceIdle.blockID) {
            this.thePlayer.addStat(AchievementList.buildFurnace, 1);
         } else if (par1ItemStack.itemID == Block.furnaceObsidianIdle.blockID) {
            this.thePlayer.triggerAchievement(AchievementList.obsidianFurnace);
         } else if (par1ItemStack.itemID == Block.furnaceNetherrackIdle.blockID) {
            this.thePlayer.triggerAchievement(AchievementList.netherrackFurnace);
         } else if (!(item instanceof ItemHoe) && !(item instanceof ItemMattock)) {
            if (par1ItemStack.itemID == Item.cake.itemID) {
               this.thePlayer.addStat(AchievementList.bakeCake, 1);
            } else if (item instanceof ItemTool && item.getAsTool().isEffectiveAgainstBlock(Block.obsidian, 0)) {
               this.thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
               if (this.thePlayer.worldObj instanceof WorldServer) {
                  this.thePlayer.worldObj.getWorldInfo().fullfillVillageCondition(16, (WorldServer)this.thePlayer.worldObj);
               }

               if (item.getAsTool().isEffectiveAgainstBlock(Block.blockMithril, 0)) {
                  this.thePlayer.triggerAchievement(AchievementList.crystalBreaker);
               }
            } else if (item != Item.hatchetFlint && item != Item.knifeFlint) {
               if (item == Item.clubWood) {
                  this.thePlayer.addStat(AchievementList.buildClub, 1);
               } else if (item instanceof ItemAxe && !(item instanceof ItemHatchet)) {
                  this.thePlayer.addStat(AchievementList.buildAxe, 1);
               } else if (par1ItemStack.itemID != Block.enchantmentTable.blockID && par1ItemStack.itemID != Block.enchantmentTableEmerald.blockID) {
                  if (par1ItemStack.itemID == Block.bookShelf.blockID) {
                     this.thePlayer.addStat(AchievementList.bookcase, 1);
                  } else if (item instanceof ItemShovel && !(item instanceof ItemMattock)) {
                     this.thePlayer.addStat(AchievementList.buildShovel, 1);
                  } else if (item instanceof ItemScythe) {
                     this.thePlayer.addStat(AchievementList.buildScythe, 1);
                  } else if (item instanceof ItemArmor && item.isChainMail()) {
                     this.thePlayer.addStat(AchievementList.buildChainMail, 1);
                  } else if (item instanceof ItemFishingRod) {
                     this.thePlayer.triggerAchievement(AchievementList.fishingRod);
                  } else if (item == Item.flour) {
                     this.thePlayer.triggerAchievement(AchievementList.flour);
                  } else if (item instanceof ItemBowl && (item == Item.bowlSalad || ItemBowl.isSoupOrStew(item))) {
                     this.thePlayer.triggerAchievement(AchievementList.fineDining);
                  } else if (item == Items.VIBRANIUM_INGOT) {
                     this.thePlayer.triggerAchievement(Achievements.vibraniumIngot);
                  } else if (block == Blocks.anvilVibranium) {
                     this.thePlayer.triggerAchievement(Achievements.vibraniumAnvil);
                  } else if (block == Blocks.blockSpawn) {
                     this.thePlayer.triggerAchievement(Achievements.spawnBlock);
                  } else if (item == Items.ringKillerCopper) {
                     this.thePlayer.triggerAchievement(Achievements.ringKillerCopper);
                  } else if (block == Blocks.gemSetting) {
                     this.thePlayer.triggerAchievement(Achievements.gemSettingBlock);
                  } else if (item == Items.itemDynamicCoreIron) {
                     this.thePlayer.triggerAchievement(Achievements.itemDynamicCoreIron);
                  }
               } else {
                  this.thePlayer.addStat(AchievementList.enchantments, 1);
               }
            } else {
               this.thePlayer.addStat(AchievementList.cuttingEdge, 1);
            }
         } else {
            this.thePlayer.addStat(AchievementList.buildHoe, 1);
         }
      } else {
         if (!this.thePlayer.worldObj.isRemote) {
            DedicatedServer.checkForTournamentWinner(this.thePlayer, EnumTournamentType.pickaxe);
         }

         this.thePlayer.addStat(AchievementList.buildPickaxe, 1);
      }
   }

   public int getNumCraftingResults_(EntityPlayer player){
      return this.getNumCraftingResults(player);
   }
   public void setInitialItemStack_(EntityPlayer player, MITEContainerCrafting container){
      this.setInitialItemStack(player, container);
   }
   @Shadow
   protected int getNumCraftingResults(EntityPlayer player) {
      return 1;
   }
   @Shadow
   protected void setInitialItemStack(EntityPlayer player, MITEContainerCrafting container) {
   }
}
