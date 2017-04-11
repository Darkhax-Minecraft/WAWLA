package net.darkhax.wawla.plugins.generic;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.config.Configurable;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityTameable;

@WawlaFeature(description = "Shows info about tameable mobs", name = "tameable", type = ProviderType.ENTITY)
public class PluginTameable extends InfoProvider {

    @Configurable(category = "tameable", description = "Show when a pet mob is tamed?")
    public static boolean showTamed = true;

    @Configurable(category = "tameable", description = "Show when a pet mob is sitting?")
    public static boolean showSitting = true;

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof EntityTameable) {

            final EntityTameable entity = (EntityTameable) data.entity;

            if (showTamed && entity.isTamed())
                info.add(ChatFormatting.YELLOW + I18n.format("tooltip.wawla.generic.tamed"));

            if (showSitting && entity.isSitting())
                info.add(I18n.format("tooltip.wawla.generic.sitting"));
        }
    }
}
