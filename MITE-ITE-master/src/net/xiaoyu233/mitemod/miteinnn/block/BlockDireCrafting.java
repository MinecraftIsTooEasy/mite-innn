package net.xiaoyu233.mitemod.miteinnn.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteinnn.block.tileentity.TileEntityDireCrafting;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityGemSetting;

import java.util.Random;

public class BlockDireCrafting extends Block implements IContainer {
    private static IIcon top, sides, bottom;
    private final Random random = new Random();
    public BlockDireCrafting(int par1, Material par2Material) {
        super(par1, par2Material, new BlockConstants());
        this.setStepSound(Block.soundGlassFootstep);
        this.setHardness(100.0F);
        this.setResistance(2000.0F);
        this.setMinHarvestLevel(5);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeModeTab.tabDecorations);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z) {
        if (player.onServer() && world.isAirOrPassableBlock(x, y + 1, z, false)) {
            TileEntityDireCrafting tile_entity = (TileEntityDireCrafting) world.getBlockTileEntity(x, y, z);
            if(tile_entity != null) {
                player.displayGUIExtremeCrafting(tile_entity);
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int block_id, int meta) {
        TileEntityDireCrafting craft = (TileEntityDireCrafting)world.getBlockTileEntity(x, y, z);

        if (craft != null) {
            for(int i = 1;i < 82;i++) {
                ItemStack itemstack = craft.getStackInSlot(i);

                if (itemstack != null) {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int j1 = this.random.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(world, (float) x + f, (float) y + f1, (float) z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
                        entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
                world.func_96440_m(x, y, z, block_id);
            }
        }
        super.breakBlock(world, x, y, z, block_id, meta);
        world.removeBlockTileEntity(x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityDireCrafting();
    }

    @Override
    public IIcon a(int side, int metadata) {
        if (side == 0)
            return bottom;
        if (side == 1)
            return top;
        return sides;
    }

    @Override
    public void a(mt mt) {
        top = mt.a("avaritia/dire_crafting_top");
        sides = mt.a("avaritia/dire_crafting_side");
        bottom = mt.a("avaritia/block_crystal_matrix");
    }
}
