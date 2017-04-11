package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;

@WawlaFeature(description = "Shows the blast resistance of a block", name = "blastres", type = ProviderType.BLOCK)
public class PluginBlastResistance extends InfoProvider {

    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {

        info.add(I18n.format("tooltip.wawla.generic.blastresist") + ": " + data.block.getExplosionResistance(data.world, data.pos, null, null) * 5F);
    }

    @Override
    public boolean enabledByDefault () {

        return false;
    }
}