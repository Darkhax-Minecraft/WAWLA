package net.darkhax.wawla.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.darkhax.wawla.Wawla;
import net.darkhax.wawla.lib.Constants;
import net.darkhax.wawla.utils.AnnotationUtils;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

public class FeatureManager {

    public static final List<Class<?>> classes = new ArrayList<>();
    public static final List<InfoProvider> tileProviders = new ArrayList<>();
    public static final List<InfoProvider> entityProviders = new ArrayList<>();
    public static final List<InfoProvider> itemProviders = new ArrayList<>();

    private static boolean loaded = false;

    public static void init (ASMDataTable asmDataTable) {

        loaded = true;

        for (final Entry<InfoProvider, WawlaFeature> feature : AnnotationUtils.getAnnotations(asmDataTable, WawlaFeature.class, InfoProvider.class).entrySet()) {

            classes.add(feature.getKey().getClass());
            final WawlaFeature annotation = feature.getValue();

            if (annotation == null) {

                Constants.LOG.warn("Annotation for " + feature.getKey().getClass().getCanonicalName() + " was null!");
                continue;
            }

            registerFeature(feature.getKey(), annotation.name(), annotation.description(), annotation.type());
        }

    }

    public static void registerFeature (InfoProvider feature, String name, String description, ProviderType type) {

        if (feature.canEnable()) {

            final boolean enabled = Wawla.config.getConfig().getBoolean(name, "_feature", feature.enabledByDefault(), description);

            if (enabled)
                switch (type) {

                    case BLOCK:
                        tileProviders.add(feature);
                        break;

                    case ENTITY:
                        entityProviders.add(feature);
                        break;

                    case ITEM:
                        itemProviders.add(feature);
                        break;

                    case ITEM_BLOCK:
                        itemProviders.add(feature);
                        tileProviders.add(feature);
                        break;

                    default:
                        break;
                }
        }
    }

    public static boolean isLoaded () {

        return loaded;
    }
}