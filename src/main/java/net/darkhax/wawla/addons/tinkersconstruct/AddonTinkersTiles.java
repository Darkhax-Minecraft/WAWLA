package net.darkhax.wawla.addons.tinkersconstruct;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class AddonTinkersTiles implements IWailaDataProvider {

    public AddonTinkersTiles() {

        if (Loader.isModLoaded("TConstruct")) {

            isEnabled = true;
            try {

                classHarvestTool = Class.forName("tconstruct.library.tools.HarvestTool");
                classDualHarvestTool = Class.forName("tconstruct.library.tools.DualHarvestTool");
                classDryingRackLogic = Class.forName("tconstruct.blocks.logic.DryingRackLogic");
                classFurnaceLogic = Class.forName("tconstruct.tools.logic.FurnaceLogic");
                classBlockLandmine = Class.forName("tconstruct.mechworks.blocks.BlockLandmine");
                classDryingRack = Class.forName("tconstruct.armor.blocks.DryingRack");
                classFurnaceSlab = Class.forName("tconstruct.tools.blocks.FurnaceSlab");
                getHarvestType = classHarvestTool.getDeclaredMethod("getHarvestType");
                getSecondHarvestType = classDualHarvestTool.getDeclaredMethod("getSecondHarvestType");
                getHarvestType.setAccessible(true);
                getSecondHarvestType.setAccessible(true);
            }

            catch (ClassNotFoundException e) {

                e.printStackTrace();
            }

            catch (NoSuchMethodException e) {

                e.printStackTrace();
            }

            catch (SecurityException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor data, IWailaConfigHandler cfg) {

        // Hides landmines
        if (cfg.getConfig(hideLandmine) && data.getTileEntity() != null && Utilities.compareByClass(classBlockLandmine, data.getBlock().getClass())) {

            if (data.getNBTData() != null) {

                ItemStack cover = Utilities.getInventoryStacks(data.getNBTData(), 4)[3];

                if (cover != null)
                    return cover;
            }
        }

        return data.getStack();
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        if (data.getBlock() != null && data.getTileEntity() != null) {

            // drying rack
            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classDryingRackLogic)) {

                if (cfg.getConfig(showDryerItem)) {

                    ItemStack item = Utilities.getInventoryStacks(data.getNBTData(), 1)[0];

                    if (item != null)
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.item") + ": " + item.getDisplayName());
                }

                if (cfg.getConfig(showDryerTime)) {

                    double percent = Utilities.round(Utilities.getProgression(data.getNBTData().getInteger("Time"), data.getNBTData().getInteger("MaxTime")), 2);

                    if (percent > 0 && !(percent > 100))
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.tinkers.dryness") + ": " + percent + "%");
                }
            }

            // slab furnace
            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classFurnaceLogic)) {

                if (cfg.getConfig(showBurnTime)) {

                    int burnTime = data.getNBTData().getInteger("Fuel") / 20;

                    if (burnTime > 0 && cfg.getConfig("wawla.furnace.burntime"))
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.burnTime") + ": " + burnTime + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
                }

                if (cfg.getConfig(showFurnaceItem)) {

                    if (data.getPlayer().isSneaking()) {

                        ItemStack[] furnaceStacks = Utilities.getInventoryStacks(data.getNBTData(), 3);

                        if (furnaceStacks[0] != null && cfg.getConfig("wawla.furnace.input"))
                            tip.add(StatCollector.translateToLocal("tooltip.wawla.input") + ": " + furnaceStacks[0].getDisplayName() + " X " + furnaceStacks[0].stackSize);

                        if (furnaceStacks[1] != null && cfg.getConfig("wawla.furnace.fuel"))
                            tip.add(StatCollector.translateToLocal("tooltip.wawla.fuel") + ": " + furnaceStacks[1].getDisplayName() + " X " + furnaceStacks[1].stackSize);

                        if (furnaceStacks[2] != null && cfg.getConfig("wawla.furnace.output"))
                            tip.add(StatCollector.translateToLocal("tooltip.wawla.output") + ": " + furnaceStacks[2].getDisplayName() + " X " + furnaceStacks[2].stackSize);
                    }
                }
            }
        }

        return tip;
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {

        if (te != null)
            te.writeToNBT(tag);

        return tag;
    }

    public static void registerAddon(IWailaRegistrar register) {

        AddonTinkersTiles dataProvider = new AddonTinkersTiles();

        register.addConfig("Tinkers' Construct", hideLandmine);
        register.addConfig("Tinkers' Construct", showDryerTime);
        register.addConfig("Tinkers' Construct", showDryerItem);
        register.addConfig("Tinkers' Construct", showFurnaceItem);
        register.registerBodyProvider(dataProvider, classDryingRack);
        register.registerBodyProvider(dataProvider, classFurnaceSlab);
        register.registerNBTProvider(dataProvider, classDryingRack);
        register.registerNBTProvider(dataProvider, classBlockLandmine);
        register.registerStackProvider(dataProvider, classBlockLandmine);
    }

    /**
     * This is a special method added to allow the ModuleHarvest to apply to tinkers construct tools and
     * ores.
     * 
     * @param item: The item stack being checked.
     * @param required: The tool type required for the block.
     * @return true: When the item is the right type.
     * @return false: When the item is not the right type.
     */
    public static boolean canHarvest(ItemStack item, String required) {

        if (isEnabled) {

            List<String> tooltypes = new ArrayList<String>();

            if (classDualHarvestTool.isInstance(item.getItem())) {

                try {

                    tooltypes.add((String) getSecondHarvestType.invoke(item.getItem()));
                }

                catch (Exception e) {

                    e.printStackTrace();
                }
            }

            if (classHarvestTool.isInstance(item.getItem())) {

                try {

                    tooltypes.add((String) getHarvestType.invoke(item.getItem()));
                }

                catch (Exception e) {

                    e.printStackTrace();
                }
            }

            return (tooltypes.contains(required)) ? true : false;
        }

        return false;
    }

    private static String showDryerTime = "wawla.tinkers.showDryerTime";
    private static String showDryerItem = "wawla.tinkers.showDryerItem";
    private static String showFurnaceItem = "wawla.tinkers.showFurnace";
    private static String showBurnTime = "wawla.tinkers.showBurnTime";
    private static String hideLandmine = "wawla.tinkers.hideLandmine";

    public static boolean isEnabled = false;
    public static Class classHarvestTool = null;
    public static Class classDualHarvestTool = null;
    public static Class classDryingRackLogic = null;
    public static Class classFurnaceLogic = null;
    public static Class classBlockLandmine = null;
    public static Class classDryingRack = null;
    public static Class classFurnaceSlab = null;
    public static Method getHarvestType = null;
    public static Method getSecondHarvestType = null;
}
