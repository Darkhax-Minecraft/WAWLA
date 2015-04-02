package net.darkhax.wawla.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.darkhax.wawla.util.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    
    public static boolean enableDumpFile;
    
    public static Configuration config;
    
    public ConfigurationHandler(File file) {
    
        config = new Configuration(file);
        
        FMLCommonHandler.instance().bus().register(this);
        syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (ConfigChangedEvent.OnConfigChangedEvent event) {
    
        if (event.modID.equals(Constants.MODID))
            syncConfigData();
    }
    
    private void syncConfigData () {
    
        List<String> propOrder = new ArrayList<String>();
        Property prop;
        
        prop = config.get(Configuration.CATEGORY_GENERAL, "Data Dump Enabled", true);
        prop.comment = "Should wawla generate data relating to missing language files?";
        prop.setLanguageKey("wawla.configGui.enableDump");
        enableDumpFile = prop.getBoolean(true);
        propOrder.add(prop.getName());
        
        config.setCategoryPropertyOrder(Configuration.CATEGORY_GENERAL, propOrder);
        
        if (config.hasChanged())
            config.save();
    }
}
