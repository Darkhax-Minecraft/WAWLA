package net.darkhax.wawla.handler;

import java.util.ArrayList;

import net.darkhax.wawla.modules.Module;

public class TranslationHooks {

    /**
     * An array to hold all of the broken or missing strings found by the tryTranslateKey hook method.
     */
    public static ArrayList<String> lines = new ArrayList<String>();

    /**
     * A hook method created by Ghostrec35, this method is injected into the TranslateKey method and is
     * called every time Minecraft attempts to translate text. The key and the provided translation will
     * be supplied to this method. The purpose of this method in our mod is to create a list of all the
     * broken/missing language translations in the mod, if a broken/null translation is detected it will
     * be added to the list.
     * 
     * @param key: The key for this translation. eg: item.stick.name
     * @param translation: The result for the translation. eg: Stick
     */
    public static void tryTranslateKey(String key, String translation) {

        for (Module module : Module.modules)
            module.onStringTranslation(key, translation);

        String output = "Key: " + key + ", Translation: " + translation;
        System.out.println(output);
        if (translation == null && !lines.contains(output))
            lines.add(output);
    }
}