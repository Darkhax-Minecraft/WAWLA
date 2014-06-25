package net.darkhax.wawla.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

import net.darkhax.wawla.util.Reference;
import net.minecraft.util.ScreenShotHelper;

public class TranslationHooks {

    /**
     * An array to hold all of the broken or missing strings found by the tryTranslateKey hook method.
     */
    static ArrayList<String> lines = new ArrayList<String>();

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

        String output = "Key: " + key + ", Translation: " + translation;
        if (translation == null && !lines.contains(output))
            lines.add("Key: " + key + ", Translation: " + translation);
    }

    /**
     * This is a method that when called will print out all the entries stored by the tryTranslateKey
     * method.
     * 
     * @param fileName: This param allows for the location of the file to be changed. By default this
     *        should be logs/missingStrings.txt
     */
    static File print(String fileName) {

        BufferedWriter writer = null;
        try {

            File logFile = new File(fileName + " " + Reference.DATE.format(new Date()).toString());
            writer = new BufferedWriter(new FileWriter(logFile));

            for (int i = 0; i < lines.size(); i++) {

                writer.write(lines.get(i));
                writer.newLine();
            }
            
            return logFile;
        }

        catch (Exception e) {

            e.printStackTrace();
        }

        finally {

            try {

                writer.close();
            }

            catch (Exception e) {

            }
        }
        
        return null;
    }
}