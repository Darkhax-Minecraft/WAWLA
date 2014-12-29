package net.darkhax.wawla.proxy;

import net.darkhax.wawla.addons.vanillamc.AddonEnchantments;
import net.darkhax.wawla.handler.ForgeEventHandler;
import net.darkhax.wawla.plugins.PluginVersionChecker;
import cpw.mods.fml.common.Loader;

public class ProxyClient extends ProxyCommon {

    /**
     * The client side alternative to .registerSidedEvents(), this method should only be called from the
     * client side. The purpose of this method is to register events that are unique to the client.
     */
    @Override
    public void registerSidedEvents() {

        new ForgeEventHandler();
    }

    /**
     * Used to register modules only on the client side.
     */
    @Override
    public void registerSidedModules() {

        new AddonEnchantments();
        new PluginVersionChecker(Loader.isModLoaded("VersionChecker"));
    }
}
