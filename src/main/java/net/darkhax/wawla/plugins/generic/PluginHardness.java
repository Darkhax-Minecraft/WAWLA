package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;

@WawlaFeature(description = "Shows the hardness of the block", name = "hardness", type = ProviderType.BLOCK)
public class PluginHardness extends InfoProvider {

    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {

        info.add(I18n.format("tooltip.wawla.generic.hardness") + ": " + data.block.getBlockHardness(data.state, data.world, data.pos));
    }

    @Override
    public boolean enabledByDefault () {

        return false;
    }
}