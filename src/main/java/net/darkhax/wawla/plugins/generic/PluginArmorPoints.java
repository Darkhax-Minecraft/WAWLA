package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;

@WawlaFeature(description = "Shows information about armor points", name = "armor", type = ProviderType.ENTITY)
public class PluginArmorPoints extends InfoProvider {

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof EntityLivingBase) {

            final EntityLivingBase entity = (EntityLivingBase) data.entity;
            final int armorPoints = entity.getTotalArmorValue();

            if (armorPoints > 0)
                info.add(I18n.format("tooltip.wawla.generic.armor") + ": " + armorPoints);
        }
    }
}
