package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityXPOrb;

@WawlaFeature(description = "Shows the amount of exp in exp orbs", name = "exp", type = ProviderType.ENTITY)
public class PluginEXPOrb extends InfoProvider {

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof EntityXPOrb)
            info.add(I18n.format("tooltip.wawla.vanilla.experience") + ": " + ((EntityXPOrb) data.entity).xpValue);
    }
}
