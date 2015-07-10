package net.darkhax.wawla.proxy;

import net.darkhax.wawla.addons.generic.AddonGenericTooltips;
import net.minecraftforge.common.MinecraftForge;

public class ProxyClient extends ProxyCommon {
    
    /**
     * The client side alternative to .registerSidedEvents(), this method should only be called
     * from the client side. The purpose of this method is to register events that are unique
     * to the client.
     */
    @Override
    public void registerSidedEvents () {
    
        MinecraftForge.EVENT_BUS.register(new AddonGenericTooltips());
    }
    
    /**
     * Used to register modules only on the client side.
     */
    @Override
    public void registerSidedModules () {
    
    }
}
