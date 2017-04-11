package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

@WawlaFeature(description = "Shows how close blocks are to breaking", name = "breakprogress", type = ProviderType.BLOCK)
public class PluginBreakProgression extends InfoProvider {

    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {

        final double progress = Minecraft.getMinecraft().playerController.curBlockDamageMP;

        if (progress > 0.0d)
            info.add(I18n.format("tooltip.wawla.generic.progression") + ": " + (int) (progress * 100) + "%");
    }
}