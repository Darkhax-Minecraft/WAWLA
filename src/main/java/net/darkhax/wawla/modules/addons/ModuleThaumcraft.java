package net.darkhax.wawla.modules.addons;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.modules.Module;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;

public class ModuleThaumcraft extends Module {

    private String showJarAspects = "wawla.thaumcraft.jarAspect";
    private String showJarAmount = "wawla.thaumcraft.jarAmount";
    private String showMirrorLink = "wawla.thaumcraft.mirrorLink";
    private String showMirrorDimension = "wawla.thaumcraft.mirrorDim";
    private String showJarEXP = "wawla.thaumcraft.jarEXP";
    private String showWandItem = "wawla.thaumcraft.wandItem";
    private String showWandCharge = "wawla.thaumcraft.wandCharge";
    private String showPedestalItem = "wawla.thaumcraft.pedestalItem";
    private String showDeconstructionAspect = "wawla.thaumcraft.deconAspect";

    public Class classTileJarFillable = null;
    public Class classTileJarFillableVoid = null;
    public Class classTileMirror = null;
    public Class classTileMirrorEssentia = null;
    public Class classTileJarBrain = null;
    public Class classTileWandPedestal = null;
    public Class classTilePedestal = null;
    public Class classTileDeconstructionTable = null;
    public Class classEntityGolemBase = null;

    public ModuleThaumcraft(boolean enabled) {

        super(enabled);

        if (enabled) {

            try {

                classTileJarFillable = Class.forName("thaumcraft.common.tiles.TileJarFillable");
                classTileJarFillableVoid = Class.forName("thaumcraft.common.tiles.TileJarFillableVoid");
                classTileMirror = Class.forName("thaumcraft.common.tiles.TileMirror");
                classTileMirrorEssentia = Class.forName("thaumcraft.common.tiles.TileMirrorEssentia");
                classTileJarBrain = Class.forName("thaumcraft.common.tiles.TileJarBrain");
                classTileWandPedestal = Class.forName("thaumcraft.common.tiles.TileWandPedestal");
                classTilePedestal = Class.forName("thaumcraft.common.tiles.TilePedestal");
                classTileDeconstructionTable = Class.forName("thaumcraft.common.tiles.TileDeconstructionTable");
                classEntityGolemBase = Class.forName("thaumcraft.common.entities.golems.EntityGolemBase");
            }

            catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        if (access.getBlock() != null && access.getTileEntity() != null) {

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileJarFillable) || Utilities.compareTileEntityByClass(access.getTileEntity(), classTileJarFillableVoid)) {

                String aspect = access.getNBTData().getString("Aspect");
                int amount = access.getNBTData().getShort("Amount");

                if (true && aspect != null && !aspect.isEmpty() && config.getConfig(showJarAspects))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.aspect") + ": " + Utilities.upperCase(aspect));

                if (true && amount > 0 && config.getConfig(showJarAmount))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.amount") + ": " + amount);
            }

            if ((Utilities.compareTileEntityByClass(access.getTileEntity(), classTileMirror) || Utilities.compareTileEntityByClass(access.getTileEntity(), classTileMirrorEssentia)) && access.getNBTData().getBoolean("linked")) {

                if (config.getConfig(showMirrorLink))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.linked") + ": X:" + access.getNBTData().getInteger("linkX") + " Y:" + access.getNBTData().getInteger("linkY") + " Z:" + access.getNBTData().getInteger("linkZ"));

                if (config.getConfig(showMirrorDimension))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.dimension") + ": " + DimensionManager.getProvider(access.getNBTData().getInteger("linkDim")).getDimensionName());
            }

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileJarBrain)) {

                if (config.getConfig(showJarEXP))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.experience") + ": " + access.getNBTData().getInteger("XP"));
            }

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileDeconstructionTable)) {

                if (config.getConfig(showDeconstructionAspect))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.thaumcraft.aspect") + ": " + (access.getNBTData().hasKey("Aspect") ? Utilities.upperCase(access.getNBTData().getString("Aspect")) : "None"));
            }

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTilePedestal)) {

                if (config.getConfig(showPedestalItem)) {
                    ItemStack pedestalStack = Utilities.getInventoryStacks(access.getNBTData(), 1)[0];

                    if (pedestalStack != null)
                        tooltip.add(StatCollector.translateToLocal("tooltip.wawla.item") + ": " + pedestalStack.getDisplayName());
                }
            }

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileWandPedestal)) {

                if (true) {

                    ItemStack pedestalStack = Utilities.getInventoryStacks(access.getNBTData(), 1)[0];

                    if (pedestalStack != null && pedestalStack.hasTagCompound()) {

                        if (config.getConfig(showWandItem))
                            tooltip.add(pedestalStack.getDisplayName());

                        if (pedestalStack.stackTagCompound.hasKey("aqua") && config.getConfig(showWandCharge)) {

                            String split = EnumChatFormatting.WHITE + " | ";
                            tooltip.add(EnumChatFormatting.YELLOW + "" + (float) (pedestalStack.stackTagCompound.getInteger("aer") / 100f) + split + EnumChatFormatting.DARK_GREEN + "" + (float) (pedestalStack.stackTagCompound.getInteger("terra") / 100) + split + EnumChatFormatting.RED + "" + (float) (pedestalStack.stackTagCompound.getInteger("ignis") / 100f) + split + EnumChatFormatting.DARK_AQUA + "" + (float) (pedestalStack.stackTagCompound.getInteger("aqua") / 100f) + split + EnumChatFormatting.GRAY + "" + (float) (pedestalStack.stackTagCompound.getInteger("ordo") / 100f) + split + EnumChatFormatting.DARK_GRAY + "" + (float) (pedestalStack.stackTagCompound.getInteger("perditio") / 100f));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        if (classTileJarFillable != null)
            register.registerSyncedNBTKey("*", classTileJarFillable);

        if (classTileMirror != null)
            register.registerSyncedNBTKey("*", classTileMirror);

        if (classTileMirrorEssentia != null)
            register.registerSyncedNBTKey("*", classTileMirrorEssentia);

        if (classTileJarBrain != null)
            register.registerSyncedNBTKey("*", classTileJarBrain);

        if (classTileWandPedestal != null)
            register.registerSyncedNBTKey("*", classTileWandPedestal);

        if (classTilePedestal != null)
            register.registerSyncedNBTKey("*", classTilePedestal);

        if (classTileDeconstructionTable != null)
            register.registerSyncedNBTKey("*", classTileDeconstructionTable);

        register.addConfig("Thaumcraft", showJarAspects);
        register.addConfig("Thaumcraft", showJarAmount);
        register.addConfig("Thaumcraft", showMirrorLink);
        register.addConfig("Thaumcraft", showMirrorDimension);
        register.addConfig("Thaumcraft", showJarEXP);
        register.addConfig("Thaumcraft", showWandItem);
        register.addConfig("Thaumcraft", showWandCharge);
        register.addConfig("Thaumcraft", showPedestalItem);
        register.addConfig("Thaumcraft", showDeconstructionAspect);
    }
}
