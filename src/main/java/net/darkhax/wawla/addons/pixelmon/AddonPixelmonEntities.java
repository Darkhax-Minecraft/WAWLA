package net.darkhax.wawla.addons.pixelmon;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class AddonPixelmonEntities implements IWailaEntityProvider {

    public AddonPixelmonEntities() {

        if (Loader.isModLoaded("pixelmon")) {

            try {

                classEntityPixelmon = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon");
                enumNature = Class.forName("com.pixelmonmod.pixelmon.enums.EnumNature");
                enumGrowth = Class.forName("com.pixelmonmod.pixelmon.enums.EnumGrowth");
            }

            catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public Entity getWailaOverride(IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return data.getEntity();
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public List<String> getWailaBody(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        if (Utilities.compareByClass(classEntityPixelmon, entity.getClass()) && data.getNBTData() != null) {

            NBTTagCompound tag = data.getNBTData();
            if (data.getPlayer().isSneaking()) {

                if (natureList != null && cfg.getConfig(showNature))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.nature") + ": " + natureList[tag.getShort("Nature")]);

                if (cfg.getConfig(showAbility)) {
                    
                    EnumChatFormatting abilityColor = (tag.getInteger("AbilitySlot") == 2) ? EnumChatFormatting.GOLD : EnumChatFormatting.GRAY;
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.ability") + ": " + abilityColor + tag.getString("Ability"));
                }

                if (sizeList != null && cfg.getConfig(showSize))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.size") + ": " + sizeList[tag.getShort("Growth")]);

                if (cfg.getConfig(showFriendship))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.happiness") + ": " + tag.getInteger("Friendship"));

                if (cfg.getConfig(showHeldItem) && tag.hasKey("HeldItemStack"))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.helditem") + ": " + ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.getTag("HeldItemStack")).getDisplayName());
            }
        }

        return tip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {

        if (entity != null)
            entity.writeToNBT(tag);

        return tag;
    }

    public static void registerAddon(IWailaRegistrar register) {

        AddonPixelmonEntities dataProvider = new AddonPixelmonEntities();

        register.addConfig("Pixelmon", showAbility);
        register.addConfig("Pixelmon", showFriendship);
        register.addConfig("Pixelmon", showHeldItem);
        register.addConfig("Pixelmon", showNature);
        register.addConfig("Pixelmon", showSize);
        register.registerBodyProvider(dataProvider, classEntityPixelmon);
        natureList = Utilities.generateElementArray(enumNature);
        sizeList = Utilities.generateElementArray(enumGrowth);
    }

    public static Class classEntityPixelmon = null;
    public static Class enumNature = null;
    public static Class enumGrowth = null;

    private static String showAbility = "wawla.pixelmon.showAbility";
    private static String showFriendship = "wawla.pixelmon.showFriendship";
    private static String showHeldItem = "wawla.pixelmon.showHeldItem";
    private static String showNature = "wawla.pixelmon.showNature";
    private static String showSize = "wawla.pixelmon.showSize";

    private static String[] natureList = null;
    private static String[] sizeList = null;
}
