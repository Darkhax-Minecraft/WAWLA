package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.BlockBeacon;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.StatCollector;

public class ModuleBeacons extends Module {
    
    private String showLevels = "wawla.beacon.showLevels";
    private String showPrimary = "wawla.beacon.showPrimary";
    private String showSecondary = "wawla.beacon.showSecondary";
    
    public ModuleBeacons(boolean enabled) {
    
        super(enabled);
    }
    
    @Override
    public void onWailaBlockDescription (ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {
    
        if (access.getTileEntity() != null && access.getTileEntity() instanceof TileEntityBeacon) {
            
            NBTTagCompound tag = access.getNBTData();
            int level = tag.getInteger("Levels");
            int primary = tag.getInteger("Primary");
            int secondary = tag.getInteger("Secondary");
            
            if (config.getConfig(showLevels))
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.levels") + ": " + level);
            
            if (config.getConfig(showPrimary) && primary > 0)
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.primary") + ": " + StatCollector.translateToLocal(Potion.potionTypes[primary].getName()));
            
            // Although this is always 0 in vanilla it is possible to nbtedit a beacon :)
            if (config.getConfig(showSecondary) && secondary > 0)
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.secondary") + ": " + StatCollector.translateToLocal(Potion.potionTypes[secondary].getName()));
        }
    }
    
    @Override
    public void onWailaRegistrar (IWailaRegistrar register) {
    
        register.registerSyncedNBTKey("*", BlockBeacon.class);
        register.addConfig("Wawla", showLevels);
        register.addConfig("Wawla", showPrimary);
        register.addConfig("Wawla", showSecondary);
    }
}
