package net.darkhax.wawla;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class WawlaPlugin implements IWailaPlugin {
    
    @Override
    public void register (IRegistrar hwyla) {
        
        Wawla.getFeatures().forEach(f -> f.initialize(hwyla));
    }
}