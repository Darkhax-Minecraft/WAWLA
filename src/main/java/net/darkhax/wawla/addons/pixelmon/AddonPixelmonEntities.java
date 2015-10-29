package net.darkhax.wawla.addons.pixelmon;

import java.util.List;

import cpw.mods.fml.common.Loader;
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
    public Entity getWailaOverride (IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return data.getEntity();
    }
    
    @Override
    public List<String> getWailaHead (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        boolean canDisplay = (cfg.getConfig(CONFIG_REQUIRE_SNEAK)) ? data.getPlayer().isSneaking() : true;
        
        if (canDisplay && Utilities.compareByClass(classEntityPixelmon, entity.getClass()) && data.getNBTData() != null) {
            
            NBTTagCompound tag = data.getNBTData();
            
            if (natureList != null && cfg.getConfig(CONFIG_NATURE))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.nature") + ": " + natureList[tag.getShort("Nature")]);
                
            if (cfg.getConfig(CONFIG_ABILITIES)) {
                
                EnumChatFormatting abilityColor = (tag.getInteger("AbilitySlot") == 2) ? EnumChatFormatting.GOLD : EnumChatFormatting.GRAY;
                tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.ability") + ": " + abilityColor + tag.getString("Ability"));
            }
            
            if (sizeList != null && cfg.getConfig(CONFIG_SIZE))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.size") + ": " + sizeList[tag.getShort("Growth")]);
                
            if (cfg.getConfig(CONFIG_FRIENDSHIP))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.happiness") + ": " + tag.getInteger("Friendship"));
                
            if (cfg.getConfig(CONFIG_HELD_ITEM) && tag.hasKey("HeldItemStack"))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.pixelmon.helditem") + ": " + ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.getTag("HeldItemStack")).getDisplayName());
                
            if (cfg.getConfig(CONFIG_INDIVIDUAL_VALUES))
                tip.add("IV's - Atk:" + tag.getInteger("IVAttack") + " Def:" + tag.getInteger("IVDefence") + " HP:" + tag.getInteger("IVHP") + " SpAtk: " + tag.getInteger("IVSpAtt") + " SpDef:" + tag.getInteger("IVSpDef") + " Spd:" + tag.getInteger("IVSpeed"));
                
            if (cfg.getConfig(CONFIG_EFFORT_VALUES) && !tag.getString("OwnerUUID").isEmpty())
                tip.add("EV's - Atk:" + tag.getInteger("EVAttack") + " Def:" + tag.getInteger("EVDefence") + " HP:" + tag.getInteger("EVHP") + " SpAtk: " + tag.getInteger("EVSpAtt") + " SpDef:" + tag.getInteger("EVSpDef") + " Spd:" + tag.getInteger("EVSpeed"));
        }
        
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {
        
        if (entity != null)
            entity.writeToNBT(tag);
            
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
        
        AddonPixelmonEntities dataProvider = new AddonPixelmonEntities();
        
        register.addConfig("Pixelmon", CONFIG_REQUIRE_SNEAK);
        register.addConfig("Pixelmon", CONFIG_ABILITIES);
        register.addConfig("Pixelmon", CONFIG_FRIENDSHIP);
        register.addConfig("Pixelmon", CONFIG_HELD_ITEM);
        register.addConfig("Pixelmon", CONFIG_NATURE);
        register.addConfig("Pixelmon", CONFIG_SIZE);
        register.addConfig("Pixelmon", CONFIG_INDIVIDUAL_VALUES);
        register.addConfig("Pixelmon", CONFIG_EFFORT_VALUES);
        register.registerBodyProvider(dataProvider, classEntityPixelmon);
        natureList = Utilities.generateElementArray(enumNature);
        sizeList = Utilities.generateElementArray(enumGrowth);
    }
    
    public static Class classEntityPixelmon = null;
    public static Class enumNature = null;
    public static Class enumGrowth = null;
    
    private static final String CONFIG_REQUIRE_SNEAK = "wawla.pixelmon.useSneak";
    private static final String CONFIG_ABILITIES = "wawla.pixelmon.showAbility";
    private static final String CONFIG_FRIENDSHIP = "wawla.pixelmon.showFriendship";
    private static final String CONFIG_HELD_ITEM = "wawla.pixelmon.showHeldItem";
    private static final String CONFIG_NATURE = "wawla.pixelmon.showNature";
    private static final String CONFIG_SIZE = "wawla.pixelmon.showSize";
    private static final String CONFIG_INDIVIDUAL_VALUES = "wawla.pixelmon.showIV";
    private static final String CONFIG_EFFORT_VALUES = "wawla.pixelmon.showEV";
    
    private static String[] natureList = null;
    private static String[] sizeList = null;
}
