package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaDataAccessor;
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
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access) {

        if (access.getBlock() instanceof BlockSkull) {

            MovingObjectPosition pos = access.getPosition();
            TileEntitySkull skull = (TileEntitySkull) access.getTileEntity();
            // This GameProfile stuff is cool but weird. func_1524259_a is a nbt thing for creating a
            // GameProfile using nbt.
            tooltip.add(StatCollector.translateToLocal("tooltip.wawla.owner") + ": " + NBTUtil.func_152459_a(access.getNBTData().getCompoundTag("Owner")).getName());
        }
    }
}