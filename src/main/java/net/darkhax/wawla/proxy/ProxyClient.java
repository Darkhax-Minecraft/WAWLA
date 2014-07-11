package net.darkhax.wawla.proxy;

import net.darkhax.wawla.handler.ForgeEventHandler;
import net.darkhax.wawla.handler.WailaBlockHandler;
import net.darkhax.wawla.handler.WailaEntityHandler;
import net.darkhax.wawla.modules.ModuleEnchantmentBooks;
import net.darkhax.wawla.modules.ModuleEntityEquipment;
import net.darkhax.wawla.modules.ModuleHarvest;
import net.darkhax.wawla.modules.ModuleHorses;
import net.darkhax.wawla.modules.ModuleItemFrame;
import net.darkhax.wawla.modules.ModulePets;
import net.darkhax.wawla.modules.ModulePlayerhead;

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

        new ModuleEnchantmentBooks();
        new ModuleEntityEquipment();
        new ModuleItemFrame();
        new ModuleHorses();
        new ModulePets();
        new ModuleHarvest();
        new ModulePlayerhead();
    }
}
