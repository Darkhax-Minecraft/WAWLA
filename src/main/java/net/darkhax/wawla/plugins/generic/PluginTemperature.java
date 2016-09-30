package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class PluginTemperature extends InfoProvider {
    
    private static boolean enabled = true;
    
    @Override
    public void addItemInfo (List<String> info, ItemStack stack, boolean advanced, EntityPlayer entityPlayer) {
        
        final FluidStack fluid = FluidUtil.getFluidContained(stack);
        
        if (enabled && fluid != null)
            info.add(I18n.format("tooltip.wawla.generic.temperature", fluid.getFluid().getTemperature(fluid)));
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Temperature", "generic_items", true, "Shows the temperature of the fluid held by items?");
    }
}
