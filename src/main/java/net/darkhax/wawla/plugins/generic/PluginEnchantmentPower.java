package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;

public class PluginEnchantmentPower extends InfoProvider {
    
    private static boolean enabled = true;
    
    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {
        
        if (enabled) {
            
            final float enchPower = data.block.getEnchantPowerBonus(data.world, data.pos);
            
            if (enchPower > 0)
                info.add(I18n.format("tooltip.wawla.generic.enchpower") + ": " + enchPower);
        }
    }
    
    @Override
    public void addItemInfo (List<String> info, ItemStack stack, boolean advanced, EntityPlayer entityPlayer) {
        
        if (enabled) {
            
            final Block block = Block.getBlockFromItem(stack.getItem());
            
            if (block != null)
                try {
                    
                    final float enchPower = block.getEnchantPowerBonus(entityPlayer.worldObj, BlockPos.ORIGIN);
                    
                    if (enchPower > 0)
                        info.add(I18n.format("tooltip.wawla.enchPower") + ": " + enchPower);
                }
                
                catch (final Exception exception) {
                    
                }
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("EnchantPower", "generic_tiles", true, "If this is enabled, the hud will display the enchant power of a block while looking at it.");
    }
}