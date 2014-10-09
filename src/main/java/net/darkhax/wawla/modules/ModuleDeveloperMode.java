package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ModuleDeveloperMode extends Module {

    private static String developerOn = "wawla.dev.devOn";
    private static String showAllBlockNBT = "wawla.dev.blockNBT";
    private static String showBlockClass = "wawla.dev.blockClass";
    private static String showTEClass = "wawla.dev.teClass";
    private static String showBlockID = "wawla.dev.blockID";
    private static String showAllEntityNBT = "wawla.dev.entityNBT";
    private static String showEntityClass = "wawla.dev.entityClass";
    private static String showEntitySize = "wawla.dev.entitySize";

    public ModuleDeveloperMode(boolean enabled) {

        super(enabled);
    }

    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        if (config.getConfig(developerOn)) {

            if (access.getTileEntity() != null) {

                if (config.getConfig(showAllBlockNBT))
                    Utilities.wrapStringToList(access.getNBTData().toString(), 40, true, tooltip);

                if (config.getConfig(showTEClass))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.dev.teClass") + " " + Utilities.upperCase(access.getTileEntity().getClass().toString()));
            }

            if (access.getBlock() != null) {

                if (config.getConfig(showBlockClass))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.dev.blockClass") + " " + Utilities.upperCase(access.getBlock().getClass().toString()));

                if (config.getConfig(showBlockID))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.dev.blockID") + ": " + Block.blockRegistry.getNameForObject(access.getBlock()) + " (" + Block.getIdFromBlock(access.getBlock()) + ":" + access.getWorld().getBlockMetadata(access.getPosition().blockX, access.getPosition().blockY, access.getPosition().blockZ) + ")");
            }
        }
    }

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor access, IWailaConfigHandler config) {

        if (config.getConfig(developerOn) && entity != null) {

            if (config.getConfig(showAllEntityNBT))
                Utilities.wrapStringToList(access.getNBTData().toString(), 40, true, tooltip);

            if (config.getConfig(showEntityClass))
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.dev.entityClass") + " " + entity.getClass());

            if (config.getConfig(showEntitySize))
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.dev.width") + ": " + entity.width + " " + StatCollector.translateToLocal("tooltip.wawla.dev.height") + ": " + entity.height);
        }
    }

    public void onWailaRegistrar(IWailaRegistrar register) {

        register.addConfig("Wawla-Developer", developerOn, false);
        register.addConfig("Wawla-Developer", showAllBlockNBT, false);
        register.addConfig("Wawla-Developer", showBlockClass, false);
        register.addConfig("Wawla-Developer", showTEClass, false);
        register.addConfig("Wawla-Developer", showBlockID, false);
        register.addConfig("Wawla-Developer", showAllEntityNBT, false);
        register.addConfig("Wawla-Developer", showEntityClass, false);
        register.addConfig("Wawla-Developer", showEntitySize, false);
    }
}
