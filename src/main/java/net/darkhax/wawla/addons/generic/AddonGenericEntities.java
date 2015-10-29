package net.darkhax.wawla.addons.generic;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class AddonGenericEntities implements IWailaEntityProvider {
    
    private static String[] itemTypes = { "heldItem", "feet", "leggings", "chestplate", "helmet" };
    
    @Override
    public Entity getWailaOverride (IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return data.getEntity();
    }
    
    @Override
    public List<String> getWailaHead (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        // Equipment
        if (entity instanceof EntityLiving && cfg.getConfig(CONFIG_EQUIPMENT)) {
            
            EntityLiving living = (EntityLiving) entity;
            
            for (int i = 0; i < 5; i++) {
                
                ItemStack stack = living.getEquipmentInSlot(i);
                if (stack != null && data.getPlayer().isSneaking())
                    tip.add(StatCollector.translateToLocal("tooltip.wawla." + itemTypes[i]) + ": " + stack.getDisplayName());
            }
            
            // Total Armor
            if (cfg.getConfig(CONFIG_ARMOR) && living.getTotalArmorValue() > 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.armor") + ": " + living.getTotalArmorValue());
        }
        
        // shows pet owner
        if (cfg.getConfig(CONFIG_PET_OWNER) && data.getNBTData().hasKey("OwnerUUID") && data.getNBTData().getString("OwnerUUID").length() > 0)
            tip.add(StatCollector.translateToLocal("tooltip.wawla.owner") + ": " + Utilities.getUsernameByUUID(data.getNBTData().getString("OwnerUUID")));
            
        // shows age info
        if (entity instanceof EntityAnimal) {
            
            EntityAnimal animal = (EntityAnimal) entity;
            
            if (cfg.getConfig(CONFIG_AGE) && animal.isChild() && animal.getGrowingAge() != 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.age") + ": " + ((animal.getGrowingAge() / 20) * -1) + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
                
            else if (cfg.getConfig(CONFIG_BIRTH_COOLDOWN) && animal.getGrowingAge() != 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.birth") + ": " + ((animal.getGrowingAge() / 20)) + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
        }
        
        // shows if sitting
        if (entity instanceof EntityTameable && cfg.getConfig(CONFIG_PET_SITTING)) {
            
            EntityTameable pet = (EntityTameable) entity;
            
            if (pet.isTamed())
                tip.add(StatCollector.translateToLocal("tooltip.wawla.sit") + ": " + pet.isSitting());
        }
        
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {
        
        if (entity != null)
            entity.writeToNBT(tag);
            
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
        
        AddonGenericEntities dataProvider = new AddonGenericEntities();
        
        register.addConfig("Wawla-Entity", CONFIG_EQUIPMENT);
        register.addConfig("Wawla-Entity", CONFIG_PET_OWNER);
        register.addConfig("Wawla-Entity", CONFIG_PET_SITTING);
        register.addConfig("Wawla-Entity", CONFIG_AGE);
        register.addConfig("Wawla-Entity", CONFIG_BIRTH_COOLDOWN);
        register.addConfig("Wawla-Entity", CONFIG_ARMOR);
        
        register.registerBodyProvider(dataProvider, Entity.class);
        register.registerNBTProvider(dataProvider, Entity.class);
    }
    
    private static final String CONFIG_EQUIPMENT = "wawla.showEquipment";
    private static final String CONFIG_ARMOR = "wawla.showMobArmor";
    private static final String CONFIG_PET_OWNER = "wawla.pets.showOwner";
    private static final String CONFIG_PET_SITTING = "wawla.pets.sitting";
    private static final String CONFIG_AGE = "wawla.pets.age";
    private static final String CONFIG_BIRTH_COOLDOWN = "wawla.pets.cooldown";
}
