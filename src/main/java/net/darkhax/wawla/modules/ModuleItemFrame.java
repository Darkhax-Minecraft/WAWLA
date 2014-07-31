package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.StatCollector;

public class ModuleItemFrame extends Module {

    public ModuleItemFrame(boolean enabled) {

        super(enabled);
    }

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        if (entity instanceof EntityItemFrame) {

            EntityItemFrame frame = (EntityItemFrame) entity;

            if (frame.getDisplayedItem() != null && config.getConfig("wawla.frame.showItem"))
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.item") + ": " + frame.getDisplayedItem().getDisplayName());
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.addConfig("Wawla", "wawla.frame.showItem");
    }
}