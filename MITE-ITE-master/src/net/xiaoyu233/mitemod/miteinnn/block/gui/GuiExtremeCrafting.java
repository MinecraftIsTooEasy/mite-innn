package net.xiaoyu233.mitemod.miteinnn.block.gui;

import net.minecraft.EntityPlayer;
import net.minecraft.World;
import net.minecraft.awy;
import net.minecraft.bjo;
import net.xiaoyu233.mitemod.miteinnn.block.gui.container.ContainerExtremeCrafting;
import net.xiaoyu233.mitemod.miteinnn.block.tileentity.TileEntityDireCrafting;
import org.lwjgl.opengl.GL11;

public class GuiExtremeCrafting extends awy {
    private static final bjo tex = new bjo("textures/gui/avaritia/dire_crafting_gui.png");
    public GuiExtremeCrafting(EntityPlayer par1InventoryPlayer, TileEntityDireCrafting table) {
        super(new ContainerExtremeCrafting(par1InventoryPlayer, table));
        this.c = 256;
        this.d = 256;
    }

    @Override
    protected void a(float v, int i, int i1) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.f.J().a(tex);
        int foo = (this.g - this.c) / 2;
        int bar = (this.h - this.d) / 2;
        this.b(foo, bar, 0, 0, this.c, this.d);
    }

//    @Override
//    public void a(int mouse_x, int mouse_y, float par3) {
//        super.a(mouse_x, mouse_y, par3);
//    }
}
