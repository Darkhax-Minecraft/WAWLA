package net.darkhax.wawla.addons.thaumcraft;

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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.Loader;

public class AddonThaumcraftTiles implements IWailaDataProvider {

    public AddonThaumcraftTiles() {

        if (Loader.isModLoaded("Thaumcraft")) {

            try {

                classTileJarFillable = Class.forName("thaumcraft.common.tiles.TileJarFillable");
                classTileJarFillableVoid = Class.forName("thaumcraft.common.tiles.TileJarFillableVoid");
                classTileMirror = Class.forName("thaumcraft.common.tiles.TileMirror");
                classTileMirrorEssentia = Class.forName("thaumcraft.common.tiles.TileMirrorEssentia");
                classTileJarBrain = Class.forName("thaumcraft.common.tiles.TileJarBrain");
                classTileWandPedestal = Class.forName("thaumcraft.common.tiles.TileWandPedestal");
                classTilePedestal = Class.forName("thaumcraft.common.tiles.TilePedestal");
                classTileDeconstructionTable = Class.forName("thaumcraft.common.tiles.TileDeconstructionTable");
                classBlockJar = Class.forName("thaumcraft.common.blocks.BlockJar");
                classBlockMirror = Class.forName("thaumcraft.common.blocks.BlockMirror");
                classBlockStoneDevice = Class.forName("thaumcraft.common.blocks.BlockStoneDevice");
                classBlockTable = Class.forName("thaumcraft.common.blocks.BlockTable");
            }

            catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
        }
    }

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

        if (data.getBlock() != null && data.getTileEntity() != null) {

            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classTileJarFillable) || Utilities.compareTileEntityByClass(data.getTileEntity(), classTileJarFillableVoid)) {

                String aspect = data.getNBTData().getString("Aspect");
                int amount = data.getNBTData().getShort("Amount");

                if (true && aspect != null && !aspect.isEmpty() && cfg.getConfig(showJarAspects))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.aspect") + ": " + Utilities.upperCase(aspect));

                if (true && amount > 0 && cfg.getConfig(showJarAmount))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.amount") + ": " + amount);
            }

            if ((Utilities.compareTileEntityByClass(data.getTileEntity(), classTileMirror) || Utilities.compareTileEntityByClass(data.getTileEntity(), classTileMirrorEssentia)) && data.getNBTData().getBoolean("linked")) {

                if (cfg.getConfig(showMirrorLink))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.linked") + ": X:" + data.getNBTData().getInteger("linkX") + " Y:" + data.getNBTData().getInteger("linkY") + " Z:" + data.getNBTData().getInteger("linkZ"));

                if (cfg.getConfig(showMirrorDimension))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.dimension") + ": " + DimensionManager.getProvider(data.getNBTData().getInteger("linkDim")).getDimensionName());
            }

            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classTileJarBrain)) {

                if (cfg.getConfig(showJarEXP))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.experience") + ": " + data.getNBTData().getInteger("XP"));
            }

            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classTileDeconstructionTable)) {

                if (cfg.getConfig(showDeconstructionAspect))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.aspect") + ": " + (data.getNBTData().hasKey("Aspect") ? Utilities.upperCase(data.getNBTData().getString("Aspect")) : "None"));
            }

            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classTilePedestal)) {

                if (cfg.getConfig(showPedestalItem)) {
                    ItemStack pedestalStack = Utilities.getInventoryStacks(data.getNBTData(), 1)[0];

                    if (pedestalStack != null)
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.item") + ": " + pedestalStack.getDisplayName());
                }
            }

            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classTileWandPedestal)) {

                if (true) {

                    ItemStack pedestalStack = Utilities.getInventoryStacks(data.getNBTData(), 1)[0];

                    if (pedestalStack != null && pedestalStack.hasTagCompound()) {

                        if (cfg.getConfig(showWandItem))
                            tip.add(pedestalStack.getDisplayName());

                        if (pedestalStack.stackTagCompound.hasKey("aqua") && cfg.getConfig(showWandCharge)) {

                            String split = EnumChatFormatting.WHITE + " | ";
                            tip.add(EnumChatFormatting.YELLOW + "" + (float) (pedestalStack.stackTagCompound.getInteger("aer") / 100f) + split + EnumChatFormatting.DARK_GREEN + "" + (float) (pedestalStack.stackTagCompound.getInteger("terra") / 100) + split + EnumChatFormatting.RED + "" + (float) (pedestalStack.stackTagCompound.getInteger("ignis") / 100f) + split + EnumChatFormatting.DARK_AQUA + "" + (float) (pedestalStack.stackTagCompound.getInteger("aqua") / 100f) + split + EnumChatFormatting.GRAY + "" + (float) (pedestalStack.stackTagCompound.getInteger("ordo") / 100f) + split + EnumChatFormatting.DARK_GRAY + "" + (float) (pedestalStack.stackTagCompound.getInteger("perditio") / 100f));
                        }
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

        AddonThaumcraftTiles dataProvider = new AddonThaumcraftTiles();

        register.addConfig("Thaumcraft", showJarAspects);
        register.addConfig("Thaumcraft", showJarAmount);
        register.addConfig("Thaumcraft", showMirrorLink);
        register.addConfig("Thaumcraft", showMirrorDimension);
        register.addConfig("Thaumcraft", showJarEXP);
        register.addConfig("Thaumcraft", showWandItem);
        register.addConfig("Thaumcraft", showWandCharge);
        register.addConfig("Thaumcraft", showPedestalItem);
        register.addConfig("Thaumcraft", showDeconstructionAspect);

        register.registerBodyProvider(dataProvider, classBlockJar);
        register.registerBodyProvider(dataProvider, classBlockMirror);
        register.registerBodyProvider(dataProvider, classBlockStoneDevice);
        register.registerBodyProvider(dataProvider, classBlockTable);
        register.registerNBTProvider(dataProvider, classBlockJar);
        register.registerNBTProvider(dataProvider, classBlockMirror);
        register.registerNBTProvider(dataProvider, classBlockStoneDevice);
        register.registerNBTProvider(dataProvider, classBlockTable);
    }

    private static String showJarAspects = "wawla.thaumcraft.jarAspect";
    private static String showJarAmount = "wawla.thaumcraft.jarAmount";
    private static String showMirrorLink = "wawla.thaumcraft.mirrorLink";
    private static String showMirrorDimension = "wawla.thaumcraft.mirrorDim";
    private static String showJarEXP = "wawla.thaumcraft.jarEXP";
    private static String showWandItem = "wawla.thaumcraft.wandItem";
    private static String showWandCharge = "wawla.thaumcraft.wandCharge";
    private static String showPedestalItem = "wawla.thaumcraft.pedestalItem";
    private static String showDeconstructionAspect = "wawla.thaumcraft.deconAspect";

    public Class classTileJarFillable = null;
    public Class classTileJarFillableVoid = null;
    public Class classTileMirror = null;
    public Class classTileMirrorEssentia = null;
    public Class classTileJarBrain = null;
    public Class classTileWandPedestal = null;
    public Class classTilePedestal = null;
    public Class classTileDeconstructionTable = null;
    public static Class classBlockJar = null;
    public static Class classBlockMirror = null;
    public static Class classBlockStoneDevice = null;
    public static Class classBlockTable = null;
}
