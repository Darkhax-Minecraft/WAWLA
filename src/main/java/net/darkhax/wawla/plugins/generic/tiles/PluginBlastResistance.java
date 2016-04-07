package net.darkhax.wawla.plugins.generic.tiles;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.util.text.translation.I18n;

public class PluginBlastResistance extends InfoProvider {

	@Override
	public void addTileInfo(List<String> info, InfoAccess data) {

		info.add(I18n.translateToLocal("tooltip.wawla.generic.blastresist") + ": " + data.block.blockResistance / 5f);
	}
}