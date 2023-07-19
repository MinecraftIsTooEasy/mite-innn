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
                "    A    ",
                "   BCD   ",
                "  BCECD  ",
                " BCCFCCD ",
                "GCKFIFJCG",
                " BCCFCCD ",
                "  BCLCD  ",
                "   BCD   ",
                "    R    ",
                'A', Block.blockMithril,
                'B', Item.ingotMithril,
                'C', Item.redstone,
                'D', Item.ingotAncientMetal,
                'E', Items.voucherDoor,
                'F', Block.glass,
                //ZOMBIE
                'G', new ItemStack(Item.skull, 1, 2),
                //Skeleton
                'R', new ItemStack(Item.skull, 1, 1),
                'K', Items.voucherZombieLord,
                'J', Items.voucherAnnihilationSkeleton,
                'L', Items.voucherExchanger,
                'I', Items.itemDynamicCoreIron);
    }

    public static void registerALLRecipes(){
        registerShapelessRecipes();
        registerShapedRecipes();
    }

}
