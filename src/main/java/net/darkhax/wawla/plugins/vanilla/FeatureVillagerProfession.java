package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FeatureVillagerProfession extends Feature implements IEntityComponentProvider, IServerDataProvider<Entity> {
    
    private static final ResourceLocation TIER = new ResourceLocation("wawla", "villager_tier");
    private static final ResourceLocation TIER_PROGRESS = new ResourceLocation("wawla", "villager_tier_progress");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(TIER, true);
        hwyla.addConfig(TIER_PROGRESS, true);
        
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, VillagerEntity.class);
        hwyla.registerComponentProvider(this, TooltipPosition.HEAD, VillagerEntity.class);
        
        hwyla.registerEntityDataProvider(this, VillagerEntity.class);
    }
    
    @Override
    public void appendServerData (CompoundNBT nbt, ServerPlayerEntity player, World world, Entity target) {
        
        if (target instanceof VillagerEntity) {
            
            final VillagerEntity villager = (VillagerEntity) target;
            final CompoundNBT villagerData = new CompoundNBT();
            final int level = villager.getVillagerData().getLevel();
            
            villagerData.putInt("level", level);
            villagerData.putInt("curExp", villager.getXp());
            villagerData.putInt("targetExp", VillagerData.getExperienceNext(level));
            villagerData.putBoolean("hasTrades", !villager.getOffers().isEmpty());
            nbt.put("WAWLAVillager", villagerData);
        }
    }
    
    @Override
    public void appendHead (List<ITextComponent> info, IEntityAccessor accessor, IPluginConfig config) {
        
        if (accessor.getServerData().contains("WAWLAVillager") && accessor.getEntity() != null && !accessor.getEntity().hasCustomName()) {
            
            final CompoundNBT data = accessor.getServerData().getCompound("WAWLAVillager");
            
            if (data.getBoolean("hasTrades") && config.get(TIER)) {
                
                final int level = data.getInt("level");
                
                if (level > 0 && level <= 5) {
                    
                    info.set(0, new TranslationTextComponent("info.wawla.villager.name", new TranslationTextComponent("merchant.level." + level), accessor.getEntity().getDisplayName()));
                }
            }
        }
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IEntityAccessor accessor, IPluginConfig config) {
        
        if (accessor.getEntity() instanceof VillagerEntity && accessor.getServerData().contains("WAWLAVillager")) {
            
            final VillagerEntity villager = (VillagerEntity) accessor.getEntity();
            final CompoundNBT data = accessor.getServerData().getCompound("WAWLAVillager");
            
            if (data.getBoolean("hasTrades")) {
                
                if (config.get(TIER) && villager.hasCustomName()) {
                    
                    final int level = data.getInt("level");
                    
                    if (level > 0 && level <= 5) {
                        
                        final ResourceLocation profId = villager.getVillagerData().getProfession().getRegistryName();
                        final ITextComponent professionName = new TranslationTextComponent(villager.getType().getTranslationKey() + '.' + (!"minecraft".equals(profId.getNamespace()) ? profId.getNamespace() + '.' : "") + profId.getPath());
                        
                        info.add(new TranslationTextComponent("info.wawla.profession", new TranslationTextComponent("merchant.level." + level), professionName));
                    }
                }
                
                if (config.get(TIER_PROGRESS)) {
                    
                    final int curXp = data.getInt("curExp");
                    final int targetXp = data.getInt("targetExp");
                    
                    if (targetXp > 0) {
                        
                        info.add(new TranslationTextComponent("info.wawla.villager.exp", curXp, targetXp));
                    }
                }
            }
        }
    }
}