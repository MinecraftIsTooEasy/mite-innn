package net.xiaoyu233.mitemod.miteinnn.block;

import net.minecraft.Block;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteinnn.block.crafting.ExtremeCraftingManager;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;

public class RegisterINFRecipes {

    private static void registerShapelessRecipes(){
    }
    private static void registerShapedRecipes(){
        ExtremeCraftingManager.getInstance().addRecipe(new ItemStack(Items.baublesDamageBoost),
                "         ",
                "         ",
                "  CCACC  ",
                "  CKBFC  ",
                "  DBLBE  ",
                "  CGBKC  ",
                "  CCRCC  ",
                "         ",
                "         ",
                'A', Block.blockMithril,
                'B', Item.ingotMithril,
                'C', Item.redstone,
                //ZOMBIE
                'D', new ItemStack(Item.skull, 1, 2),
                //Skeleton
                'E', new ItemStack(Item.skull, 1, 1),
                'F', Items.voucherZombieLord,
                'G', Items.voucherAnnihilationSkeleton,
                'K', Items.voucherExchanger,
                'L', Item.swordMithril,
                'R', Block.blockAncientMetal);
    }

    public static void registerALLRecipes(){
        registerShapelessRecipes();
        registerShapedRecipes();
    }

}
