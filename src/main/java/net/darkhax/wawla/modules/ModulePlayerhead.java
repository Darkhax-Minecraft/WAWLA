package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.BlockSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;

public class ModulePlayerhead extends Module {

    public ModulePlayerhead(boolean enabled) {

        super(enabled);
    }

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        if (access.getTileEntity() != null && access.getBlock() instanceof BlockSkull && config.getConfig("wawla.showhead")) {

            // This GameProfile stuff is cool but weird. func_1524259_a is a nbt thing for creating a
            // GameProfile using nbt.
            tooltip.add(StatCollector.translateToLocal("tooltip.wawla.owner") + ": " + NBTUtil.func_152459_a(access.getNBTData().getCompoundTag("Owner")).getName());
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.addConfig("Wawla", "wawla.showhead");
    }
}