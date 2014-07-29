package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;

public class ModuleFurnace extends Module {

    public ModuleFurnace(boolean enabled) {

        super(enabled);
    }

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        if (access.getTileEntity() != null && access.getTileEntity() instanceof TileEntityFurnace) {

            TileEntityFurnace furnace = (TileEntityFurnace) access.getTileEntity();
            int burnTime = access.getNBTData().getInteger("BurnTime") / 20;

            if (burnTime > 0)
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.burnTime") + ": " + burnTime + " " + StatCollector.translateToLocal("tooltip.seconds"));

            if (access.getPlayer().isSneaking()) {

                ItemStack[] furnaceStacks = generateStacksForFurnace(access.getNBTData());

                if (furnaceStacks[0] != null)
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.input") + ": " + furnaceStacks[0].getDisplayName() + " X " + furnaceStacks[0].stackSize);

                if (furnaceStacks[1] != null)
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.fuel") + ": " + furnaceStacks[1].getDisplayName() + " X " + furnaceStacks[1].stackSize);

                if (furnaceStacks[2] != null)
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.output") + ": " + furnaceStacks[2].getDisplayName() + " X " + furnaceStacks[2].stackSize);
            }
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.registerSyncedNBTKey("*", TileEntityFurnace.class);
    }

    /**
     * This method allows for a list of ItemStacks to be retrieved from a furnace.
     * 
     * @param tag: NBTTagCompound containing nbt data for the entity.
     * @return ItemStack[]: An array of ItemStacks contained within the furnace.
     */
    public ItemStack[] generateStacksForFurnace(NBTTagCompound tag) {

        ItemStack[] stacks = new ItemStack[3];
        NBTTagList list = tag.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {

            NBTTagCompound compound = list.getCompoundTagAt(i);
            byte byt = compound.getByte("Slot");

            if (byt >= 0 && byt < stacks.length)
                stacks[byt] = ItemStack.loadItemStackFromNBT(compound);
        }

        return stacks;
    }
}