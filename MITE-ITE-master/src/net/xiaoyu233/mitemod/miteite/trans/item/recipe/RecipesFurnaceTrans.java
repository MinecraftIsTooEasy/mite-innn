package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.Block;
import net.minecraft.ItemStack;
import net.minecraft.RecipesFurnace;
import net.minecraft.TileEntityFurnace;
import net.xiaoyu233.mitemod.miteite.util.RecipeRegister;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(RecipesFurnace.class)
public class RecipesFurnaceTrans {
   @Shadow
   private final Map smeltingList = new HashMap();

//   @Inject(method = "addSmelting", at = @At("RETURN"))
//   private void test(int input_item_id, ItemStack output_item_stack, CallbackInfo callbackInfo){
//      if (output_item_stack != null) {
//         ItemStack inputItemStack = new ItemStack(input_item_id);
//         double input = (double)inputItemStack.getItem().soldPriceArray.get(inputItemStack.getItemSubtype());
//         output_item_stack.getItem().setSoldPrice(input + TileEntityFurnace.getHeatLevelRequired(input_item_id));
//      }
//   }

   public boolean doesSmeltingRecipeExistFor(ItemStack input_item_stack) {
      return this.smeltingList.get(input_item_stack.itemID) != null || input_item_stack.isBlock() && (input_item_stack.getItemAsBlock().getBlock().blockID == Block.blockIron.blockID || input_item_stack.getItemAsBlock().getBlock().blockID == Block.blockMithril.blockID);
   }

   public ItemStack getSmeltingResult(ItemStack input_item_stack, int heat_level) {
      if (input_item_stack == null) {
         return null;
      } else {
         int input_item_id = input_item_stack.itemID;
         if (heat_level == -1) {
            return (ItemStack)this.smeltingList.get(input_item_id);
         } else {
            ItemStack result_item_stack;
            if (input_item_id == Block.sand.blockID) {
               result_item_stack = (heat_level != 1 || input_item_stack.stackSize >= 4) && input_item_stack.stackSize >= 4 ? new ItemStack(heat_level == 1 ? Block.sandStone : Block.glass) : null;
            }
//            else if (input_item_id == Block.blockIron.blockID) {
//               result_item_stack = heat_level >= 3 ? (ItemStack)this.smeltingList.get(input_item_id) : null;
////                       && input_item_stack.stackSize >= Configs.wenscConfig.ironBlockCountToMithril.ConfigValue ? new ItemStack(Item.ingotMithril) : null;
//            } else if (input_item_id == Block.blockMithril.blockID) {
//               result_item_stack = heat_level >= 4 && input_item_stack.stackSize >= Configs.wenscConfig.mithrilBlockCountToAdamantium.ConfigValue ? new ItemStack(Item.ingotAdamantium) : null;
//            }
            else {
               result_item_stack = (ItemStack)this.smeltingList.get(input_item_id);
            }

            return heat_level < TileEntityFurnace.getHeatLevelRequired(input_item_stack.itemID) ? null : result_item_stack;
         }
      }
   }
}
