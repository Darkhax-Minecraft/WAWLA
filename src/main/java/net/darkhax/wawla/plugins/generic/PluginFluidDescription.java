package net.darkhax.wawla.plugins.generic;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.config.Configurable;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

@WawlaFeature(description = "Shows fluid info on fluid container items", name = "fluidcontainer", type = ProviderType.ITEM)
public class PluginFluidDescription extends InfoProvider {

    @Configurable(category = "fluidcontainer", description = "Should the temperature of the fluid be shown?")
    public static boolean showTemp = true;

    @Configurable(category = "fluidcontainer", description = "Should the owner of the fluid be shown?")
    public static boolean showOwner = true;

    @Override
    public void addItemInfo (List<String> info, ItemStack stack, ITooltipFlag flag, EntityPlayer entityPlayer) {

        final FluidStack fluid = FluidUtil.getFluidContained(stack);

        if (showTemp && fluid != null) {
            info.add(I18n.format("tooltip.wawla.generic.temperature", fluid.getFluid().getTemperature(fluid)));
        }

        if (showOwner && fluid != null) {
            info.add(I18n.format("tooltip.wawla.enchdesc.addedby") + ": " + ChatFormatting.BLUE + getModName(FluidRegistry.getDefaultFluidName(fluid.getFluid()).split(":")[0]));
        }
    }

    public static String getModName (String modID) {

        final ModContainer mod = Loader.instance().getIndexedModList().get(modID);
        return mod != null ? mod.getName() : modID;
    }
}
