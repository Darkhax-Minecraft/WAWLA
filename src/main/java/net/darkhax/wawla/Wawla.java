package net.darkhax.wawla;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.wawla.lib.Feature;
import net.darkhax.wawla.plugins.vanilla.FeatureAgeable;
import net.darkhax.wawla.plugins.vanilla.FeatureArmorPoints;
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
import net.minecraftforge.fml.common.Mod;

@Mod("wawla")
public class Wawla {
    
    public static final Logger LOG = LogManager.getLogger("WAWLA");
    public static final DecimalFormat FORMAT = new DecimalFormat("#.##");   
    private static final List<Feature> features = NonNullList.create();
    
    public Wawla() {
        
        features.add(new FeatureAgeable());
        features.add(new FeatureArmorPoints());
        features.add(new FeatureBlastResistance());
        features.add(new FeatureBreakProgress());
        features.add(new FeatureHardness());
        features.add(new FeatureHorse());
        features.add(new FeatureItemFrame());
        features.add(new FeaturePlayerHead());
        features.add(new FeatureVillagerProfession());
        features.add(new FeatureHiddenBlocks());
        features.add(new FeatureEnchantmentPower());
    }
    
    public static List<Feature> getFeatures() {
        
        return features;
    }
}