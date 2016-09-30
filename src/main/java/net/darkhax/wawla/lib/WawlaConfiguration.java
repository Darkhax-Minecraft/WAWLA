package net.darkhax.wawla.lib;

import java.io.File;

import net.darkhax.wawla.Wawla;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WawlaConfiguration {
    
    public static Configuration config;
    public static boolean useSymbols = true;
    
    public WawlaConfiguration(File configFile) {
        
        config = new Configuration(configFile);
        MinecraftForge.EVENT_BUS.register(this);
        this.syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (ConfigChangedEvent.OnConfigChangedEvent event) {
        
        if (event.getModID().equals(Constants.MODID))
            this.syncConfigData();
    }
    
    private void syncConfigData () {
        
        useSymbols = config.getBoolean("Symbols", "core_settings", true, "Enables the use of symbols in place of some words. This will make things like true display as a check mark.");
        
        for (final InfoProvider provider : Wawla.tileProviders)
            provider.syncConfig(config);
        
        for (final InfoProvider provider : Wawla.entityProviders)
            provider.syncConfig(config);
        
        for (final InfoProvider provider : Wawla.itemProviders)
            provider.syncConfig(config);
        
        config.save();
    }
}