package net.darkhax.wawla.plugins.vanilla;

import java.text.DecimalFormat;
import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FeatureHorse extends Feature implements IEntityComponentProvider {
    
    private static final ResourceLocation SHOW_JUMP = new ResourceLocation("wawla", "horse_jump");
    private static final ResourceLocation SHOW_SPEED = new ResourceLocation("wawla", "horse_speed");
    
    private static final DecimalFormat FORMAT = new DecimalFormat("#.####");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(SHOW_JUMP, true);
        hwyla.addConfig(SHOW_SPEED, true);
        
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, AbstractHorseEntity.class);
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IEntityAccessor accessor, IPluginConfig config) {
        
        final Entity entity = accessor.getEntity();
        
        if (entity instanceof AbstractHorseEntity) {
            
            final AbstractHorseEntity horse = (AbstractHorseEntity) entity;
            
            if (config.get(SHOW_JUMP)) {
                
                this.addInfo(info, "jump", FORMAT.format(horse.getHorseJumpStrength()));
            }
            
            if (config.get(SHOW_SPEED)) {
                
                final double horseSpeed = horse.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
                this.addInfo(info, "speed", FORMAT.format(horseSpeed));
            }
        }
    }
}