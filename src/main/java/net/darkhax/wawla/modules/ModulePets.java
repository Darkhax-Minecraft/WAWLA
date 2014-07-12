package net.darkhax.wawla.modules;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaEntityAccessor;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ModulePets extends Module {

    public static ArrayList<String> nbtNames = new ArrayList<String>();

    public ModulePets() {

        nbtNames.add("Owner");
        nbtNames.add("OwnerName");
        nbtNames.add("owner");
        nbtNames.add("ownerName");
    }

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

        NBTTagCompound tag = Utilities.convertEntityToNbt(entity);
        NBTTagCompound extTag = entity.getEntityData();
        for (String currentKey : nbtNames) {

            if (tag.hasKey(currentKey) && !tag.getString(currentKey).isEmpty())
                tooltip.add(StatCollector.translateToLocal("tooltip.owner") + ": " + tag.getString(currentKey));

            if (extTag.hasKey(currentKey) && !extTag.getString(currentKey).isEmpty())
                tooltip.add(StatCollector.translateToLocal("tooltip.owner") + ": " + extTag.getString(currentKey));
        }
    }
}