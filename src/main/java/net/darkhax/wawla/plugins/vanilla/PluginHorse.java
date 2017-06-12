package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.config.Configurable;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;

@WawlaFeature(description = "Shows info about horses", name = "horses", type = ProviderType.ENTITY)
public class PluginHorse extends InfoProvider {

    @Configurable(category = "horses", description = "Show the jump strength of the horse, relative ot the player.")
    public static boolean jump = true;

    @Configurable(category = "horses", description = "Show the speed of the horse, relative ot the player.")
    public static boolean speed = true;

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof EntityHorse) {

            final EntityHorse horse = (EntityHorse) data.entity;

            if (jump) {
                info.add(I18n.format("tooltip.wawla.vanilla.jump") + ": " + this.getPlayerRelativeInfo(horse.getHorseJumpStrength(), 0.45d));
            }

            if (speed) {
                info.add(I18n.format("tooltip.wawla.vanilla.speed") + ": " + this.getPlayerRelativeInfo(horse.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(), 0.1d));
            }
        }
    }

    public String getPlayerRelativeInfo (double horseStat, double playerStat) {

        return InfoProvider.round(horseStat / playerStat, 1) + "X";
    }
}
