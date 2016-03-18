package net.darkhax.wawla;

import net.darkhax.wawla.proxy.ProxyCommon;
import net.darkhax.wawla.util.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.8,1.8.9]", dependencies = "required-after:Waila")
public class Wawla {
    
    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static ProxyCommon proxy;
    
    @Mod.Instance(Constants.MODID)
    public static Wawla instance;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        proxy.preInit();
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
        
        proxy.init();
        
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.vanillamc.AddonVanillaEntities.registerAddon");
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.vanillamc.AddonVanillaTiles.registerAddon");
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.generic.AddonGenericEntities.registerAddon");
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.generic.AddonGenericTiles.registerAddon");
    }
}