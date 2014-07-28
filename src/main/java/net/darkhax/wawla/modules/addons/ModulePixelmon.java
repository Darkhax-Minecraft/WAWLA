package net.darkhax.wawla.modules.addons;

import java.util.List;

import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.modules.Module;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class ModulePixelmon extends Module {

    public static Class classEntityPixelmon = null;
    public static Class classTileEntityApricornTree = null;
    private String tooltipKey = "tooltip.wawla.pixelmon.";

    public ModulePixelmon(Boolean Enabled) {

        super(true);

        try {

            classEntityPixelmon = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon");
            classTileEntityApricornTree = Class.forName("com.pixelmonmod.pixelmon.blocks.apricornTrees.TileEntityApricornTree");
        }

        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onEntityOverride(Entity entity, IWailaEntityAccessor accessor) {

        if (isPixelmon(entity)) {

            EntityLiving clone = (EntityLiving) entity;
            clone.copyDataFrom(entity, false);
            NBTTagCompound tag = accessor.getNBTData();
            clone.setCustomNameTag(tag.getString("Name") + getGender(tag.getShort("Gender")));
        }
    }

    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access) {

        createApricornTooltip(access.getTileEntity(), tooltip, access.getBlock());
        createApricornTooltip(access.getWorld().getTileEntity(access.getPosition().blockX, access.getPosition().blockY - 1, access.getPosition().blockZ), tooltip, access.getBlock());
    }

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

        if (isPixelmon(entity)) {

            NBTTagCompound tag = accessor.getNBTData();
            if (accessor.getPlayer().isSneaking()) {

                tooltip.add(StatCollector.translateToLocal(tooltipKey + "ability") + ": " + tag.getString("Ability"));
                tooltip.add(StatCollector.translateToLocal(tooltipKey + "happiness") + ": " + tag.getInteger("Friendship"));

                ItemStack item = new ItemStack(Item.getItemById(tag.getInteger("HeldItem")));

                if (item != null)
                    tooltip.add(StatCollector.translateToLocal(tooltipKey + "helditem") + ": " + item.getDisplayName());
            }
        }
    }

    @Override
    public void onWailaRegistrar(IWailaRegistrar register) {

        register.registerSyncedNBTKey("*", classEntityPixelmon);
    }

    boolean isPixelmon(Entity entity) {

        return (classEntityPixelmon != null && entity.getClass().equals(classEntityPixelmon)) ? true : false;
    }

    boolean isApricorn(TileEntity entity) {

        return (classTileEntityApricornTree != null && entity.getClass().equals(classTileEntityApricornTree)) ? true : false;
    }

    String getGender(short gender) {

        if (gender < 3)
            return (gender == 1) ? "(Male)" : "(Female)";

        else
            return "";
    }

    String generateIVEV(String type, String key1, String key2, NBTTagCompound tag) {

        return StatCollector.translateToLocal(tooltipKey + type) + ": " + StatCollector.translateToLocal(tooltipKey + "iv") + ": " + tag.getInteger(key1) + ": " + StatCollector.translateToLocal(tooltipKey + "ev") + ": " + tag.getInteger(key2);
    }

    float getGrowth(float curStage, float maxStage) {

        return (curStage / maxStage) * 100;
    }

    void createApricornTooltip(TileEntity entity, List<String> tooltip, Block block) {

        if (entity != null && isApricorn(entity)) {

            float meta = entity.getWorldObj().getBlockMetadata(entity.xCoord, entity.yCoord, entity.zCoord);
            String product = Block.blockRegistry.getNameForObject(block);
            tooltip.add(StatCollector.translateToLocal(tooltipKey + "growth") + ": " + Utilities.round(getGrowth(meta, 5), 0) + "%");
            tooltip.add(StatCollector.translateToLocal(tooltipKey + "product") + ": " + product.substring(9, product.length() - 5));
        }
    }
}