package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import javax.annotation.Nullable;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PluginVillagerTypes extends Feature implements IEntityComponentProvider {
    
    private static final ResourceLocation ENABLED = new ResourceLocation("wawla", "villager_type");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(ENABLED, true);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, VillagerEntity.class);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, ZombieVillagerEntity.class);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, WitchEntity.class);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, AbstractIllagerEntity.class);
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IEntityAccessor accessor, IPluginConfig config) {
        
        if (config.get(ENABLED)) {
            
            final ITextComponent profession = getProfessionName(accessor.getEntity());
            
            if (profession != null) {
                
                this.addInfo(info, "profession", profession);
            }
        }
    }
    
    @Nullable
    private ITextComponent getProfessionName (Entity entity) {
        
        if (entity instanceof VillagerEntity) {
            
            return getProfessionName(((VillagerEntity) entity).getVillagerData());
        }
        
        else if (entity instanceof ZombieVillagerEntity) {
            
            return getProfessionName(((ZombieVillagerEntity) entity).getVillagerData());
        }
        
        else if (entity instanceof WitchEntity) {
            
            return getProfessionName("witch");
        }
        
        else if (entity instanceof AbstractIllagerEntity) {
            
            return getProfessionName("illager");
        }
        
        return null;
    }
    
    @Nullable
    private ITextComponent getProfessionName (VillagerData data) {
        
        if (data != null) {
            
            final ResourceLocation profName = data.getProfession().getRegistryName();
            return new TranslationTextComponent(EntityType.VILLAGER.getTranslationKey() + '.' + (!"minecraft".equals(profName.getNamespace()) ? profName.getNamespace() + '.' : "") + profName.getPath());
        }
        
        return null;
    }
    
    @Nullable
    private ITextComponent getProfessionName (String profession) {
        
        return new TranslationTextComponent(EntityType.VILLAGER.getTranslationKey() + ".wawla." + profession);
    }
}