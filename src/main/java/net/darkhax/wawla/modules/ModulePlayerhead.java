package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.BlockSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;

public class ModulePlayerhead extends Module {

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access) {

        if (access.getBlock() instanceof BlockSkull) {

            MovingObjectPosition pos = access.getPosition();
            TileEntitySkull skull = (TileEntitySkull) access.getWorld().getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
            // func_145907_c is a currently unmapped method that returns field_145909_j. field_145909_j
            // is an unmapped variable that is equal to the value of the ExtraType tag on the tile
            // entity.
            tooltip.add(StatCollector.translateToLocal("tooltip.owner") + ": " + skull.func_145907_c());
        }
    }
}