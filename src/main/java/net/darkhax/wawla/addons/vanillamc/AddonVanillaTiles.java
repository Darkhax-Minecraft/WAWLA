package net.darkhax.wawla.addons.vanillamc;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockSkull;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class AddonVanillaTiles implements IWailaDataProvider {

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

        // Beacon
        if (data.getTileEntity() instanceof TileEntityBeacon) {

            NBTTagCompound tag = data.getNBTData();
            int level = tag.getInteger("Levels");
            int primary = tag.getInteger("Primary");
            int secondary = tag.getInteger("Secondary");

            if (cfg.getConfig(showBeaconLevel))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.levels") + ": " + level);

            if (cfg.getConfig(showBeaconPrimaryEffect) && primary > 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.primary") + ": " + StatCollector.translateToLocal(Potion.potionTypes[primary].getName()));

            if (cfg.getConfig(showBeaconSecondaryEffect) && secondary > 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.secondary") + ": " + StatCollector.translateToLocal(Potion.potionTypes[secondary].getName()));
        }

        // Furnace
        else if (data.getTileEntity() instanceof TileEntityFurnace) {

            TileEntityFurnace furnace = (TileEntityFurnace) data.getTileEntity();
            int burnTime = data.getNBTData().getInteger("BurnTime") / 20;

            if (burnTime > 0 && cfg.getConfig(showFurnaceBurnTime))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.burnTime") + ": " + burnTime + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));

            if (data.getPlayer().isSneaking()) {

                ItemStack[] furnaceStacks = Utilities.getInventoryStacks(data.getNBTData(), 3);

                if (furnaceStacks[0] != null && cfg.getConfig(showFurnaceInput))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.input") + ": " + furnaceStacks[0].getDisplayName() + " X " + furnaceStacks[0].stackSize);

                if (furnaceStacks[1] != null && cfg.getConfig(showFurnaceFuel))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.fuel") + ": " + furnaceStacks[1].getDisplayName() + " X " + furnaceStacks[1].stackSize);

                if (furnaceStacks[2] != null && cfg.getConfig(showFurnaceOutput))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.output") + ": " + furnaceStacks[2].getDisplayName() + " X " + furnaceStacks[2].stackSize);
            }
        }

        // Player Skull
        if (data.getTileEntity() instanceof TileEntitySkull && cfg.getConfig(showSkullName))
            tip.add(StatCollector.translateToLocal("tooltooltip.wawla.owner") + ": " + NBTUtil.func_152459_a(data.getNBTData().getCompoundTag("Owner")).getName());

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

        AddonVanillaTiles dataProvider = new AddonVanillaTiles();

        register.addConfig("Wawla-Blocks", showBeaconLevel);
        register.addConfig("Wawla-Blocks", showBeaconPrimaryEffect);
        register.addConfig("Wawla-Blocks", showBeaconSecondaryEffect);
        register.registerBodyProvider(dataProvider, BlockBeacon.class);
        register.registerNBTProvider(dataProvider, BlockBeacon.class);

        register.addConfig("Wawla-Blocks", showFurnaceInput);
        register.addConfig("Wawla-Blocks", showFurnaceOutput);
        register.addConfig("Wawla-Blocks", showFurnaceFuel);
        register.addConfig("Wawla-Blocks", showFurnaceBurnTime);
        register.registerBodyProvider(dataProvider, BlockFurnace.class);
        register.registerNBTProvider(dataProvider, BlockFurnace.class);

        register.addConfig("Wawla-Blocks", "wawla.showhead");
        register.registerBodyProvider(dataProvider, BlockSkull.class);
        register.registerNBTProvider(dataProvider, BlockSkull.class);
    }

    private static String showBeaconLevel = "wawla.beacon.showLevels";
    private static String showBeaconPrimaryEffect = "wawla.beacon.showPrimary";
    private static String showBeaconSecondaryEffect = "wawla.beacon.showSecondary";

    private static String showFurnaceInput = "wawla.furnace.input";
    private static String showFurnaceOutput = "wawla.furnace.output";
    private static String showFurnaceFuel = "wawla.furnace.fuel";
    private static String showFurnaceBurnTime = "wawla.furnace.burntime";

    private String showSkullName = "wawla.showHead";
}
