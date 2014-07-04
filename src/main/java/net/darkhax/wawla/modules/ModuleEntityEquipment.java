package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaEntityAccessor;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ModuleEntityEquipment extends Module {

    private static String[] itemTypes = { "heldItem", "feet", "leggings", "chestplate", "helmet" };

    @Override
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

        if (entity instanceof EntityLiving) {

            String ench = "Enchantments: ";
            EntityLiving living = (EntityLiving) entity;

            for (int i = 0; i < 5; i++) {

                ItemStack stack = living.getEquipmentInSlot(i);
                if (stack != null) {

                    tooltip.add(StatCollector.translateToLocal("tooltip." + itemTypes[i]) + ": " + stack.getDisplayName());
                    Enchantment[] enchantments = Utilities.getEnchantmentsFromStack(stack, false);

                    if (accessor.getPlayer().isSneaking()) {

                        for (int x = 0; x < enchantments.length; x++) {

                            String name = StatCollector.translateToLocal(enchantments[x].getName());
                            if (!ench.contains(name))
                                ench = ench + name + ", ";
                        }
                    }
                }
            }

            if (accessor.getPlayer().isSneaking()) {

                if (ench.equals("Enchantments: "))
                    tooltip.add("This entity has no enchantments!");

                else {

                    ench = ench.substring(0, ench.length() - 2);
                    Utilities.wrapStringToList(ench, 50, false, tooltip);
                }
            }
        }
    }
}