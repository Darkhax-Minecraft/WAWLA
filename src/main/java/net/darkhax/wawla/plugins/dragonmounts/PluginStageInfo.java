package net.darkhax.wawla.plugins.dragonmounts;

import java.util.List;

import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import info.ata4.minecraft.dragon.server.entity.helper.EnumDragonLifeStage;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Optional.Method;

public class PluginStageInfo extends InfoProvider {
    
    private boolean showStage = true;
    private boolean showAdultTimer = true;
    private boolean showStageTimer = true;
    private final int maxTime = 4 * EnumDragonLifeStage.TICKS_PER_STAGE;
    
    @Override
    @Method(modid = "DragonMounts")
    public void syncConfig (Configuration config) {
        
        this.showStage = config.getBoolean("Stage", "dragon_mounts", true, "If this is enabled, the hud will show the current stage of the dragon.");
        this.showAdultTimer = config.getBoolean("AdultTimer", "dragon_mounts", true, "If this is enabled, the hud will show the time until the dragon is fully grown.");
        this.showStageTimer = config.getBoolean("StageTimer", "dragon_mounts", true, "If this is enabled, the hud will show the time until the dragon enters the next stage.");
    }
    
    @Override
    @Method(modid = "DragonMounts")
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (data.entity instanceof EntityTameableDragon) {
            
            final EntityTameableDragon dragon = (EntityTameableDragon) data.entity;
            
            if (this.showStage)
                I18n.format("tooltip.wawla.dragonmounts.stage." + dragon.getLifeStageHelper().getLifeStage().name().toLowerCase());
            
            if (this.showAdultTimer) {
                
                final int time = this.maxTime - dragon.getLifeStageHelper().getTicksSinceCreation();
                
                if (time > 0)
                    info.add(I18n.format("tooltip.wawla.dragonmounts.adulttime") + ": " + StringUtils.ticksToElapsedTime(time));
            }
            
            if (this.showStageTimer) {
                
                final int time = this.ticksUntilNextStage(dragon.getLifeStageHelper().getTicksSinceCreation(), dragon.getLifeStageHelper().getLifeStage());
                
                if (time > 0)
                    info.add(I18n.format("tooltip.wawla.dragonmounts.nexttime") + ": " + StringUtils.ticksToElapsedTime(time));
            }
        }
    }
    
    private int ticksUntilNextStage (int currentTicks, EnumDragonLifeStage stage) {
        
        final int currentStage = stage == EnumDragonLifeStage.EGG ? 0 : stage == EnumDragonLifeStage.HATCHLING ? 1 : stage == EnumDragonLifeStage.JUVENILE ? 2 : 3;
        final int ticks = currentTicks - currentStage * EnumDragonLifeStage.TICKS_PER_STAGE;
        return EnumDragonLifeStage.TICKS_PER_STAGE - ticks;
    }
}
