package net.darkhax.wawla.plugins.generic;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class PluginFluidDescription extends InfoProvider {

    private static boolean showTemp = true;
    private static boolean showOwner = true;

    @Override
    public void addItemInfo (List<String> info, ItemStack stack, boolean advanced, EntityPlayer entityPlayer) {

        final FluidStack fluid = FluidUtil.getFluidContained(stack);

        if (showTemp && fluid != null)
            info.add(I18n.format("tooltip.wawla.generic.temperature", fluid.getFluid().getTemperature(fluid)));

        if (showOwner && fluid != null)
            info.add(I18n.format("tooltip.wawla.enchdesc.addedby") + ": " + ChatFormatting.BLUE + getModName(FluidRegistry.getDefaultFluidName(fluid.getFluid()).split(":")[0]));

    }

    @Override
    public void syncConfig (Configuration config) {

        showTemp = config.getBoolean("FluidTemperature", "generic_items", true, "Shows the temperature of the fluid held by items?");
        showOwner = config.getBoolean("FluidOwner", "generic_items", true, "Shows the name of the mod which added the fluid?");
    }

    public static String getModName (String modID) {

        final ModContainer mod = Loader.instance().getIndexedModList().get(modID);
        return mod != null ? mod.getName() : modID;
    }
}
