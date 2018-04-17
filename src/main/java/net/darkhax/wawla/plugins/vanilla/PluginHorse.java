package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.config.Configurable;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AbstractHorse;

@WawlaFeature(description = "Shows info about horses", name = "horses", type = ProviderType.ENTITY)
public class PluginHorse extends InfoProvider {

    @Configurable(category = "horses", description = "Show the jump strength of the horse, relative ot the player.")
    public static boolean jump = true;

    @Configurable(category = "horses", description = "Show the speed of the horse, relative ot the player.")
    public static boolean speed = true;

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof AbstractHorse) {

            final AbstractHorse horse = (AbstractHorse) data.entity;

            if (jump) {
            	
            	final double horseJump = horse.getHorseJumpStrength();
                info.add(I18n.format("tooltip.wawla.vanilla.jump", InfoProvider.round(horseJump, 4), this.getPlayerRelativeInfo(horseJump, 0.45d)));
            }

            if (speed) {
            	
            	final double horseSpeed = horse.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
                info.add(I18n.format("tooltip.wawla.vanilla.speed", InfoProvider.round(horseSpeed, 4), this.getPlayerRelativeInfo(horseSpeed, 0.1d)));
            }
        }
    }

    public double getPlayerRelativeInfo (double horseStat, double playerStat) {

        return InfoProvider.round(horseStat / playerStat, 1);
    }
}
