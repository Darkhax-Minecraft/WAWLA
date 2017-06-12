package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

@WawlaFeature(description = "Shows the enchantment power of a block", name = "enchpower", type = ProviderType.ITEM_BLOCK)
public class PluginEnchantmentPower extends InfoProvider {

    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {

        final float enchPower = data.block.getEnchantPowerBonus(data.world, data.pos);

        if (enchPower > 0) {
            info.add(I18n.format("tooltip.wawla.generic.enchpower") + ": " + enchPower);
        }
    }

    @Override
    public void addItemInfo (List<String> info, ItemStack stack, ITooltipFlag flag, EntityPlayer entityPlayer) {

        final Block block = Block.getBlockFromItem(stack.getItem());

        if (block != null) {
            try {

                final float enchPower = block.getEnchantPowerBonus(entityPlayer.world, BlockPos.ORIGIN);

                if (enchPower > 0) {
                    info.add(I18n.format("tooltip.wawla.enchPower") + ": " + enchPower);
                }
            }

            catch (final Exception exception) {

            }
        }
    }
}