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
    
    public WawlaConfiguration(File configFile) {
        
        config = new Configuration(configFile);
        MinecraftForge.EVENT_BUS.register(this);
        syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (ConfigChangedEvent.OnConfigChangedEvent event) {
        
        if (event.getModID().equals(Constants.MODID))
            syncConfigData();
    }
    
    private void syncConfigData () {
        
        for (InfoProvider provider : Wawla.tileProviders)
            provider.syncConfig(config);
            
        for (InfoProvider provider : Wawla.entityProviders)
            provider.syncConfig(config);
    }
}