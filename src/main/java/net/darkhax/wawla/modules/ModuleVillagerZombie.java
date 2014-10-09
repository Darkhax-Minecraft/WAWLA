package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;

public class ModuleVillagerZombie extends Module {

    public ModuleVillagerZombie(boolean enabled) {

        super(enabled);
    }

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        if (entity instanceof EntityZombie && config.getConfig("wawla.villagerZombie")) {

            EntityZombie zombie = (EntityZombie) entity;
            if (zombie.isVillager())
                tooltip.add("Zombie Villager");
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.addConfig("Wawla-Entity", "wawla.villagerZombie");
    }
}