package net.darkhax.wawla.handler;

import mcp.mobius.waila.api.SpecialChars;
import net.darkhax.wawla.addons.vanillamc.AddonEnchantments;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {

    public ForgeEventHandler() {

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {

        AddonEnchantments.onTooltipDisplayed(event.itemStack, event.entityPlayer, event.toolTip, event.showAdvancedItemTooltips);
    }
}
