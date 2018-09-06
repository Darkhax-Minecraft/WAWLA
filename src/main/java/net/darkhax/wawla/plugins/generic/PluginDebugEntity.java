package net.darkhax.wawla.plugins.generic;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@WawlaFeature(description = "Shows debug entity information", name = "debugEntities", type = ProviderType.ENTITY)
public class PluginDebugEntity extends InfoProvider {

    @Override
    @SideOnly(Side.CLIENT)
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.player.isCreative() && Minecraft.getMinecraft().gameSettings.advancedItemTooltips) {

            final KeyBinding keyBindSneak = Minecraft.getMinecraft().gameSettings.keyBindSneak;
            
            info.add("Class: " + data.entity.getClass());
            info.add("ID: " + EntityList.getKey(data.entity).toString());

            if (GameSettings.isKeyDown(keyBindSneak)) {

                info.add("NBT: " + data.tag.toString());
            }

            else {

                info.add("Hold " + ChatFormatting.LIGHT_PURPLE + keyBindSneak.getDisplayName() + ChatFormatting.GRAY + " for NBT");
            }
        }
    }
}