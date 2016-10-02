package net.darkhax.wawla;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.icse.ICSE;
import net.darkhax.wawla.common.CommonProxy;
import net.darkhax.wawla.engine.ICSEEngine;
import net.darkhax.wawla.engine.InfoEngine;
import net.darkhax.wawla.engine.WailaEngine;
import net.darkhax.wawla.lib.Constants;
import net.darkhax.wawla.lib.WawlaConfiguration;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.dragonmounts.PluginEggInfo;
import net.darkhax.wawla.plugins.dragonmounts.PluginStageInfo;
import net.darkhax.wawla.plugins.generic.PluginAnimal;
import net.darkhax.wawla.plugins.generic.PluginArmorPoints;
import net.darkhax.wawla.plugins.generic.PluginBlastResistance;
import net.darkhax.wawla.plugins.generic.PluginBreakProgression;
import net.darkhax.wawla.plugins.generic.PluginEnchantmentDescription;
import net.darkhax.wawla.plugins.generic.PluginEnchantmentPower;
import net.darkhax.wawla.plugins.generic.PluginEquipment;
import net.darkhax.wawla.plugins.generic.PluginHardness;
import net.darkhax.wawla.plugins.generic.PluginHarvestability;
import net.darkhax.wawla.plugins.generic.PluginTameable;
import net.darkhax.wawla.plugins.generic.PluginFluidDescription;
import net.darkhax.wawla.plugins.vanilla.PluginEXPOrb;
import net.darkhax.wawla.plugins.vanilla.PluginFurnace;
import net.darkhax.wawla.plugins.vanilla.PluginHorse;
import net.darkhax.wawla.plugins.vanilla.PluginItemFrame;
import net.darkhax.wawla.plugins.vanilla.PluginPrimedTNT;
import net.darkhax.wawla.plugins.vanilla.PluginSkulls;
import net.darkhax.wawla.plugins.vanilla.PluginVillagerTypes;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptableRemoteVersions = "*", dependencies = "after:icse", acceptedMinecraftVersions = "[1.9.4,1.10.2]")
public class Wawla {
    
    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static CommonProxy proxy;
    
    @Mod.Instance(Constants.MODID)
    public static Wawla instance;
    
    public static InfoEngine engine;
    public static final List<InfoProvider> tileProviders = new ArrayList<>();
    public static final List<InfoProvider> entityProviders = new ArrayList<>();
    public static final List<InfoProvider> itemProviders = new ArrayList<>();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        if (Loader.isModLoaded("Waila"))
            engine = new WailaEngine();
        
        else if (Loader.isModLoaded("ICSE")) {
            
            engine = new ICSEEngine();
            ICSE.plugins.add((ICSEEngine) engine);
        }
        
        // Generic Tiles
        tileProviders.add(new PluginBreakProgression());
        tileProviders.add(new PluginEnchantmentPower());
        tileProviders.add(new PluginBlastResistance());
        tileProviders.add(new PluginHardness());
        tileProviders.add(new PluginHarvestability());
        
        // Generic Entities
        entityProviders.add(new PluginEquipment());
        entityProviders.add(new PluginArmorPoints());
        entityProviders.add(new PluginTameable());
        entityProviders.add(new PluginAnimal());
        
        // Generic Items
        itemProviders.add(new PluginEnchantmentPower());
        itemProviders.add(new PluginFluidDescription());
        if (!Loader.isModLoaded("enchdesc") && event.getSide().equals(Side.CLIENT))
            itemProviders.add(new PluginEnchantmentDescription());
        
        // Vanilla tiles
        tileProviders.add(new PluginSkulls());
        tileProviders.add(new PluginFurnace());
        
        // Vanilla Entities
        entityProviders.add(new PluginHorse());
        entityProviders.add(new PluginVillagerTypes());
        entityProviders.add(new PluginItemFrame());
        entityProviders.add(new PluginPrimedTNT());
        entityProviders.add(new PluginEXPOrb());
        
        if (Loader.isModLoaded("DragonMounts")) {
            
            tileProviders.add(new PluginEggInfo());
            entityProviders.add(new PluginStageInfo());
        }
        
        // Devs
        // tileProviders.add(new PluginDevTiles());
        // entityProviders.add(new PluginDevEntity());
        
        new WawlaConfiguration(event.getSuggestedConfigurationFile());
        proxy.preInit();
    }
}