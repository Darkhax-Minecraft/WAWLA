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
    private final List<Feature> features = NonNullList.create();
    
    public Wawla() {
        
        this.features.add(new PluginAnimal());
        this.features.add(new PluginArmorPoints());
        this.features.add(new PluginBlastResistance());
        this.features.add(new PluginBreakProgression());
        this.features.add(new PluginHardness());
        this.features.add(new PluginHorse());
        this.features.add(new PluginItemFrame());
        this.features.add(new PluginSkulls());
        this.features.add(new PluginVillagerTypes());
    }
    
    @Override
    public void register (IRegistrar hwyla) {
        
        this.features.forEach(f -> f.initialize(hwyla));
    }
}