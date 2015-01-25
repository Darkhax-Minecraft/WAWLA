package net.darkhax.wawla.addons.vanillamc;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class AddonVanillaEntities implements IWailaEntityProvider {

    @Override
    public Entity getWailaOverride(IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return data.getEntity();
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public List<String> getWailaBody(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        // Horses
        if (entity instanceof EntityHorse) {

            EntityHorse horse = (EntityHorse) entity;

            if (cfg.getConfig("wawla.horse.showJump"))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.jumpStrength") + ": " + Utilities.round(horse.getHorseJumpStrength(), 4));

            if (cfg.getConfig("wawla.horse.showSpeed"))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.speed") + ": " + Utilities.round(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue(), 4));
        }

        // Villager
        else if (entity instanceof EntityZombie && cfg.getConfig(showZombieType)) {

            EntityZombie zombie = (EntityZombie) entity;
            if (zombie.isVillager())
                tip.add("Zombie Villager");
        }
        
        else if (entity instanceof EntityPlayer) {
            
            EntityPlayer player = (EntityPlayer) entity;
            
            if (player.experienceLevel > 0 && cfg.getConfig(showPlayerLevel))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.playerLevel") + ": " + player.experienceLevel);
        }

        return tip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {

        if (entity != null)
            entity.writeToNBT(tag);

        return tag;
    }

    public static void registerAddon(IWailaRegistrar register) {

        AddonVanillaEntities dataProvider = new AddonVanillaEntities();

        register.registerBodyProvider(dataProvider, EntityHorse.class);
        register.registerNBTProvider(dataProvider, EntityHorse.class);

        register.registerBodyProvider(dataProvider, EntityVillager.class);
        register.registerNBTProvider(dataProvider, EntityVillager.class);
        
        register.addConfig("Wawla-Entity", showHorseJump);
        register.addConfig("Wawla-Entity", showHorseSpeed);
        register.addConfig("Wawla-Entity", showZombieType);
        register.addConfig("Wawla-Entity", showPlayerLevel);
    }

    private static String showHorseJump = "wawla.horse.showJump";
    private static String showHorseSpeed = "wawla.horse.showSpeed";

    private static String showZombieType = "wawla.villagerZombie";
    
    private static String showPlayerLevel = "wawla.playerLevel";
}
