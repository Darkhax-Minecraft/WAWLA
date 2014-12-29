package net.darkhax.wawla.addons.generic;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class AddonGenericEntities implements IWailaEntityProvider {

    private static String[] itemTypes = { "heldItem", "feet", "leggings", "chestplate", "helmet" };
    public static ArrayList<String> petTags = new ArrayList<String>();

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

        // Equipment
        if (entity instanceof EntityLiving && cfg.getConfig(showEquippedItems)) {

            String ench = StatCollector.translateToLocal("tip.wawla") + ": ";
            EntityLiving living = (EntityLiving) entity;

            for (int i = 0; i < 5; i++) {

                ItemStack stack = living.getEquipmentInSlot(i);
                if (stack != null) {

                    tip.add(StatCollector.translateToLocal("tooltip.wawla." + itemTypes[i]) + ": " + stack.getDisplayName());
                    Enchantment[] enchantments = Utilities.getEnchantmentsFromStack(stack, false);

                    if (data.getPlayer().isSneaking()) {

                        for (int x = 0; x < enchantments.length; x++) {

                            String name = StatCollector.translateToLocal(enchantments[x].getName());
                            if (!ench.contains(name))
                                ench = ench + name + ", ";
                        }
                    }
                }
            }
        }

        // shows pet owner
        if (cfg.getConfig(showPetOwner)) {

            NBTTagCompound tag = Utilities.convertEntityToNbt(entity);
            NBTTagCompound extTag = entity.getEntityData();

            for (String currentKey : petTags) {

                if (tag.hasKey(currentKey) && !tag.getString(currentKey).isEmpty())
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.owner") + ": " + tag.getString(currentKey));

                if (extTag.hasKey(currentKey) && !extTag.getString(currentKey).isEmpty())
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.owner") + ": " + extTag.getString(currentKey));
            }
        }

        // shows age info
        if (entity instanceof EntityAnimal) {

            EntityAnimal animal = (EntityAnimal) entity;

            if (cfg.getConfig(showAge) && animal.isChild() && animal.getGrowingAge() != 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.age") + ": " + ((animal.getGrowingAge() / 20) * -1) + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));

            else if (cfg.getConfig(showBirthCooldown) && animal.getGrowingAge() != 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.birth") + ": " + ((animal.getGrowingAge() / 20)) + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
        }

        // shows if sitting
        if (entity instanceof EntityTameable && cfg.getConfig(showPetSitting)) {

            EntityTameable pet = (EntityTameable) entity;
            tip.add(StatCollector.translateToLocal("tooltip.wawla.sit") + ": " + pet.isSitting());
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

        AddonGenericEntities dataProvider = new AddonGenericEntities();

        petTags.add("Owner");
        petTags.add("OwnerName");
        petTags.add("owner");
        petTags.add("ownerName");
        register.addConfig("Wawla-Entity", showEquippedItems);
        register.registerBodyProvider(dataProvider, Entity.class);
        register.registerNBTProvider(dataProvider, Entity.class);
    }

    private static String showEquippedItems = "wawla.showEquipment";

    private String showPetOwner = "wawla.pets.showOwner";
    private String showPetSitting = "wawla.pets.sitting";

    private String showAge = "wawla.pets.age";
    private String showBirthCooldown = "wawla.pets.cooldown";
}
