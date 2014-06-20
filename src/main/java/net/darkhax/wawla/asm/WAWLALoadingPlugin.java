package net.darkhax.wawla.asm;

import java.io.File;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

/**
 *ASM Loading plugin for WAWLA.
 *
 *@author Ghostrec35
 */
public class WAWLALoadingPlugin implements IFMLLoadingPlugin {
    
    private static final String CATEGORY_CLASS_TRANSFORMATIONS = "Class Transformations";
    private static final String CATEGORY_CLASS_TRANSFORMATIONS_COMMENT = "This category contains configurable properties for performing certain class transformations. "
            + "\nPerforming certain class transformations along with other mods that also perform class transformations can sometimes cause problems."
            + "\nIf you encounter problems, feel free to disable these transformations, but be aware that some features of this mod will be disabled.";
    
    @Override
    public String[] getASMTransformerClass() {

        return new String[] { "net.darkhax.wawla.asm.WAWLAClassTransformer" };
    }

    @Override
    public String getModContainerClass() {

        return null;
    }

    @Override
    public String getSetupClass() {

        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

        Configuration config = new Configuration(new File(
                (File) data.get("mcLocation"), "config/wawla.cfg"));
        config.addCustomCategoryComment(CATEGORY_CLASS_TRANSFORMATIONS,
                CATEGORY_CLASS_TRANSFORMATIONS_COMMENT);
        config.load();
        WAWLAConfiguration.TRANSLATION_CLASS_TRANSFORM = config.get(
                CATEGORY_CLASS_TRANSFORMATIONS,
                "Perform Translation Hook Injection?", true);
        config.save();
    }

    @Override
    public String getAccessTransformerClass() {

        return null;
    }
}
