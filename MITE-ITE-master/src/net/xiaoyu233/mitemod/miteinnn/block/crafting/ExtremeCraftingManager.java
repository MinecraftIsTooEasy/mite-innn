package net.xiaoyu233.mitemod.miteinnn.block.crafting;

import net.minecraft.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExtremeCraftingManager {
    private static final ExtremeCraftingManager instance = new ExtremeCraftingManager();
    private List recipes = new ArrayList();

    public static final ExtremeCraftingManager getInstance() {
        return instance;
    }

    public ExtremeShapedRecipe addRecipe(ItemStack result, Object ... recipe) {
        StringBuilder s = new StringBuilder();
        int i = 0;
        int width = 0;
        int height = 0;

        if (recipe[i] instanceof String[]) {
            String[] astring = (String[])((String[])recipe[i++]);

            for (String s1 : astring) {
                ++height;
                width = s1.length();
                s.append(s1);
            }
        } else {
            while (recipe[i] instanceof String)
            {
                String s2 = (String)recipe[i++];
                ++height;
                width = s2.length();
                s.append(s2);
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < recipe.length; i += 2) {
            Character character = (Character)recipe[i];
            ItemStack itemstack1 = null;

            if (recipe[i + 1] instanceof Item)
            {
                itemstack1 = new ItemStack((Item)recipe[i + 1]);
            }
            else if (recipe[i + 1] instanceof Block)
            {
                itemstack1 = new ItemStack((Block)recipe[i + 1], 1, 32767);
            }
            else if (recipe[i + 1] instanceof ItemStack)
            {
                itemstack1 = (ItemStack)recipe[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] ingredients = new ItemStack[width * height];

        for (int i1 = 0; i1 < width * height; ++i1) {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0))) {
                ingredients[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
            } else {
                ingredients[i1] = null;
            }
        }

        ExtremeShapedRecipe shapedrecipes = new ExtremeShapedRecipe(width, height, ingredients, result);
        this.recipes.add(shapedrecipes);
        return shapedrecipes;
    }


    public ExtremeShapelessRecipe addShapelessRecipe(ItemStack result, Object ... ingredients) {
        ArrayList arraylist = new ArrayList();
        Object[] aobject = ingredients;
        int i = ingredients.length;

        for (int j = 0; j < i; ++j) {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack)object1).copy());
            } else if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item)object1));
            } else {
                if (!(object1 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((Block)object1));
            }
        }

        ExtremeShapelessRecipe recipe = new ExtremeShapelessRecipe(result, arraylist);
        this.recipes.add(recipe);
        return recipe;
    }


    public ItemStack findMatchingRecipe(InventoryCrafting matrix, World world) {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < matrix.getSizeInventory(); ++j) {
            ItemStack itemstack2 = matrix.getStackInSlot(j);

            if (itemstack2 != null) {
                if (i == 0) {
                    itemstack = itemstack2;
                }

                if (i == 1) {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable()) {
            Item item = itemstack.getItem();
            int j1 = item.getMaxDamage(itemstack) - itemstack.getItemDamageForDisplay();
            int k = item.getMaxDamage(itemstack) - itemstack1.getItemDamageForDisplay();
            int l = j1 + k + item.getMaxDamage(itemstack) * 5 / 100;
            int i1 = item.getMaxDamage(itemstack) - l;

            if (i1 < 0)
            {
                i1 = 0;
            }

            return new ItemStack(itemstack.getItem(), 1, i1);
        } else {
            for (j = 0; j < this.recipes.size(); ++j)
            {
                IRecipe irecipe = (IRecipe)this.recipes.get(j);

                if (irecipe.matches(matrix, world))
                {
                    return irecipe.getCraftingResult(matrix);
                }
            }

            return null;
        }
    }

    public List getRecipeList()
    {
        return this.recipes;
    }
}