package net.darkhax.wawla.proxy;

import net.darkhax.wawla.addons.generic.AddonGenericTooltips;
import net.darkhax.wawla.handler.MissingDataDumpHandler;
import net.darkhax.wawla.plugins.PluginVersionChecker;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Loader;

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
    
        new AddonGenericTooltips();
        new PluginVersionChecker(Loader.isModLoaded("VersionChecker"));
    }
    
    /**
     * Used to handle postInit stuff on the client.
     */
    @Override
    public void sidedPostInit () {
    
        new MissingDataDumpHandler();
    }
}
