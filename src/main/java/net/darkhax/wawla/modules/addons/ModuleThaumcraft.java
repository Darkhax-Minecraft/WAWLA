package net.darkhax.wawla.modules.addons;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.wawla.modules.Module;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.DimensionManager;

public class ModuleThaumcraft extends Module {

    public Class classTileJarFillable = null;
    public Class classTileMirror = null;
    public Class classTileMirrorEssentia = null;
    public Class classTileJarBrain = null;
    public Class classTileWandPedestal = null;
    public Class classTilePedestal = null;
    public Class classTileDeconstructionTable = null;

    public ModuleThaumcraft(boolean enabled) {

        super(enabled);

        if (enabled) {

            try {

                classTileJarFillable = Class.forName("thaumcraft.common.tiles.TileJarFillable");
                classTileMirror = Class.forName("thaumcraft.common.tiles.TileMirror");
                classTileMirrorEssentia = Class.forName("thaumcraft.common.tiles.TileMirrorEssentia");
                classTileJarBrain = Class.forName("thaumcraft.common.tiles.TileJarBrain");
                classTileWandPedestal = Class.forName("thaumcraft.common.tiles.TileWandPedestal");
                classTilePedestal = Class.forName("thaumcraft.common.tiles.TilePedestal");
                classTileDeconstructionTable = Class.forName("thaumcraft.common.tiles.TileDeconstructionTable");
            }

            catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        if (access.getBlock() != null && access.getTileEntity() != null) {

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileJarFillable)) {

                String aspect = access.getNBTData().getString("Aspect");
                int amount = access.getNBTData().getShort("Amount");

                if (true && aspect != null && !aspect.isEmpty())
                    tooltip.add("Asepct: " + Utilities.upperCase(aspect));

                if (true && amount > 0)
                    tooltip.add("Amount: " + amount);
            }

            if ((Utilities.compareTileEntityByClass(access.getTileEntity(), classTileMirror) || Utilities.compareTileEntityByClass(access.getTileEntity(), classTileMirrorEssentia)) && access.getNBTData().getBoolean("linked")) {

                if (true)
                    tooltip.add("Linked: X:" + access.getNBTData().getInteger("linkX") + " Y:" + access.getNBTData().getInteger("linkY") + " Z:" + access.getNBTData().getInteger("linkZ"));

                if (true)
                    tooltip.add("Dimension: " + DimensionManager.getProvider(access.getNBTData().getInteger("linkDim")).getDimensionName());
            }

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileJarBrain)) {

                if (true)
                    tooltip.add("Experience: " + access.getNBTData().getInteger("XP"));
            }

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileDeconstructionTable)) {

                if (true)
                    tooltip.add("Aspect: " + (access.getNBTData().hasKey("Aspect") ? Utilities.upperCase(access.getNBTData().getString("Aspect")) : "None"));
            }

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTilePedestal)) {

                if (true) {
                    ItemStack pedestalStack = Utilities.getInventoryStacks(access.getNBTData(), 1)[0];

                    if (pedestalStack != null)
                        tooltip.add("Item: " + pedestalStack.getDisplayName());
                }
            }

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileWandPedestal)) {

                if (true) {

                    ItemStack pedestalStack = Utilities.getInventoryStacks(access.getNBTData(), 1)[0];

                    if (pedestalStack != null && pedestalStack.hasTagCompound()) {

                        tooltip.add(pedestalStack.getDisplayName());

                        if (pedestalStack.stackTagCompound.hasKey("aqua")) {

                            String split = EnumChatFormatting.WHITE + " | ";
                            tooltip.add(EnumChatFormatting.YELLOW + "" + (float) (pedestalStack.stackTagCompound.getInteger("aer") / 100f) + split + EnumChatFormatting.DARK_GREEN + "" + (float) (pedestalStack.stackTagCompound.getInteger("terra") / 100) + split + EnumChatFormatting.RED + "" + (float) (pedestalStack.stackTagCompound.getInteger("ignis") / 100f) + split + EnumChatFormatting.DARK_AQUA + "" + (float) (pedestalStack.stackTagCompound.getInteger("aqua") / 100f) + split + EnumChatFormatting.GRAY + "" + (float) (pedestalStack.stackTagCompound.getInteger("ordo") / 100f) + split + EnumChatFormatting.DARK_GRAY + "" + (float) (pedestalStack.stackTagCompound.getInteger("perditio") / 100f));
                        }
                    }
                }
            }
        }
    }
}
