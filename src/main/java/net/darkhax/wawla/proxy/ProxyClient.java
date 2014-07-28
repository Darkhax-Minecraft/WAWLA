package net.darkhax.wawla.proxy;

import net.darkhax.wawla.handler.ForgeEventHandler;
import net.darkhax.wawla.handler.WailaBlockHandler;
import net.darkhax.wawla.handler.WailaEntityHandler;
import net.darkhax.wawla.modules.ModuleEnchantmentBooks;
import net.darkhax.wawla.modules.ModuleEntityEquipment;
import net.darkhax.wawla.modules.ModuleFurnace;
import net.darkhax.wawla.modules.ModuleHarvest;
import net.darkhax.wawla.modules.ModuleHorses;
import net.darkhax.wawla.modules.ModuleItemFrame;
import net.darkhax.wawla.modules.ModulePets;
import net.darkhax.wawla.modules.ModulePlayerhead;
import net.darkhax.wawla.modules.addons.ModulePixelmon;

public class ProxyClient extends ProxyCommon {

    /**
     * The client side alternative to .registerSidedEvents(), this method should only be called from the
     * client side. The purpose of this method is to register events that are unique to the client.
     */
    @Override
    public void registerSidedEvents() {

        new ForgeEventHandler();
        new WailaBlockHandler();
        new WailaEntityHandler();
    }

    /**
     * this method is used to launch modules from the client side.
     */
    @Override
    public void registerSidedModules() {

        new ModuleEnchantmentBooks(true);
        new ModuleEntityEquipment(true);
        new ModuleItemFrame(true);
        new ModuleHorses(true);
        new ModulePets(true);
        new ModuleHarvest(true);
        new ModulePlayerhead(true);
        new ModuleFurnace(true);
    }

    @Override
    public void registerSidedPlugins() {

        new ModulePixelmon(true);
    }
}
