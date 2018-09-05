package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@WawlaFeature(description = "Shows debug tile information", name = "debugTiles", type = ProviderType.BLOCK)
public class PluginDebugTiles extends InfoProvider {

    @Override
    @SideOnly(Side.CLIENT)
    public void addTileInfo (List<String> info, InfoAccess data) {

        if (data.player.isCreative() && Minecraft.getMinecraft().gameSettings.advancedItemTooltips) {

            info.add("Class: " + data.block.getClass());
            info.add("ID: " + data.block.getRegistryName().toString());

            final TileEntity tile = data.world.getTileEntity(data.pos);

            if (tile != null) {

                info.add("Tile Class: " + tile.getClass());
                info.add("Tile ID: " + TileEntity.getKey(tile.getClass()).toString());
            }
        }
    }
}