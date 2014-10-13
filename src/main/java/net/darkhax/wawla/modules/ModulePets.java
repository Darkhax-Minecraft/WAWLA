package net.darkhax.wawla.modules;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ModulePets extends Module {

    public static ArrayList<String> nbtNames = new ArrayList<String>();
    private String showPetOwner = "wawla.pets.showOwner";
    private String showPetSitting = "wawla.pets.sitting";
    private String showAge = "wawla.pets.age";
    private String showBirthCooldown = "wawla.pets.cooldown";
    
    public ModulePets(boolean enabled) {

        super(enabled);
        nbtNames.add("Owner");
        nbtNames.add("OwnerName");
        nbtNames.add("owner");
        nbtNames.add("ownerName");
    }

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        if (config.getConfig(showPetOwner)) {

            NBTTagCompound tag = Utilities.convertEntityToNbt(entity);
            NBTTagCompound extTag = entity.getEntityData();
            for (String currentKey : nbtNames) {

                if (tag.hasKey(currentKey) && !tag.getString(currentKey).isEmpty())
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.owner") + ": " + tag.getString(currentKey));

                if (extTag.hasKey(currentKey) && !extTag.getString(currentKey).isEmpty())
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.owner") + ": " + extTag.getString(currentKey));
            }
        }
        
        if (entity instanceof EntityAnimal) {
            
            EntityAnimal animal = (EntityAnimal) entity;
            
            if (config.getConfig(showAge) && animal.isChild())
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.age") + ": " + ((animal.getGrowingAge() / 20) * -1 ) + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
            
            else if (config.getConfig(showBirthCooldown) && !animal.isChild()) 
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.birth") + ": " + ((animal.getGrowingAge() / 20)) + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
        }
        
        if (entity instanceof EntityTameable && config.getConfig(showPetSitting)) {
            
            EntityTameable pet = (EntityTameable) entity;
            tooltip.add(StatCollector.translateToLocal("tooltip.wawla.sit") + ": " + pet.isSitting());
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.addConfig("Wawla-Entity", showPetOwner);
        register.addConfig("Wawla-Entity", showPetSitting);
        register.addConfig("Wawla-Entity", showAge);
        register.addConfig("Wawla-Entity", showBirthCooldown);
    }
}