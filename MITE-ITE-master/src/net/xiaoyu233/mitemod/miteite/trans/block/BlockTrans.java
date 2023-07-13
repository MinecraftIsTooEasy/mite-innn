package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockColorful;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.lang.ref.Reference;

@Mixin(Block.class)
public abstract class BlockTrans {
   private double soldPrice = -1D;
   private double price = -1D;

   @ModifyConstant(method = {
           "<clinit>",
           "getBlock(Ljava/lang/String;)Lnet/minecraft/Block;",
   }, constant = @Constant(intValue = 256))
   private static int injected(int value) {
      return 1024;
   }

   @Shadow protected Block setResistance(float par1){
      return null;
   };

   @Overwrite
   public void reportInvalidMetadata(int metadata) {
//      Minecraft.setErrorMessage("Block: invalid metadata value of " + metadata + " for Block[" + this.blockID + "]");
//      (new Exception()).printStackTrace();
   }

   public String getItemDisplayName(ItemStack itemStack){
      if(itemStack != null) {
         return ("" + LocaleI18n.translateToLocal(itemStack.getItem().getUnlocalizedNameInefficiently(itemStack) + ".name")).trim();
      } else {
         return "nothing";
      }
   }

   public double getPrice(){
      return this.price;
   }

   public Block setBlockPrice(double price){
      return this.setPrice(price);
   }

   public Block setPrice(double price){
      this.price = price;
      return (Block) ReflectHelper.dyCast(this);
   }


   public double getSoldPrice(){
      return this.soldPrice;
   }

   public Block setBlockSoldPrice(double price){
      return this.setSoldPrice(price);
   }

   public Block setSoldPrice(double price){
      this.soldPrice = price;
      return (Block) ReflectHelper.dyCast(this);
   }

   public Block setBlockHardness(float resistance) {
      return this.setHardness(resistance);
   }

   public Block setExplosionResistance(float v) {
      return this.setResistance(v);
   }

   public Block setBlockLightLevel(float v){
      return this.setLightValue(v);
   }

   @Shadow
   protected Block setHardness(float par1) {
      return null;
   }

   @Shadow
   protected Block setLightValue(float exp) {
      return null;
   }

   public Block setResourceLocation(String location) {
      return this.setTextureName(location);
   }

   @Shadow
   protected Block setStepSound(StepSound par1StepSound) {
      return null;
   }

   public Block setStepSound_(StepSound stepSound) {
      return this.setStepSound(stepSound);
   }

   @Shadow
   protected Block setTextureName(String par1Str) {
      return null;
   }
}
