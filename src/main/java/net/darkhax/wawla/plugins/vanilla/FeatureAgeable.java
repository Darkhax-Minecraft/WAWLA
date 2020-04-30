package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class FeatureAgeable extends Feature implements IEntityComponentProvider, IServerDataProvider<Entity> {
    
    private static final ResourceLocation BREEDING_COOLDOWN = new ResourceLocation("wawla", "breeding_cooldown");
    private static final ResourceLocation GROWING_COOLDOWN = new ResourceLocation("wawla", "growing_cooldown");
    private static final ResourceLocation BREEDING_ITEM = new ResourceLocation("wawla", "breeding_item");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(BREEDING_COOLDOWN, true);
        hwyla.addConfig(GROWING_COOLDOWN, true);
        hwyla.addConfig(BREEDING_ITEM, true);
        
        hwyla.registerEntityDataProvider(this, AgeableEntity.class);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, AgeableEntity.class);
    }
    
    @Override
    public void appendServerData (CompoundNBT tag, ServerPlayerEntity player, World world, Entity target) {
        
        if (target instanceof AgeableEntity) {
            
            tag.putInt("WawlaAnimalAge", ((AgeableEntity) target).getGrowingAge());
        }
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IEntityAccessor accessor, IPluginConfig config) {
        
        final int growingAge = accessor.getServerData().getInt("WawlaAnimalAge");
        
        if (growingAge < 0 && config.get(GROWING_COOLDOWN)) {
            
            this.addInfo(info, "growingage", StringUtils.ticksToElapsedTime(Math.abs(growingAge)));
        }
        
        else if (growingAge > 0 && config.get(BREEDING_COOLDOWN)) {
            
            this.addInfo(info, "breedingtime", StringUtils.ticksToElapsedTime(growingAge));
        }
        
        if (config.get(BREEDING_ITEM) && growingAge == 0 && accessor.getPlayer() != null && accessor.getEntity() instanceof AnimalEntity) {
            
            final ItemStack heldItem = accessor.getPlayer().getHeldItemMainhand();
            final AnimalEntity animal = (AnimalEntity) accessor.getEntity();
            
            if (!heldItem.isEmpty() && !animal.isChild() && animal.isBreedingItem(heldItem)) {
                
                info.add(this.getInfoComponent("breedingitem").applyTextStyle(TextFormatting.YELLOW));
            }
        }
    }
}
