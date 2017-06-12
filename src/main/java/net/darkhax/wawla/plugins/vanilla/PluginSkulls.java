package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;

@WawlaFeature(description = "Shows info about player heads", name = "skulls", type = ProviderType.BLOCK)
public class PluginSkulls extends InfoProvider {

    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {

        final TileEntity tile = data.world.getTileEntity(data.pos);

        if (tile instanceof TileEntitySkull) {

            final TileEntitySkull skull = (TileEntitySkull) tile;

            if (skull.getPlayerProfile() != null && skull.getPlayerProfile().getName() != null) {
                info.add(I18n.format("tooltip.wawla.name") + ": " + skull.getPlayerProfile().getName());
            }
        }
    }
}