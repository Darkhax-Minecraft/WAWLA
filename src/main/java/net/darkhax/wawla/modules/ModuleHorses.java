package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.StatCollector;

public class ModuleHorses extends Module {

    public ModuleHorses(boolean enabled) {

        super(enabled);
    }

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        if (entity instanceof EntityHorse) {

            EntityHorse horse = (EntityHorse) entity;

            if (config.getConfig("wawla.horse.showJump"))
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.jumpStrength") + ": " + Utilities.round(horse.getHorseJumpStrength(), 4));

            if (config.getConfig("wawla.horse.showSpeed"))
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.speed") + ": " + Utilities.round(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue(), 4));
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.addConfig("Wawla-Entity", "wawla.horse.showJump");
        register.addConfig("Wawla-Entity", "wawla.horse.showSpeed");
    }
}
