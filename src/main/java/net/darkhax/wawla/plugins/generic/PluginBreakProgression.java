package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;

public class PluginBreakProgression extends InfoProvider {
    
    private static boolean enabled = true;
    
    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {
        
        if (enabled) {
            
            final double progress = Minecraft.getMinecraft().playerController.curBlockDamageMP;
            
            if (progress > 0.0d)
                info.add(I18n.format("tooltip.wawla.generic.progression") + ": " + (int) (progress * 100) + "%");
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("BreakProgression", "generic_tiles", true, "If this is enabled, the hud will display the break progression.");
    }
}