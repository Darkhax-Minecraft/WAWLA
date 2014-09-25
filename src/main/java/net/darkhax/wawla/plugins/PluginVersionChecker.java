package net.darkhax.wawla.plugins;

import net.darkhax.wawla.util.Constants;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Adds support for Dynious's Version Checker mod. Support will only be added if the Version Checker mod
 * is installed. To find out more information about the Version Checker mod visit
 * http://www.minecraftforum.net/topic/2721902
 */
public class PluginVersionChecker {

    public PluginVersionChecker(boolean enabled) {

        if (enabled) {

            String versionLink = "https://dl.dropboxusercontent.com/u/38575752/files/versions/Wawla%20Version%20Info.txt";
            FMLInterModComms.sendRuntimeMessage(Constants.MODID, "VersionChecker", "addVersionCheck", versionLink);
        }
    }
}