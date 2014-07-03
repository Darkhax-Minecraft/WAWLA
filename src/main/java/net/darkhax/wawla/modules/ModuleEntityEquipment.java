package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.StatCollector;

public class ModuleEntityEquipment extends Module {

    private static String[] itemTypes = { "heldItem", "feet", "leggings", "chestplate", "helmet" };

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

        if (entity instanceof EntityLiving) {

            EntityLiving living = (EntityLiving) entity;

            for (int i = 0; i < 5; i++) {

                if (living.getEquipmentInSlot(i) != null)
                    tooltip.add(StatCollector.translateToLocal("tooltip." + itemTypes[i]) + ": " + living.getEquipmentInSlot(i).getDisplayName());
            }
        }
    }
}