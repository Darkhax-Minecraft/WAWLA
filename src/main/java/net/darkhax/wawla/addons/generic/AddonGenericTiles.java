package net.darkhax.wawla.addons.generic;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.addons.tinkersconstruct.AddonTinkersTiles;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class AddonGenericTiles implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return data.getStack();
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        MovingObjectPosition pos = data.getPosition();
        Block block = data.getBlock();
        ItemStack item = data.getPlayer().getHeldItem();
        String tool = (block != null) ? block.getHarvestTool(data.getMetadata()) : "";
        int blockLevel = block.getHarvestLevel(data.getMetadata());
        int itemLevel = (item != null) ? item.getItem().getHarvestLevel(item, tool) : 0;

        // Corrective check to prevent chisel from breaking the module.
        if (tool != null && tool.equalsIgnoreCase("chisel")) {

            if (block == Blocks.stone)
                tool = "pickaxe";

            if (block == Blocks.planks)
                tool = "axe";
        }

        // Shows if the tool is the right tier.
        if (item != null && (item.getItem().getToolClasses(item).contains(tool) || AddonTinkersTiles.canHarvest(item, tool))) {

            // When the block is harvestable.
            if (cfg.getConfig(showHarvestable) && (blockLevel <= itemLevel || blockLevel == 0))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.canHarvest") + ": " + ((EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("tooltip.wawla.yes"))));

            // When it's not harvestable.
            else {

                if (cfg.getConfig(showHarvestable))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.canHarvest") + ": " + EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.wawla.no"));

                if (cfg.getConfig(showTier))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.blockLevel") + ": " + blockLevel);
            }
        }

        // Shows correct tool type.
        if (tool != null && cfg.getConfig(showTool))
            tip.add(StatCollector.translateToLocal("tooltip.wawla.toolType") + ": " + StatCollector.translateToLocal("tooltip.wawla.tooltype." + tool));

        // Light level
        if (cfg.getConfig(showLightLevel) && (!data.getWorld().isBlockNormalCubeDefault(data.getPosition().blockX, data.getPosition().blockY + 1, data.getPosition().blockZ, false) || data.getWorld().isAirBlock(data.getPosition().blockX, data.getPosition().blockY + 1, data.getPosition().blockZ))) {

            int dayLevel = Utilities.getBlockLightLevel(data.getWorld(), data.getPosition().blockX, data.getPosition().blockY, data.getPosition().blockZ, true);
            int nightLevel = Utilities.getBlockLightLevel(data.getWorld(), data.getPosition().blockX, data.getPosition().blockY, data.getPosition().blockZ, false);

            String display = StatCollector.translateToLocal("tooltip.wawla.lightLevel") + ": ";

            if (cfg.getConfig(showMonsterSpawn)) {

                if (nightLevel <= 7)
                    display = display + EnumChatFormatting.DARK_RED + "" + nightLevel + " ";

                else if (nightLevel > 7)
                    display = display + EnumChatFormatting.GREEN + "" + nightLevel + " ";
            }

            if (cfg.getConfig(showDay))
                display = display + EnumChatFormatting.YELLOW + "(" + dayLevel + ")";

            tip.add(display);
        }

        return tip;
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {

        return tag;
    }

    public static void registerAddon(IWailaRegistrar register) {

        AddonGenericTiles dataProvider = new AddonGenericTiles();

        register.addConfig("Wawla-General", showTool);
        register.addConfig("Wawla-General", showHarvestable);
        register.addConfig("Wawla-General", showTier);

        register.addConfig("Wawla-General", showLightLevel);
        register.addConfig("Wawla-General", showMonsterSpawn);
        register.addConfig("Wawla-General", showDay);

        register.registerBodyProvider(dataProvider, Block.class);
    }

    private static String showTool = "wawla.harvest.showTool";
    private static String showHarvestable = "wawla.harvest.showHarvest";
    private static String showTier = "wawla.harvest.showTier";

    private static String showDay = "wawla.light.showDay";
    private static String showMonsterSpawn = "wawla.light.monsterSpawn";
    private static String showLightLevel = "wawla.light.lightLevel";
}
