package net.xiaoyu233.mitemod.miteinnn.block.crafting;

import net.minecraft.InventoryCrafting;
import net.minecraft.ItemStack;
import net.minecraft.World;

public interface IRecipe {
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(InventoryCrafting inventoryCrafting, World world);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(InventoryCrafting inventoryCrafting);

    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();

    ItemStack getRecipeOutput();
}
