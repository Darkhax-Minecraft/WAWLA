package net.darkhax.wawla.addons.vanillamc;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class AddonVanillaEntities implements IWailaEntityProvider {
    
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
        
        // Horses
        if (entity instanceof EntityHorse) {
            
            EntityHorse horse = (EntityHorse) entity;
            
            if (cfg.getConfig("wawla.horse.showJump"))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.jumpStrength") + ": " + Utilities.round(horse.getHorseJumpStrength(), 4));
                
            if (cfg.getConfig("wawla.horse.showSpeed"))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.speed") + ": " + Utilities.round(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue(), 4));
        }
        
        // TNT
        else if (entity instanceof EntityTNTPrimed && cfg.getConfig(CONFIG_TNT_FUSE))
            tip.add(StatCollector.translateToLocal("tooltip.wawla.tnt.fuse") + ": " + data.getNBTData().getByte("Fuse"));
            
        // Item Frame
        else if (entity instanceof EntityItemFrame) {
            
            EntityItemFrame frame = (EntityItemFrame) entity;
            if (frame.getDisplayedItem() != null)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.item") + ": " + frame.getDisplayedItem().getDisplayName());
        }
        
        // Villager Profession
        if (cfg.getConfig(CONFIG_VILLAGER_PROFESSION)) {
            
            String profession = "";
            
            if (entity instanceof EntityVillager)
                profession = ((EntityVillager) entity).getDisplayName().getFormattedText();
            
            else if (entity instanceof EntityZombie) {
                
                EntityZombie zombie = (EntityZombie) entity;
                
                if (zombie.isVillager())
                    profession = StatCollector.translateToLocal("description.villager.profession.zombie");
            }
            
            else if (entity instanceof EntityWitch)
                profession = StatCollector.translateToLocal("description.villager.profession.witch");
                
            if (!profession.isEmpty())
                tip.add(StatCollector.translateToLocal("tooltip.wawla.profession") + ": " + profession);
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
        
        AddonVanillaEntities dataProvider = new AddonVanillaEntities();
        
        register.registerBodyProvider(dataProvider, EntityHorse.class);
        register.registerNBTProvider(dataProvider, EntityHorse.class);
        
        register.registerBodyProvider(dataProvider, EntityVillager.class);
        register.registerNBTProvider(dataProvider, EntityVillager.class);
        
        register.registerBodyProvider(dataProvider, EntityZombie.class);
        register.registerNBTProvider(dataProvider, EntityZombie.class);
        
        register.registerBodyProvider(dataProvider, EntityWitch.class);
        register.registerNBTProvider(dataProvider, EntityWitch.class);
        
        register.registerBodyProvider(dataProvider, EntityTNTPrimed.class);
        register.registerNBTProvider(dataProvider, EntityTNTPrimed.class);
        
        register.registerBodyProvider(dataProvider, EntityItemFrame.class);
        register.registerNBTProvider(dataProvider, EntityItemFrame.class);
        
        register.addConfig("Wawla-Entity", CONFIG_HORSE_JUMP);
        register.addConfig("Wawla-Entity", CONFIG_HORSE_SPEED);
        register.addConfig("Wawla-Entity", CONFIG_VILLAGER_PROFESSION);
        register.addConfig("Wawla-Entity", CONFIG_TNT_FUSE);
    }
    
    private static final String CONFIG_HORSE_JUMP = "wawla.horse.showJump";
    private static final String CONFIG_HORSE_SPEED = "wawla.horse.showSpeed";
    private static final String CONFIG_VILLAGER_PROFESSION = "wawla.showProfession";
    private static final String CONFIG_TNT_FUSE = "wawla.tnt.fuse";
}
