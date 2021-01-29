package net.darkhax.wawla;

import java.util.List;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;
import net.darkhax.wawla.lib.Feature;
import net.darkhax.wawla.plugins.vanilla.FeatureAgeable;
import net.darkhax.wawla.plugins.vanilla.FeatureArmorPoints;
import net.darkhax.wawla.plugins.vanilla.FeatureBeeHive;
import net.darkhax.wawla.plugins.vanilla.FeatureBlastResistance;
import net.darkhax.wawla.plugins.vanilla.FeatureBreakProgress;
import net.darkhax.wawla.plugins.vanilla.FeatureEnchantmentPower;
import net.darkhax.wawla.plugins.vanilla.FeatureHardness;
import net.darkhax.wawla.plugins.vanilla.FeatureHiddenBlocks;
import net.darkhax.wawla.plugins.vanilla.FeatureHorse;
import net.darkhax.wawla.plugins.vanilla.FeatureItemFrame;
import net.darkhax.wawla.plugins.vanilla.FeaturePlayerHead;
import net.darkhax.wawla.plugins.vanilla.FeatureVillagerProfession;
import net.minecraft.util.NonNullList;

@WailaPlugin
public class WawlaPlugin implements IWailaPlugin {
    
    private final List<Feature> features = NonNullList.create();
    
    @Override
    public void register (IRegistrar hwyla) {
        
        this.features.add(new FeatureAgeable());
        this.features.add(new FeatureArmorPoints());
        this.features.add(new FeatureBlastResistance());
        this.features.add(new FeatureBreakProgress());
        this.features.add(new FeatureHardness());
        this.features.add(new FeatureHorse());
        this.features.add(new FeatureItemFrame());
        this.features.add(new FeaturePlayerHead());
        this.features.add(new FeatureVillagerProfession());
        this.features.add(new FeatureHiddenBlocks());
        this.features.add(new FeatureEnchantmentPower());
        this.features.add(new FeatureBeeHive());
        
        this.features.forEach(f -> f.initialize(hwyla));
    }
}