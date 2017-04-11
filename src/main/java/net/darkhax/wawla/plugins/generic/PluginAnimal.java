package net.darkhax.wawla.plugins.generic;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.config.Configurable;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

@WawlaFeature(description = "Shows information about breedable animals", name = "animals", type = ProviderType.ENTITY)
public class PluginAnimal extends InfoProvider {

    @Configurable(category = "animals", description = "Should the breedting timer be shown?")
    public static boolean showBreedingCooldown = true;

    @Configurable(category = "animals", description = "Should the growing timer be shown?")
    public static boolean showGrowingCooldown = true;

    @Configurable(category = "animals", description = "Should the correct breeding item reflect in the hud?")
    public static boolean showBreedingItem = true;

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof EntityAnimal) {

            final EntityAnimal entity = (EntityAnimal) data.entity;
            final int age = data.tag.getInteger("AnimalGrowingAge");

            if (age != 0) {

                if (showBreedingCooldown && age < 0)
                    info.add(I18n.format("tooltip.wawla.generic.growingage") + ": " + StringUtils.ticksToElapsedTime(Math.abs(age)));

                if (showGrowingCooldown && age > 0)
                    info.add(I18n.format("tooltip.wawla.generic.breedingtime") + ": " + StringUtils.ticksToElapsedTime(age));
            }

            if (showBreedingItem && data.player.getHeldItemMainhand() != null && entity.isBreedingItem(data.player.getHeldItemMainhand()))
                info.add(ChatFormatting.YELLOW + I18n.format("tooltip.wawla.generic.breedingitem"));
        }
    }

    @Override
    public void writeEntityNBT (World world, Entity entity, NBTTagCompound tag) {

        if (entity instanceof EntityAnimal) {

            final EntityAnimal animal = (EntityAnimal) entity;

            if (showBreedingCooldown || showGrowingCooldown)
                tag.setInteger("AnimalGrowingAge", animal.getGrowingAge());
        }
    }

    @Override
    public boolean requireEntitySync (World world, Entity entity) {

        return entity instanceof EntityAnimal;
    }
}
