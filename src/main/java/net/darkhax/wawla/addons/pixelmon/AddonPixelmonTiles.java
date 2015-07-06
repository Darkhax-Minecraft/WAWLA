package net.darkhax.wawla.addons.pixelmon;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class AddonPixelmonTiles implements IWailaDataProvider {
    
    public AddonPixelmonTiles() {
    
        if (Loader.isModLoaded("pixelmon")) {
            
            try {
                
                classTileEntityApricornTree = Class.forName("com.pixelmonmod.pixelmon.blocks.apricornTrees.TileEntityApricornTree");
                classBlockApricornTree = Class.forName("com.pixelmonmod.pixelmon.blocks.apricornTrees.BlockApricornTree");
            }
            
            catch (ClassNotFoundException e) {
                
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public ItemStack getWailaStack (IWailaDataAccessor data, IWailaConfigHandler cfg) {
    
        return data.getStack();
    }
    
    @Override
    public List<String> getWailaHead (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
    
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
    
        createApricornTooltip(data.getTileEntity(), tip, data.getBlock(), cfg);
        createApricornTooltip(data.getWorld().getTileEntity(data.getPosition().blockX, data.getPosition().blockY - 1, data.getPosition().blockZ), tip, data.getBlock(), cfg);
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
    
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
    
        if (te != null)
            te.writeToNBT(tag);
        
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
    
        AddonPixelmonTiles dataProvider = new AddonPixelmonTiles();
        register.addConfig("Pixelmon", showApricornGrowth);
        register.addConfig("Pixelmon", showApricornProduct);
        register.registerBodyProvider(dataProvider, classBlockApricornTree);
        register.registerNBTProvider(dataProvider, classBlockApricornTree);
    }
    
    /**
     * This is a custom method used to display information about an apricorn. The main reason
     * of having this method is due to the way apricorn blocks are handled. Apricorns are a
     * double block structures and the top block does not contain the correct information. This
     * corrects for that.
     * 
     * @param entity : A possible TileEntity related to the apricorn tree. It is okay for this
     *            variable to be null.
     * @param tip : The list of tool tips.
     * @param block : The name of the block. This is used to generate the name of the product.
     */
    void createApricornTooltip (TileEntity entity, List<String> tip, Block block, IWailaConfigHandler cfg) {
    
        if (entity != null && Utilities.compareByClass(classTileEntityApricornTree, entity.getClass())) {
            
            float meta = entity.getWorldObj().getBlockMetadata(entity.xCoord, entity.yCoord, entity.zCoord);
            String product = Block.blockRegistry.getNameForObject(block);
            
            if (cfg.getConfig(showApricornGrowth))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.growth") + ": " + Utilities.round(Utilities.getGrowth(meta, 5), 0) + "%");
            
            if (cfg.getConfig(showApricornProduct))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.product") + ": " + product.substring(9, product.length() - 5));
        }
    }
    
    public static Class classTileEntityApricornTree = null;
    public static Class classBlockApricornTree = null;
    
    private static String showApricornGrowth = "wawla.pixelmon.showApricornGrowth";
    private static String showApricornProduct = "wawla.pixelmon.showApricornProduct";
}
