package net.xiaoyu233.mitemod.miteinnn.block;

import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteinnn.block.crafting.ExtremeCraftingManager;
import net.xiaoyu233.mitemod.miteite.item.Items;

public class RegisterINFRecipes {

    private static void registerShapelessRecipes(){
        ExtremeCraftingManager.getInstance().addRecipe(new ItemStack(Items.endBook),
                "  NNNNN  ",
                'N', Items.DIAMOND_CHUNK);
    }
    private static void registerShapedRecipes(){
        ExtremeCraftingManager.getInstance().addRecipe(new ItemStack(Items.endBook),
                "  NNNNN  ",
                'N', Items.DIAMOND_CHUNK);
    }

    public static void registerALLRecipes(){
        registerShapelessRecipes();
        registerShapedRecipes();
    }

}
