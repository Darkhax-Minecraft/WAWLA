package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.item.ItemStack;

public class ModuleDeveloperMode extends Module {
    
    public ModuleDeveloperMode(boolean enabled) {
    
        super(enabled);
    }
    
    public void onWailaBlockDescription (ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {
    
        if (access.getTileEntity() != null && config.getConfig("option.wawla.devmode"))
            Utilities.wrapStringToList(access.getNBTData().toString(), 40, true, tooltip);
    }
    
    public void onWailaRegistrar (IWailaRegistrar register) {
    
        register.addConfig("Wawla", "option.wawla.devmode");
    }
}
