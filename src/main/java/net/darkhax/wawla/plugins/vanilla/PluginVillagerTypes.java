package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

@WawlaFeature(description = "Shows info about villager professions", name = "professions", type = ProviderType.ENTITY)
public class PluginVillagerTypes extends InfoProvider {

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        String career = "";

        if (data.entity instanceof EntityVillager)
            career = ((EntityVillager) data.entity).getProfessionForge().getRegistryName().getResourcePath();

        else if (data.entity instanceof EntityZombieVillager) {

            final String forgeCareer = data.tag.getString("WAWLAZombieType");

            career = forgeCareer.isEmpty() ? I18n.format("villager.wawla.zombie") : forgeCareer;
        }

        else if (data.entity instanceof EntityWitch)
            career = I18n.format("villager.wawla.witch");

        if (career != null && !career.isEmpty())
            info.add(I18n.format("tooltip.wawla.vanilla.career") + ": " + career);
    }

    @Override
    public void writeEntityNBT (World world, Entity entity, NBTTagCompound tag) {

        if (entity instanceof EntityZombieVillager) {

            final EntityZombieVillager zombie = (EntityZombieVillager) entity;
            final VillagerProfession type = zombie.getForgeProfession();

            if (type != null)
                tag.setString("WAWLAZombieType", type.getRegistryName().getResourcePath());
        }
    }

    @Override
    public boolean requireEntitySync (World world, Entity entity) {

        return entity instanceof EntityZombieVillager;
    }
}
