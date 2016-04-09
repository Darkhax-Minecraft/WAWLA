package net.darkhax.wawla.plugins.vanilla.entities;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;

public class PluginEXPOrb extends InfoProvider {
    
    private static boolean enabled;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled && data.entity instanceof EntityXPOrb)
            info.add(I18n.translateToLocal("tooltip.wawla.vanilla.experience") + ": " + ((EntityXPOrb) data.entity).xpValue);
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("EXP_Orb", "vanilla_entities", true, "When enabled, EXP orbs will show how much exp they are worth.");
    }
}
