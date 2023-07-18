package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Block;
import net.minecraft.ItemDoor;
import net.minecraft.Material;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemDoor.class)
public class ItemDoorMixin {
    @Shadow
    private Material door_material;
    @Overwrite
    public Block getBlock() {
        if (this.door_material == Material.wood) {
            return Block.doorWood;
        } else if (this.door_material == Material.copper) {
            return Block.doorCopper;
        } else if (this.door_material == Material.silver) {
            return Block.doorSilver;
        } else if (this.door_material == Material.gold) {
            return Block.doorGold;
        } else if (this.door_material == Material.iron) {
            return Block.doorIron;
        } else if (this.door_material == Material.mithril) {
            return Block.doorMithril;
        } else if (this.door_material == Material.adamantium) {
            return Block.doorAdamantium;
        } else if (this.door_material == Materials.vibranium) {
            return Blocks.vibraniumDoor;
        } else {
            return this.door_material == Material.ancient_metal ? Block.doorAncientMetal : null;
        }
    }
}
