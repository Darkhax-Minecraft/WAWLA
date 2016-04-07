package net.darkhax.wawla.plugins.generic.tiles;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.util.text.translation.I18n;

public class PluginEnchantmentPower extends InfoProvider {

	@Override
	public void addTileInfo(List<String> info, InfoAccess data) {

        final float enchPower = data.block.getEnchantPowerBonus(data.world, data.pos);
        
        if (enchPower > 0)
            info.add(I18n.translateToLocal("tooltip.wawla.generic.enchpower") + ": " + enchPower);
	}
}