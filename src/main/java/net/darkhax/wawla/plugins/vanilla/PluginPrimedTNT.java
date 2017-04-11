package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.StringUtils;

@WawlaFeature(description = "Shows info about primed tnt", name = "tnt", type = ProviderType.ENTITY)
public class PluginPrimedTNT extends InfoProvider {

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof EntityTNTPrimed)
            info.add(I18n.format("tooltip.wawla.vanilla.fuse") + ": " + StringUtils.ticksToElapsedTime(((EntityTNTPrimed) data.entity).getFuse()));
    }
}
