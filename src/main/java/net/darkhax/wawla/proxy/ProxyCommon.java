package net.darkhax.wawla.proxy;

import net.darkhax.wawla.modules.ModuleEnchantmentBooks;

public class ProxyCommon {

    /**
     * The server side alternative to .registerSidedEvents(), this method should only be called from the
     * server side. The purpose of this method is to register events that are unique to the server.
     */
    public void registerSidedEvents() {

        new ModuleEnchantmentBooks(true);
    }
}
