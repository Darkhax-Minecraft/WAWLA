package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.BlockFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;

public class ModuleFurnace extends Module {

    public ModuleFurnace(boolean enabled) {

        super(enabled);
    }

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        if (access.getTileEntity() != null && access.getTileEntity() instanceof TileEntityFurnace) {

            TileEntityFurnace furnace = (TileEntityFurnace) access.getTileEntity();
            int burnTime = access.getNBTData().getInteger("BurnTime") / 20;

            if (burnTime > 0 && config.getConfig("wawla.furnace.burntime"))
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.burnTime") + ": " + burnTime + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));

            if (access.getPlayer().isSneaking()) {

                ItemStack[] furnaceStacks = Utilities.getInventoryStacks(access.getNBTData(), 3);

                if (furnaceStacks[0] != null && config.getConfig("wawla.furnace.input"))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.input") + ": " + furnaceStacks[0].getDisplayName() + " X " + furnaceStacks[0].stackSize);

                if (furnaceStacks[1] != null && config.getConfig("wawla.furnace.fuel"))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.fuel") + ": " + furnaceStacks[1].getDisplayName() + " X " + furnaceStacks[1].stackSize);

                if (furnaceStacks[2] != null && config.getConfig("wawla.furnace.output"))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.output") + ": " + furnaceStacks[2].getDisplayName() + " X " + furnaceStacks[2].stackSize);
            }
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.addConfig("Wawla-Blocks", "wawla.furnace.input");
        register.addConfig("Wawla-Blocks", "wawla.furnace.fuel");
        register.addConfig("Wawla-Blocks", "wawla.furnace.output");
        register.addConfig("Wawla-Blocks", "wawla.furnace.burntime");
        register.registerSyncedNBTKey("*", BlockFurnace.class);
    }
}