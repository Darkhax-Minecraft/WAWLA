package net.darkhax.wawla.plugins.vanilla.entities;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;

public class PluginPrimedTNT extends InfoProvider {
    
    private static boolean enabled;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled && data.entity instanceof EntityTNTPrimed)
            info.add(I18n.translateToLocal("tooltip.wawla.vanilla.fuse") + ": " + StringUtils.ticksToElapsedTime(((EntityTNTPrimed) data.entity).getFuse()));
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Item_Frame", "vanilla_entities", true, "When enabled, shows the fuse time on lit TNT");
    }
}
