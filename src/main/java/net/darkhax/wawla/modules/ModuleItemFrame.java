package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.StatCollector;

public class ModuleItemFrame extends Module {

    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

        if (entity instanceof EntityItemFrame) {

            EntityItemFrame frame = (EntityItemFrame) entity;

            if (frame.getDisplayedItem() != null)
                tooltip.add(StatCollector.translateToLocal("tooltip.item") + ": " + frame.getDisplayedItem().getDisplayName());
        }
    }
}