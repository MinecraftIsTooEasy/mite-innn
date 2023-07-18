package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteinnn.block.gui.container.ContainerExtremeCrafting;
import net.xiaoyu233.mitemod.miteite.item.ItemEnhanceGem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(awy.class)
public class GuiCraftingTrans extends awe{
    @Shadow public Container e;
    @Overwrite
    protected void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3, Slot slot) {
        List var4 = par1ItemStack.getTooltip(this.f.h, awe.p(), slot);

        for(int var5 = 0; var5 < var4.size(); ++var5) {
            if (var5 == 0) {
                var4.set(var5, "§" + Integer.toHexString(par1ItemStack.w().e) + (String)var4.get(var5));
            } else {
                var4.set(var5, EnumChatFormat.GRAY + (String)var4.get(var5));
            }
        }

        if (slot instanceof SlotResult && !(this.e instanceof ContainerExtremeCrafting)) {
            this.e.crafting_result_shown_but_prevented = false;
            Item item = par1ItemStack.getItem();
            aah recipe = ((MITEContainerCrafting)this.e).getRecipe();
            Material material_to_check_tool_bench_hardness_against = recipe == null ? item.getHardestMetalMaterial() : recipe.getMaterialToCheckToolBenchHardnessAgainst();
            boolean upper_body_in_web = this.f.h.isUpperBodyInWeb();
            if (material_to_check_tool_bench_hardness_against != null || upper_body_in_web) {
                List tooltips = new ArrayList();
                if (upper_body_in_web) {
                    tooltips.add(EnumChatFormat.YELLOW + Translator.get("container.crafting.prevented"));
                    this.e.crafting_result_shown_but_prevented = true;
                } else if (this.e instanceof ContainerWorkbench) {
                    ContainerWorkbench container_workbench = (ContainerWorkbench)this.e;
                    Material tool_material = BlockWorkbench.getToolMaterial(container_workbench.getBlockMetadata());
                    if (tool_material == null) {
                        tooltips.add(EnumChatFormat.YELLOW + Translator.get("container.crafting.needsTools"));
                        this.e.crafting_result_shown_but_prevented = true;
                    } else if (material_to_check_tool_bench_hardness_against.getDurability() > tool_material.getDurability()) {
                        tooltips.add(EnumChatFormat.YELLOW + Translator.get("container.crafting.needsBetterTools"));
                        this.e.crafting_result_shown_but_prevented = true;
                    }
                } else {
                    tooltips.add(EnumChatFormat.YELLOW + Translator.get("container.crafting.needsToolBench"));
                    this.e.crafting_result_shown_but_prevented = true;
                }

                if (tooltips.size() > 0) {
                    if (var4.size() > 1) {
                        var4.add("");
                    }

                    var4.addAll(tooltips);
                    this.a(var4, par2, par3);
                    return;
                }
            }

            CraftingResult crafting_result = ((MITEContainerCrafting)slot.getContainer()).current_crafting_result;
            if (this.f.f.areSkillsEnabled() && !this.f.h.hasSkillsForCraftingResult(crafting_result)) {
                if (var4.size() > 2) {
                    var4.add("");
                }

                if (item.hasQuality() && !(crafting_result.recipe instanceof zw)) {
                    var4.add(EnumChatFormat.DARK_GRAY + Skill.getSkillsetsString(crafting_result.applicable_skillsets, false));
                } else {
                    var4.add(EnumChatFormat.DARK_GRAY + "Requires " + Skill.getSkillsetsString(crafting_result.applicable_skillsets, true));
                }
            } else if (item instanceof ItemCoin && this.f.h.experience < ((ItemCoin)item).getExperienceValue() * par1ItemStack.stackSize) {
                var4.add(EnumChatFormat.YELLOW + Translator.get("container.crafting.requiresExperience"));
                this.e.crafting_result_shown_but_prevented = true;
            } else if (this.e instanceof MITEContainerCrafting && ((MITEContainerCrafting)this.e).craft_matrix.hasDamagedItem() && !((MITEContainerCrafting)this.e).current_crafting_result.isRepair()) {
                var4.add(EnumChatFormat.YELLOW + Translator.get("container.crafting.damagedComponent"));
                this.e.crafting_result_shown_but_prevented = true;
            }
        }

        if (slot instanceof SlotResult
               // || slot instanceof SlotAnvilResult
        ) {
            if (this.e.repair_fail_condition == 1) {
                var4.add("");
                var4.add(EnumChatFormat.YELLOW + Translator.get("container.repair.insufficientSkill"));
            } else if (this.e.repair_fail_condition == 2) {
                var4.add("");
                var4.add(EnumChatFormat.YELLOW + Translator.get("container.repair.needsHarderAnvil"));
            }
        }

        this.a(var4, par2, par3);
    }

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "drawItemStackTooltip",at = @At(value = "INVOKE", target = "Lnet/minecraft/BlockWorkbench;getToolMaterial(I)Lnet/minecraft/Material;", shift = At.Shift.AFTER))
    public void judgeGemListExistThenPrevent(ItemStack par1ItemStack, int par2, int par3, Slot slot, CallbackInfo ci, List var4, Item item, aah recipe, Material material_to_check_tool_bench_hardness_against, boolean upper_body_in_web, List tooltips, ContainerWorkbench container_workbench) {
        for (int i = 0; i < container_workbench.craft_matrix.getInventory().length; i++) {
            if(container_workbench.craft_matrix.getInventory() != null) {
                ItemStack itemStack = container_workbench.craft_matrix.getInventory()[i];
                if(itemStack != null) {
                    if(itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("Gems")) {
                        NBTTagList nbtTagList = itemStack.stackTagCompound.getTagList("Gems");
                        for (int j = 0; j < nbtTagList.tagCount(); j++) {
                            NBTTagCompound nbtTagCompound = (NBTTagCompound) nbtTagList.tagAt(j);
                            if (nbtTagCompound.getShort("id") >= 0 && nbtTagCompound.getByte("meta") >= 0) {
                                Item itemLocal = Item.getItem(nbtTagCompound.getShort("id"));
                                if (itemLocal instanceof ItemEnhanceGem) {
                                    tooltips.add(EnumChatFormat.RED + Translator.get("container.crafting.needsRemoveGems"));
                                    this.e.crafting_result_shown_but_prevented = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Redirect(method = "drawItemStackTooltip",at = @At(value = "INVOKE",target = "Lnet/minecraft/InventoryCrafting;hasDamagedItem()Z"))
    private boolean redirectRemoveDamageLimitation(InventoryCrafting caller){
        aah recipe = ((MITEContainerCrafting) this.e).current_crafting_result.recipe;
        if (recipe instanceof ShapedRecipes){
            return !(((ShapedRecipes) recipe).isExtendsNBT()) && caller.hasDamagedItem();
        }else if (recipe instanceof ShapelessRecipes){
            return !((ShapelessRecipes) recipe).isExtendsNBT() && caller.hasDamagedItem();
        }
        return caller.hasDamagedItem();
    }

    @Shadow
    protected void a(List par1List, int par2, int par3) {
    }
}
