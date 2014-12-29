package net.darkhax.wawla.handler;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {

    public ForgeEventHandler() {

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {

        if (event.itemStack != null) {

            Block block = Block.getBlockFromItem(event.itemStack.getItem());

            if (block != null)
                event.toolTip.add("" + block.getClass());
        }
    }
}
