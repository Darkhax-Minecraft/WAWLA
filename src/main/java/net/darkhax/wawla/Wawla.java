package net.darkhax.wawla;

import net.darkhax.wawla.common.CommonProxy;
import net.darkhax.wawla.config.WawlaConfiguration;
import net.darkhax.wawla.engine.InfoEngine;
import net.darkhax.wawla.engine.WailaEngine;
import net.darkhax.wawla.lib.Constants;
import net.darkhax.wawla.plugins.FeatureManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptableRemoteVersions = "*")
public class Wawla {

    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static CommonProxy proxy;

    @Mod.Instance(Constants.MODID)
    public static Wawla instance;

    public static WawlaConfiguration config;

    public static InfoEngine engine;

    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

        config = new WawlaConfiguration("wawla");

        FeatureManager.init(event.getAsmData());

        config.init(FeatureManager.classes);
        config.sync();

        if (Loader.isModLoaded("waila"))
            engine = new WailaEngine();

        // if (!Loader.isModLoaded("enchdesc") && event.getSide().equals(Side.CLIENT))
        // itemProviders.add(new PluginEnchantmentDescription());

        proxy.preInit();
    }
}