package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaEntityAccessor;
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
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

        if (entity instanceof EntityHorse) {

            EntityHorse horse = (EntityHorse) entity;
            tooltip.add(StatCollector.translateToLocal("tooltip.wawla.jumpStrength") + ": " + Utilities.round(horse.getHorseJumpStrength(), 4));
            tooltip.add(StatCollector.translateToLocal("tooltip.wawla.speed") + ": " + Utilities.round(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue(), 4));
        }
    }
}
