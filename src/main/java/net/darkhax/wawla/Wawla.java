package net.darkhax.wawla;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;
import net.darkhax.wawla.lib.Feature;
import net.darkhax.wawla.plugins.vanilla.PluginAnimal;
import net.darkhax.wawla.plugins.vanilla.PluginArmorPoints;
import net.darkhax.wawla.plugins.vanilla.PluginBlastResistance;
import net.darkhax.wawla.plugins.vanilla.PluginBreakProgression;
import net.darkhax.wawla.plugins.vanilla.PluginHardness;
import net.darkhax.wawla.plugins.vanilla.PluginHorse;
import net.darkhax.wawla.plugins.vanilla.PluginItemFrame;
import net.darkhax.wawla.plugins.vanilla.PluginSkulls;
import net.darkhax.wawla.plugins.vanilla.PluginVillagerTypes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Mod;

@Mod("wawla")
@WailaPlugin
public class Wawla implements IWailaPlugin {

    public static final Logger LOG = LogManager.getLogger("WAWLA");
    private List<Feature> features = NonNullList.create();
    
    public Wawla () {

        features.add(new PluginAnimal());
        features.add(new PluginArmorPoints());
        features.add(new PluginBlastResistance());
        features.add(new PluginBreakProgression());
        features.add(new PluginHardness());
        features.add(new PluginHorse());
        features.add(new PluginItemFrame());
        features.add(new PluginSkulls());
        features.add(new PluginVillagerTypes());
    }

    @Override
    public void register (IRegistrar hwyla) {
        
        features.forEach(f -> f.initialize(hwyla));
    }
}