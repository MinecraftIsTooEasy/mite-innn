package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemBow.class)
public class ItemBowTrans extends Item {
   @Shadow
   private Material reinforcement_material;

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      this.setMaxDamage(reinforcement_material == Materials.vibranium ? 512 : (reinforcement_material == Material.mithril ? 128 : (reinforcement_material == Material.ancient_metal ? 64 : 32)));
   }

   public ItemBowTrans(int id, Material reinforcement_material) {
      super(id, Material.wood, "bows/" + reinforcement_material.toString() + "/");
   }

   @Overwrite
   public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
      if (extended_info && this.reinforcement_material.isMetal()) {
         int bonus = this.reinforcement_material == Material.mithril ? 25 : (this.reinforcement_material == Materials.vibranium ? 75 : 10);
         info.add("");
         info.add(EnumChatFormat.BLUE + Translator.getFormatted("item.tooltip.velocityBonus", new Object[]{bonus}));
      }

      super.addInformation(item_stack, player, info, extended_info, slot);
   }

   @Redirect(method = "<init>",
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/Item;setMaxDamage(I)Lnet/minecraft/Item;"))
   private void redirectSetMaxDamage(){
      if (reinforcement_material == Materials.vibranium) {
         this.setMaxDamage(512);
      } else {
         if (reinforcement_material == Material.mithril) {
            this.setMaxDamage(128);
         } else {
            if (reinforcement_material == Material.ancient_metal) {
               this.setMaxDamage(64);
            } else {
               this.setMaxDamage(32);
            }
         }
      }
   }

   @Overwrite
   public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
      if (EnchantmentManager.hasEnchantment(player.getHeldItemStack(), Enchantments.enchantmentGet)) {
         if (!player.inCreativeMode() && player.inventory.getReadiedArrowForEnchantment() == null) {
            return false;
         } else {
            player.nocked_arrow = player.inventory.getReadiedArrowForEnchantment();
            if (player.nocked_arrow == null && player.inCreativeMode()) {
               player.nocked_arrow = Item.arrowFlint;
            }

            if (player.onServer()) {
               player.sendPacketToAssociatedPlayers((new Packet85SimpleSignal(EnumSignal.nocked_arrow)).setShort(player.nocked_arrow.itemID).setEntityID(player), false);
            }

            player.setHeldItemInUse();
            return true;
         }
      } else{
         if (!player.inCreativeMode() && player.inventory.getReadiedArrow() == null) {
            return false;
         } else {
            player.nocked_arrow = player.inventory.getReadiedArrow();
            if (player.nocked_arrow == null && player.inCreativeMode()) {
               player.nocked_arrow = Item.arrowFlint;
            }

            if (player.onServer()) {
               player.sendPacketToAssociatedPlayers((new Packet85SimpleSignal(EnumSignal.nocked_arrow)).setShort(player.nocked_arrow.itemID).setEntityID(player), false);
            }

            player.setHeldItemInUse();
            return true;
         }
      }
   }
}
