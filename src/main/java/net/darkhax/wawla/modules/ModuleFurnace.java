package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ModuleFurnace extends Module {

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access) {

        if (access.getTileEntity() != null && access.getTileEntity() instanceof TileEntityFurnace) {

            tooltip.add("Time: " + access.getNBTData().getInteger("BurnTime"));
        }
    }
}