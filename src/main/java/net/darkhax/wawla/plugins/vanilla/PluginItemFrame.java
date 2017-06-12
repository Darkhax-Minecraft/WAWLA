package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;

@WawlaFeature(description = "Shows info about item frames", name = "itemframes", type = ProviderType.ENTITY)
public class PluginItemFrame extends InfoProvider {

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof EntityItemFrame) {

            final ItemStack stack = ((EntityItemFrame) data.entity).getDisplayedItem();

            if (!stack.isEmpty()) {
                info.add(I18n.format("tooltip.wawla.item") + ": " + stack.getDisplayName());
            }
        }
    }
}
