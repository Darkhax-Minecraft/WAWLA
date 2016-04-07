package net.darkhax.wawla.plugins.generic.tiles;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.translation.I18n;

public class PluginBreakProgression extends InfoProvider {

	@Override
	public void addTileInfo(List<String> info, InfoAccess data) {

		double progress = Minecraft.getMinecraft().playerController.curBlockDamageMP;

		if (progress > 0.0d)
			info.add(I18n.translateToLocal("tooltip.wawla.generic.progression") + ": " + (int) (progress * 100) + "%");
	}
}