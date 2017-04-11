package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;

public class PluginHardness extends InfoProvider {

    private static boolean enabled = false;

    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {

        if (enabled)
            info.add(I18n.format("tooltip.wawla.generic.hardness") + ": " + data.block.getBlockHardness(data.state, data.world, data.pos));
    }

    @Override
    public void syncConfig (Configuration config) {

        enabled = config.getBoolean("Hardness", "generic_tiles", false, "If this is enabled, the hud will display the hardness of a block while looking at it.");
    }
}