package net.darkhax.wawla.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class TranslationHooks {

    static ArrayList<String> lines = new ArrayList<String>();
    static File logFile = new File("logs/missingStrings.txt");
    
    public static void tryTranslateKey(String key, String translation) {

        String output = "Key: " + key + ", Translation: " + translation;
        if (translation == null && !lines.contains(output))
            lines.add("Key: " + key + ", Translation: " + translation);
    }
    
    static void print(String line) {
        
        BufferedWriter writer = null;
        try {

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(line);
            writer.newLine();
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
    }
}