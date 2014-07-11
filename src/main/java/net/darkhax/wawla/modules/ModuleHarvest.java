package net.darkhax.wawla.modules;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;

public class ModuleHarvest extends Module {

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access) {

        MovingObjectPosition pos = access.getPosition();
        Block block = access.getBlock();
        ItemStack item = access.getPlayer().getHeldItem();
        String tool = (block != null) ? block.getHarvestTool(access.getMetadata()) : "";
        int blockLevel = block.getHarvestLevel(access.getMetadata());
        int itemLevel = (item != null) ? item.getItem().getHarvestLevel(item, tool) : 0;
        ArrayList<ItemStack> list = block.getDrops(access.getWorld(), pos.blockX, pos.blockY, pos.blockZ, access.getMetadata(), EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, access.getPlayer().getHeldItem()));

        if (item != null && item.getItem().getToolClasses(item).contains(tool)) {

            tooltip.add(StatCollector.translateToLocal("tooltip.canHarvest") + ": " + ((blockLevel <= itemLevel) ? StatCollector.translateToLocal("tooltip.yes") : StatCollector.translateToLocal("tooltip.no")));
            return;
        }

        if (tool != null)
            tooltip.add(StatCollector.translateToLocal("tooltip.toolType") + ": " + tool);
    }
}
