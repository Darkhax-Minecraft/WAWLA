package net.darkhax.wawla.modules.addons;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.modules.Module;
import net.darkhax.wawla.util.Reference;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class ModulePixelmon extends Module {
    
    public static Class classEntityPixelmon = null;
    public static Class classTileEntityApricornTree = null;
    public static Class enumNature = null;
    public static Class enumGrowth = null;
    
    private String tooltipKey = "tooltip.wawla.pixelmon.";
    private String showAbility = "wawla.pixelmon.showAbility";
    private String showFriendship = "wawla.pixelmon.showFriendship";
    private String showHeldItem = "wawla.pixelmon.showHeldItem";
    private String showNature = "wawla.pixelmon.showNature";
    private String showSize = "wawla.pixelmon.showSize";
    private String showApricornGrowth = "wawla.pixelmon.showApricornGrowth";
    private String showApricornProduct = "wawla.pixelmon.showApricornProduct";
    
    private static String[] natureList = null;
    private static String[] sizeList = null;
    
    public ModulePixelmon(Boolean enabled) {
    
        super(enabled);
        
        if (enabled) {
            
            try {
                
                classEntityPixelmon = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon");
                classTileEntityApricornTree = Class.forName("com.pixelmonmod.pixelmon.blocks.apricornTrees.TileEntityApricornTree");
                enumNature = Class.forName("com.pixelmonmod.pixelmon.enums.EnumNature");
                enumGrowth = Class.forName("com.pixelmonmod.pixelmon.enums.EnumGrowth");
            }
            
            catch (ClassNotFoundException e) {
                
                Reference.LOG.info("The Pixelmon mod can not be detected. Module ignored.");
            }
        }
    }
    
    @Override
    public void onWailaBlockDescription (ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {
    
        createApricornTooltip(access.getTileEntity(), tooltip, access.getBlock(), config);
        createApricornTooltip(access.getWorld().getTileEntity(access.getPosition().blockX, access.getPosition().blockY - 1, access.getPosition().blockZ), tooltip, access.getBlock(), config);
    }
    
    @Override
    public void onWailaEntityDescription (Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
    
        if (Utilities.compareByClass(classEntityPixelmon, entity.getClass()) && accessor.getNBTData() != null) {
            
            NBTTagCompound tag = accessor.getNBTData();
            if (accessor.getPlayer().isSneaking()) {
                
                if (natureList != null && config.getConfig(showNature))
                    tooltip.add(StatCollector.translateToLocal(tooltipKey + "nature") + ": " + natureList[tag.getShort("Nature")]);
                
                if (config.getConfig(showAbility))
                    tooltip.add(StatCollector.translateToLocal(tooltipKey + "ability") + ": " + tag.getString("Ability"));
                
                if (sizeList != null && config.getConfig(showSize))
                    tooltip.add(StatCollector.translateToLocal(tooltipKey + "size") + ": " + sizeList[tag.getShort("Growth")]);
                
                if (config.getConfig(showFriendship))
                    tooltip.add(StatCollector.translateToLocal(tooltipKey + "happiness") + ": " + tag.getInteger("Friendship"));
                
                if (config.getConfig(showHeldItem) && tag.hasKey("HeldItemStack"))
                    tooltip.add(StatCollector.translateToLocal(tooltipKey + "helditem") + ": " + ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.getTag("HeldItemStack")).getDisplayName());
            }
        }
    }
    
    @Override
    public void onWailaRegistrar (IWailaRegistrar register) {
    
        if (classEntityPixelmon != null)
            register.registerSyncedNBTKey("*", classEntityPixelmon);
        
        if (classTileEntityApricornTree != null)
            register.registerSyncedNBTKey("*", classTileEntityApricornTree);
        
        register.addConfig("Pixelmon", showAbility);
        register.addConfig("Pixelmon", showFriendship);
        register.addConfig("Pixelmon", showHeldItem);
        register.addConfig("Pixelmon", showNature);
        register.addConfig("Pixelmon", showSize);
        register.addConfig("Pixelmon", showApricornGrowth);
        register.addConfig("Pixelmon", showApricornProduct);
        natureList = Utilities.generateElementArray(enumNature);
        sizeList = Utilities.generateElementArray(enumGrowth);
    }
    
    /**
     * This is a helper method to generate the percentage of growth in a block using meta-data
     * stages.
     * 
     * @param curStage: The current stage of the block, should be meta-data.
     * @param maxStage: The stage in meta-data where the block is fully grown.
     * @return float: The percent value of growth based on metadata stages.
     */
    float getGrowth (float curStage, float maxStage) {
    
        return (curStage / maxStage) * 100;
    }
    
    /**
     * This is a custom method used to display information about an apricorn. The main reason
     * of having this method is due to the way apricorn blocks are handled. Apricorns are a
     * double block structures and the top block does not contain the correct information. This
     * corrects for that.
     * 
     * @param entity: A possible TileEntity related to the apricorn tree. It is okay for this
     *            variable to be null.
     * @param tooltip: The list of tool tips.
     * @param block: The name of the block. This is used to generate the name of the product.
     */
    void createApricornTooltip (TileEntity entity, List<String> tooltip, Block block, IWailaConfigHandler config) {
    
        if (entity != null && Utilities.compareByClass(classTileEntityApricornTree, entity.getClass())) {
            
            float meta = entity.getWorldObj().getBlockMetadata(entity.xCoord, entity.yCoord, entity.zCoord);
            String product = Block.blockRegistry.getNameForObject(block);
            
            if (config.getConfig(showApricornGrowth))
                tooltip.add(StatCollector.translateToLocal(tooltipKey + "growth") + ": " + Utilities.round(getGrowth(meta, 5), 0) + "%");
            
            if (config.getConfig(showApricornProduct))
                tooltip.add(StatCollector.translateToLocal(tooltipKey + "product") + ": " + product.substring(9, product.length() - 5));
        }
    }
}