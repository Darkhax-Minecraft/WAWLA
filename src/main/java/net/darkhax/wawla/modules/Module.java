package net.darkhax.wawla.modules;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class Module {

    //TODO add way to configure what modules are loaded
    
    /**
     * This is a list of all modules. This list is used to pass calls on to all modules.
     */
    private static ArrayList<Module> modules = new ArrayList<Module>();

    public Module() {

        modules.add(this);
    }

    /**
     * This method is called when minecraft renders an item tooltip. This can be used to add information.
     * 
     * @param stack: An instance of the ItemStack being looked at.
     * @param tooltip: A list of strings that are used to create the tooltip.
     * @param advanced: If the player has advanced mode (ctrl+f3+h) this will be true.
     */
    public void onTooltipDisplayed(ItemStack stack, List<String> tooltip, boolean advanced) {

    }

    /**
     * This method is called every time minecraft attempts to translate a string.
     * 
     * @param key: The key being used for the translation.
     * @param translation: The translation result, this can be null.
     */
    public void onStringTranslation(String key, String translation) {

    }

    /**
     * This method is called when waila prints a block name. This is used to manipulate the display name
     * of the block. This is the upper most section of the tool tip.
     * 
     * @param stack: An instance of an ItemStack that contains data about the block being looked at.
     * @param tooltip: A list of all the current text being displayed about the block.
     * @param access: An accessor that can be used to pull a bunch of miscellaneous information.
     */
    public void onWailaBlockName(ItemStack stack, List<String> tooltip, IWailaDataAccessor access) {

    }

    /**
     * This method is called when waila prints the description of a block. This can be used to add
     * information about a block. This makes up the middle section of the tool tip.
     * 
     * @param stack: An instance of an ItemStack that contains data about the block being looked at.
     * @param tooltip: A list of all the current text being displayed about the block.
     * @param access: An accessor that can be used to pull a bunch of miscellaneous information.
     */
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access) {

    }

    /**
     * This method is called when waila prints the end of a block tooltip. This is used by waila to print
     * the name of the mod that owns the block.
     * 
     * @param stack: An instance of an ItemStack that contains data about the block being looked at.
     * @param tooltip: A list of all the current text being displayed about the block.
     * @param access: An accessor that can be used to pull a bunch of miscellaneous information.
     */
    public void onWailaBlockTail(ItemStack stack, List<String> tooltip, IWailaDataAccessor access) {

    }

    /**
     * This method is called when waila prints the name of an entity into an entity tooltip. This is used
     * by waila to display the block name. This is the upper most part of the tooltip.
     * 
     * @param entity: An instance of the entity that is currently being looked at by the player.
     * @param tooltip: An list of all the current text being displayed in the top part of the tooltip.
     * @param accessor: An accessor that can be used to pull information.
     */
    public void onWailaEntityName(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

    }

    /**
     * This method is called when waila prints the body of an entity tooltip. This is the middle section
     * of the tooltip.
     * 
     * @param entity: An instance of the entity that is currently being looked at by the player.
     * @param tooltip: An list of all the current text being displayed in the middle part of the tooltip.
     * @param accessor: An accessor that can be used to pull information.
     */
    public void onWailaEntityDescription(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

    }

    /**
     * This method is called when waila prints the end section of a tooltip. This is used by waila to add
     * the name of the mob that the entity is registered to.
     * 
     * @param entity: An instance of the entity that is currently being looked at by the player.
     * @param tooltip: An list of all the current text being displayed in the bottom part of the tooltip.
     * @param accessor: An accessor that can be used to pull information.
     */
    public void onWailaEntityTail(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor) {

    }

    /**
     * A way to access the array of modules.
     * 
     * @return ArrayList<Module>: Access to the module array.
     */
    public static ArrayList<Module> getModules() {

        return modules;
    }
}
