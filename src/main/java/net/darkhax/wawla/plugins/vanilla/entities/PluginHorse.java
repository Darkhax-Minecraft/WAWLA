package net.darkhax.wawla.plugins.vanilla.entities;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraftforge.common.config.Configuration;

public class PluginHorse extends InfoProvider {
    
    private static boolean jump = true;
    private static boolean speed = true;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (data.entity instanceof EntityHorse) {
            
            final EntityHorse horse = (EntityHorse) data.entity;
            
            if (jump)
                info.add(I18n.format("tooltip.wawla.vanilla.jump") + ": " + this.getPlayerRelativeInfo(horse.getHorseJumpStrength(), 0.45d));
                
            if (speed)
                info.add(I18n.format("tooltip.wawla.vanilla.speed") + ": " + this.getPlayerRelativeInfo(horse.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(), 0.1d));
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        jump = config.getBoolean("Horse_Jump", "vanilla_entities", true, "When enabled, shows how many times higher the horse can jump, when compared to the player.");
        speed = config.getBoolean("Horse_Speed", "vanilla_entities", true, "When enabled, shows how many times faster the horse can run, when compared to the player's normal walking speed.");
    }
    
    public String getPlayerRelativeInfo (double horseStat, double playerStat) {
        
        return InfoProvider.round(horseStat / playerStat, 1) + "X";
    }
}
