package net.darkhax.wawla.plugins.vanilla.entities;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

public class PluginAnimal extends InfoProvider {
    
    private static boolean enabled = true;
    private static boolean showBreedingCooldown = true;
    private static boolean showGrowingCooldown = true;
    private static boolean showBreedingItem = true;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled && data.entity instanceof EntityAnimal) {
            
            final EntityAnimal entity = (EntityAnimal) data.entity;
            final int age = data.tag.getInteger("AnimalGrowingAge");
            
            if (age != 0) {
                
                if (showBreedingCooldown && age < 0)
                    info.add(I18n.translateToLocal("tooltip.wawla.generic.growingage") + ": " + StringUtils.ticksToElapsedTime(Math.abs(age)));
                    
                if (showGrowingCooldown && age > 0)
                    info.add(I18n.translateToLocal("tooltip.wawla.generic.breedingtime") + ": " + StringUtils.ticksToElapsedTime(age));
            }
            
            if (showBreedingItem && data.player.getHeldItemMainhand() != null && entity.isBreedingItem(data.player.getHeldItemMainhand()))
                info.add(ChatFormatting.YELLOW + I18n.translateToLocal("tooltip.wawla.generic.breedingitem"));
        }
    }
    
    @Override
    public void writeEntityNBT (World world, Entity entity, NBTTagCompound tag) {
        
        if (enabled && entity instanceof EntityAnimal) {
            
            EntityAnimal animal = (EntityAnimal) entity;
            
            if (showBreedingCooldown || showGrowingCooldown)
                tag.setInteger("AnimalGrowingAge", animal.getGrowingAge());
        }
    }
    
    @Override
    public boolean requireEntitySync (World world, Entity entity) {
        
        return enabled && entity instanceof EntityAnimal;
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Animal", "generic_entities", true, "When enabled, information about the animal will be displayed.");
        showBreedingCooldown = config.getBoolean("Animal_Breeding_Cooldown", "generic_entities", true, "When enabled, shows how long the entity has before it can breed again.");
        showGrowingCooldown = config.getBoolean("Animal_Growing_Age", "generic_entities", true, "When enabled, shows how long the entity has before it is fully grown.");
        showBreedingItem = config.getBoolean("Animal_Breeding_Item", "generic_entities", true, "When enabled, will show if the held item is a breeding item.");
    }
}
