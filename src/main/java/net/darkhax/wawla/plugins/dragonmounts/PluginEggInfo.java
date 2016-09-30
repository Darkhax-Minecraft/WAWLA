package net.darkhax.wawla.plugins.dragonmounts;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import info.ata4.minecraft.dragon.DragonMounts;
import info.ata4.minecraft.dragon.server.block.BlockDragonBreedEgg;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Optional.Method;

public class PluginEggInfo extends InfoProvider {
    
    private boolean enabled = true;
    
    @Override
    @Method(modid = "DragonMounts")
    public void addTileInfo (List<String> info, InfoAccess data) {
        
        if (this.enabled && (data.block instanceof BlockDragonEgg && !DragonMounts.instance.getConfig().isDisableBlockOverride() || data.block instanceof BlockDragonBreedEgg))
            info.add(ChatFormatting.YELLOW + I18n.format("tooltip.wawla.dragonmounts.start"));
    }
    
    @Override
    @Method(modid = "DragonMounts")
    public void syncConfig (Configuration config) {
        
        this.enabled = config.getBoolean("StartHatching", "dragon_mounts", true, "If this is enabled, the hud will tell players to right click to start the hatching process.");
    }
}
