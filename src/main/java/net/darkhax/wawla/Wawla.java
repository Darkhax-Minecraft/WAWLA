package net.darkhax.wawla;

import net.darkhax.wawla.proxy.ProxyCommon;
import net.darkhax.wawla.util.Constants;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION, acceptableRemoteVersions = "*", dependencies = "required-after:Waila")
public class Wawla {
    
    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static ProxyCommon proxy;
    
    @Mod.Instance(Constants.MODID)
    public static Wawla instance;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
    
        proxy.registerSidedEvents();
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
    
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.vanillamc.AddonVanillaEntities.registerAddon");
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.vanillamc.AddonVanillaTiles.registerAddon");
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.generic.AddonGenericEntities.registerAddon");
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.generic.AddonGenericTiles.registerAddon");
        
        if (Loader.isModLoaded("pixelmon")) {
            
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.pixelmon.AddonPixelmonEntities.registerAddon");
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.pixelmon.AddonPixelmonTiles.registerAddon");
        }
        
        if (Loader.isModLoaded("Thaumcraft"))
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.thaumcraft.AddonThaumcraftTiles.registerAddon");
        
        if (Loader.isModLoaded("TConstruct"))
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.tinkersconstruct.AddonTinkersTiles.registerAddon");
        
        if (Loader.isModLoaded("Jewelrycraft"))
            FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.addons.jewelrycraft.AddonJewelrycraftTiles.registerAddon");
        
        proxy.registerSidedModules();
    }
}