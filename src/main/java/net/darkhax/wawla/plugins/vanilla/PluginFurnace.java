package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

public class PluginFurnace extends InfoProvider {

    private static boolean enabled = true;
    private static boolean fuel = true;
    private static boolean input = true;
    private static boolean output = true;
    private static boolean burntime = true;

    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {

        if (enabled)
            if (data.world.getTileEntity(data.pos) instanceof TileEntityFurnace) {

                if (input) {

                    final ItemStack stack = new ItemStack(data.tag.getCompoundTag("InputStack"));

                    if (!stack.isEmpty())
                        info.add(I18n.format("tooltip.wawla.vanilla.input") + ": " + stack.getDisplayName() + " * " + stack.getCount());
                }

                if (fuel) {

                    final ItemStack stack = new ItemStack(data.tag.getCompoundTag("FuelStack"));

                    if (!stack.isEmpty())
                        info.add(I18n.format("tooltip.wawla.vanilla.fuel") + ": " + stack.getDisplayName() + " * " + stack.getCount());
                }

                if (output) {

                    final ItemStack stack = new ItemStack(data.tag.getCompoundTag("OutputStack"));

                    if (!stack.isEmpty())
                        info.add(I18n.format("tooltip.wawla.vanilla.output") + ": " + stack.getDisplayName() + " * " + stack.getCount());
                }

                if (burntime) {

                    final int time = data.tag.getInteger("BurnTime");

                    if (time > 0)
                        info.add(I18n.format("tooltip.wawla.vanilla.burntime") + ": " + StringUtils.ticksToElapsedTime(time));
                }
            }
    }

    @Override
    public void writeTileNBT (World world, TileEntity tile, NBTTagCompound tag) {

        if (tile instanceof TileEntityFurnace) {

            final TileEntityFurnace furnace = (TileEntityFurnace) tile;
            ItemStack stack = null;

            if (input) {

                stack = furnace.getStackInSlot(0);

                if (stack != null)
                    InfoProvider.writeStackToTag(stack, "InputStack", tag);
            }

            if (fuel) {

                stack = furnace.getStackInSlot(1);

                if (stack != null)
                    InfoProvider.writeStackToTag(stack, "FuelStack", tag);
            }

            if (output) {

                stack = furnace.getStackInSlot(2);

                if (stack != null)
                    InfoProvider.writeStackToTag(stack, "OutputStack", tag);
            }

            tag.setInteger("BurnTime", furnace.getField(0));
        }
    }

    @Override
    public boolean requireTileSync (World world, TileEntity tile) {

        return enabled && tile instanceof TileEntityFurnace;
    }

    @Override
    public void syncConfig (Configuration config) {

        enabled = config.getBoolean("Furnace", "vanilla_tiles", true, "If this is enabled, the hud will display info about furnaces.");
        fuel = config.getBoolean("Furnace_Fuel", "vanilla_tiles", true, "If this is enabled, the hud will show fuel items in a furnace.");
        input = config.getBoolean("Furnace_Input", "vanilla_tiles", true, "If this is enabled, the hud will show input items in a furnace.");
        output = config.getBoolean("Furnace_Output", "vanilla_tiles", true, "If this is enabled, the hud will show output items in a furnace.");
        burntime = config.getBoolean("Furnace_Burn_Time", "vanilla_tiles", true, "If this is enabled, the hud will show how much longer the furnace will burn.");
    }
}