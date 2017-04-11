package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class PluginVillagerTypes extends InfoProvider {
    
    private static boolean enabled = true;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled) {
            
            String career = "";
            
            if (data.entity instanceof EntityVillager)
                career = ((EntityVillager) data.entity).getProfessionForge().getRegistryName().getResourcePath();
            
            else if (data.entity instanceof EntityZombieVillager) {
            	
            	String forgeCareer = data.tag.getString("WAWLAZombieType");
            	
            	career = forgeCareer.isEmpty() ? I18n.format("villager.wawla.zombie") : forgeCareer;
            }
            
            else if (data.entity instanceof EntityWitch)
                career = I18n.format("villager.wawla.witch");
            
            if (career != null && !career.isEmpty())
                info.add(I18n.format("tooltip.wawla.vanilla.career") + ": " + career);
        }
    }
    
    @Override
    public void writeEntityNBT (World world, Entity entity, NBTTagCompound tag) {
        
    	if (entity instanceof EntityZombieVillager) {
    		
    		EntityZombieVillager zombie = (EntityZombieVillager) entity;
    		VillagerProfession type = zombie.getForgeProfession();
    		
    		if (type != null)
    			tag.setString("WAWLAZombieType", type.getRegistryName().getResourcePath());
    	}
    }
    
    @Override
    public boolean requireEntitySync (World world, Entity entity) {
        
        return (entity instanceof EntityZombieVillager);
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Villager_Career", "vanilla_entities", true, "When enabled, shows the career type of villagers on the HUD.");
    }
}
