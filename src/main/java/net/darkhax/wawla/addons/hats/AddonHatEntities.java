package net.darkhax.wawla.addons.hats;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class AddonHatEntities implements IWailaEntityProvider {

    static HashMap<File, String> hatMap = null;

    @Override
    public Entity getWailaOverride(IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return data.getEntity();
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public List<String> getWailaBody(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        if (cfg.getConfig(showHats) && data.getNBTData() != null && data.getNBTData().hasKey("ForgeData")) {

            NBTTagCompound forgeTag = data.getNBTData().getCompoundTag("ForgeData");

            if (forgeTag.hasKey("Hats_hatInfo") && !forgeTag.getString("Hats_hatInfo").isEmpty()) {

                String hatName = getHatFromList(forgeTag.getString("Hats_hatInfo"));
                tip.add(StatCollector.translateToLocal("tooltip.wawla.hats") + ": " + hatName.substring(0, hatName.length() - 4));
            }
        }
        return tip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {

        if (entity != null)
            entity.writeToNBT(tag);

        return tag;
    }

    public static void registerAddon(IWailaRegistrar register) {

        AddonHatEntities dataProvider = new AddonHatEntities();
        register.registerBodyProvider(dataProvider, Entity.class);
        register.registerNBTProvider(dataProvider, Entity.class);
        register.addConfig("Wawla-Entity", showHats);

        try {

            Class classHatHandler = Class.forName("hats.common.core.HatHandler");
            Method getHatNames = null;

            for (Method method : classHatHandler.getDeclaredMethods())
                if (method.getName().equalsIgnoreCase("getHatNames"))
                    getHatNames = method;

            hatMap = (HashMap<File, String>) getHatNames.invoke(null);
        }

        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        catch (IllegalAccessException e) {

            e.printStackTrace();
        }

        catch (IllegalArgumentException e) {

            e.printStackTrace();
        }

        catch (InvocationTargetException e) {

            e.printStackTrace();
        }
    }

    private static String showHats = "wawla.showHat";

    public String getHatFromList(String name) {

        for (Map.Entry entry : hatMap.entrySet()) {

            String value = (String) entry.getValue();
            if (value.equalsIgnoreCase(name)) {

                File hat = (File) entry.getKey();
                return hat.getName();
            }
        }

        return "null";
    }
}
