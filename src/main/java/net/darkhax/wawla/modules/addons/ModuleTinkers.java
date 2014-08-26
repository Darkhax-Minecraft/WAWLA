package net.darkhax.wawla.modules.addons;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.modules.Module;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class ModuleTinkers extends Module {
    
    private String showTankInv = "wawla.tinkers.showTankInv";
    private String showSmelterInv = "wawla.tinkers.showSmelteryInv";
    private String hideLandmine = "wawla.tinkers.hideLandmine";
    
    public static boolean isEnabled = false;
    public static Class classHarvestTool = null;
    public static Class classDualHarvestTool = null;
    public static Method getHarvestType = null;
    public static Method getSecondHarvestType = null;
    
    public ModuleTinkers(boolean enabled) {
    
        super(enabled);
        
        if (enabled) {
            
            isEnabled = enabled;
            
            try {
                
                classHarvestTool = Class.forName("tconstruct.library.tools.HarvestTool");
                classDualHarvestTool = Class.forName("tconstruct.library.tools.DualHarvestTool");
                getHarvestType = classHarvestTool.getDeclaredMethod("getHarvestType");
                getSecondHarvestType = classDualHarvestTool.getDeclaredMethod("getSecondHarvestType");
                getHarvestType.setAccessible(true);
                getSecondHarvestType.setAccessible(true);
            }
            
            catch (ClassNotFoundException e) {
                
                e.printStackTrace();
            }
            
            catch (NoSuchMethodException e) {
                
                e.printStackTrace();
            }
            catch (SecurityException e) {
                
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public ItemStack onBlockOverride (ItemStack stack, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    
        // hides landmines
        if (config.getConfig(hideLandmine) && accessor.getTileEntity() != null) {
            
            if (accessor.getStack() != null && accessor.getStack().getDisplayName().contains("mine")) {
                
                if (accessor.getNBTData() != null) {
                    
                    if (accessor.getNBTData().getString("id").equalsIgnoreCase("landmine")) {
                        
                        ItemStack cover = Utilities.getInventoryStacks(accessor.getNBTData(), 4)[3];
                        
                        if (cover != null)
                            return cover;
                    }
                }
            }
        }
        
        return stack;
    }
    
    @Override
    public void onWailaBlockDescription (ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {
    
        if (access.getTileEntity() != null) {
            
            TileEntity tile = access.getTileEntity();
            NBTTagCompound tag = access.getNBTData();
            EntityPlayer player = access.getPlayer();
            
            // Shows smeltery inventory when sneaking
            if (config.getConfig(showSmelterInv) && tag.getString("id").equalsIgnoreCase("tconstruct.smeltery")) {
                
                if (player.isSneaking()) {
                    
                    NBTTagList fluids = tag.getTagList("Liquids", 10);
                    
                    for (int i = 0; i < fluids.tagCount(); i++) {
                        
                        NBTTagCompound liquid = fluids.getCompoundTagAt(i);
                        
                        if (liquid != null) {
                            
                            tooltip.add(getCorrectFluidName(liquid.getString("FluidName")) + ": " + liquid.getInteger("Amount") + StatCollector.translateToLocal("tooltip.wawla.tinkers.mb"));
                        }
                    }
                }
            }
            
            // Adds content of lava tanks
            if (config.getConfig(showTankInv) && tag.getString("id").equalsIgnoreCase("tconstruct.lavatank")) {
                
                if (tag.getInteger("amount") > 0)
                    tooltip.add(getCorrectFluidName(tag.getString("fluidName")) + ": " + tag.getInteger("amount") + StatCollector.translateToLocal("tooltip.wawla.tinkers.mb"));
            }
        }
    }
    
    @Override
    public void onWailaRegistrar (IWailaRegistrar register) {
    
        register.addConfig("Tinkers", showSmelterInv);
        register.addConfig("Tinkers", showTankInv);
        register.addConfig("Tinkers", hideLandmine);
    }
    
    /**
     * Checks to see if a tinkers item is the right type to mine a block.
     * 
     * @param item: The item stack being checked.
     * @param required: The tool type required for the block.
     * @return true: When the item is the right type.
     * @return false: When the item is not the right type.
     */
    public static boolean canHarvest (ItemStack item, String required) {
    
        if (isEnabled) {
            
            List<String> tooltypes = new ArrayList<String>();
            
            if (classDualHarvestTool.isInstance(item.getItem())) {
                
                try {
                    
                    tooltypes.add((String) getSecondHarvestType.invoke(item.getItem()));
                }
                
                catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }
            
            if (classHarvestTool.isInstance(item.getItem())) {
                
                try {
                    
                    tooltypes.add((String) getHarvestType.invoke(item.getItem()));
                }
                
                catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }
            
            return (tooltypes.contains(required)) ? true : false;
        }
        
        return false;
    }
    
    public String getCorrectFluidName (String fluid) {
    
        String base = fluid.split("\\.")[0];
        String result = "";
        
        if (base != null)
            result = StatCollector.translateToLocal("tile.fluid.molten." + base + ".name");
        
        if (result.contains("tile.fluid.molten"))
            result = StatCollector.translateToLocal("tile.fluid." + base + ".name");
        
        if (result.contains("tile.fluid"))
            result = StatCollector.translateToLocal("tile.liquid." + base + ".name");
        
        if (result.contains("tile.liquid"))
            result = StatCollector.translateToLocal("tile.molten." + base + ".name");
        
        if (result.contains("tile.molten"))
            result = StatCollector.translateToLocal(fluid);
        
        return result;
    }
}