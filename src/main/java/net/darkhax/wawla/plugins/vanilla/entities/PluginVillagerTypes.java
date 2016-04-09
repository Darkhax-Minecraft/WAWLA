package net.darkhax.wawla.plugins.vanilla.entities;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;

public class PluginVillagerTypes extends InfoProvider {
    
    private static boolean enabled = true;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled) {
            
            String career = "";
            
            if (data.entity instanceof EntityVillager)
                career = ((EntityVillager) data.entity).getDisplayName().getFormattedText();
                
            else if (data.entity instanceof EntityZombie && ((EntityZombie) data.entity).isVillager())
                career = I18n.translateToLocal("villager.wawla.zombie");
                
            else if (data.entity instanceof EntityWitch)
                career = I18n.translateToLocal("villager.wawla.witch");
                
            if (career != null && !career.isEmpty())
                info.add(I18n.translateToLocal("tooltip.wawla.vanilla.career") + ": " + career);
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Villager_Career", "vanilla_entities", true, "When enabled, shows the career type of villagers on the HUD.");
    }
}
