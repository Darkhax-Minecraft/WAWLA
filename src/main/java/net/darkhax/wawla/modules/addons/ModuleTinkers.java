package net.darkhax.wawla.modules.addons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.darkhax.wawla.modules.Module;
import net.minecraft.item.ItemStack;

public class ModuleTinkers extends Module {
    
    public static Class classHarvestTool = null;
    public static Class classDualHarvestTool = null;
    public static Method getHarvestType = null;
    public static Method getSecondHarvestType = null;
    
    public ModuleTinkers(boolean enabled) {
        
        super(enabled);
        
        if (enabled) {
            
            try {
                
                classHarvestTool = Class.forName("tconstruct.library.tools.HarvestTool");
                classDualHarvestTool = Class.forName("tconstruct.library.tools.DualHarvestTool");
                getHarvestType = classHarvestTool.getDeclaredMethod("getHarvestType");
                getSecondHarvestType = classDualHarvestTool.getDeclaredMethod("getSecondHarvestType");
                getHarvestType.setAccessible(true);
                getSecondHarvestType.setAccessible(true);
            }
            
            catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
            
            catch (NoSuchMethodException e) {

                e.printStackTrace();
            }
            catch (SecurityException e) {
                
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Checks to see if a tinkers item is the right type to mine a block.
     * @param item: The item stack being checked.
     * @param required: The tool type required for the block. 
     * @return true: When the item is the right type.
     * @return false: When the item is not the right type.
     */
    public static boolean canHarvest(ItemStack item, String required) {
        
        List<String> tooltypes = new ArrayList<String>();
        
        if (classDualHarvestTool.isInstance(item.getItem())) {
            
            try {
                
                tooltypes.add((String)getSecondHarvestType.invoke(item.getItem()));
            }
            
            catch (Exception e) {
                
                e.printStackTrace();
            }
        }
            
        if (classHarvestTool.isInstance(item.getItem())) {
            
            try {
                
                tooltypes.add((String)getHarvestType.invoke(item.getItem()));
            }
            
            catch (Exception e) {
                
                e.printStackTrace();
            }
        }
        
        return (tooltypes.contains(required)) ? true : false;
    }
}