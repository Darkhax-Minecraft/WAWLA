package net.darkhax.wawla.plugins.generic;

import java.util.List;

import net.darkhax.icse.lib.Constants;
import net.darkhax.icse.lib.Utilities;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.EntityList;

public class PluginDevEntity extends InfoProvider {
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        info.add("Entity Class: " + data.entity.getClass().getCanonicalName());
        info.add("Entity ID: " + EntityList.getEntityString(data.entity));
        
        if (data.player.isSneaking()) {
            Utilities.wrapStringToList(data.tag.toString(), 50, true, info);
            Constants.LOG.info(data.entity.getClass().getCanonicalName());
        }
    }
}
