package net.darkhax.icse;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.icse.common.CommonProxy;
import net.darkhax.icse.lib.Constants;
import net.darkhax.icse.plugins.InfoPlugin;
import net.darkhax.icse.plugins.entity.PluginHealth;
import net.darkhax.icse.plugins.entity.PluginEntityItem;
import net.darkhax.icse.plugins.tile.PluginMonsterEggFix;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.9,1.9.2]", dependencies = "after:Waila")
public class ICSE {
    
    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static CommonProxy proxy;
    
    @Mod.Instance(Constants.MODID)
    public static ICSE instance;
    
    public static List<InfoPlugin> plugins = new ArrayList<InfoPlugin>();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        proxy.preInit();
        plugins.add(new PluginEntityItem());
        plugins.add(new PluginMonsterEggFix());
        plugins.add(new PluginHealth());
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
    
    }
}