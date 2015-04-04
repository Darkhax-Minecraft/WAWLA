package net.darkhax.wawla.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.darkhax.wawla.util.Constants;
import net.darkhax.wawla.util.IRCUtility;
import net.darkhax.wawla.util.PastebinUtility;
import net.darkhax.wawla.util.PastebinUtility.ExpireDate;
import net.darkhax.wawla.util.PastebinUtility.Paste;
import net.darkhax.wawla.util.PastebinUtility.ReportFormat;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class MissingDataDumpHandler {
    
    public static boolean sendReport = false;
    
    ArrayList<String> lang = new ArrayList<String>();
    
    /**
     * Constructs the handler object for sending a missing data report. This class will go
     * through various data in the users installation, an if permitted, shall send that data to
     * pastebin, and then to my private IRC channel.
     */
    public MissingDataDumpHandler() {
    
        if (ConfigurationHandler.enableDumpFile) {
            
            PastebinUtility pastebin = new PastebinUtility("a8dec0b9187cb7989f3ca85a34ee52db");
            
            Constants.LOG.info("Beginning missing data dump.");
            Paste log = new Paste();
            writeIntroduction(log);
            initExistingLangFile();
            writeMissingEnchantments(log);
            writeMissingVillagers(log);
            String outpaste = pastebin.post("Wawla-Data-Dump" + getTimeStamp(), log, ReportFormat.PLAIN_TEXT, ExpireDate.ONE_WEEK);
            
            if (sendReport)
                new IRCUtility("Darkhax: Some data has been collected: " + outpaste);
            
            Constants.LOG.info("The data dump has been completed. Please see " + outpaste);
            lang = null;
        }
    }
    
    /**
     * Writes the introduction for the file. No user data is included in this part of the code,
     * this is where the wawla ascii art is created, along with the introductory message.
     * 
     * @param paste: A Paste object, which represents the pastebin entry.
     */
    public void writeIntroduction (Paste paste) {
    
        ArrayList<String> message = new ArrayList<String>();
        Utilities.wrapStringToList("This is a data dump file for the Wawla mod. This dump file is generated when the player launches their game. Wawla will go through all enchantements and villagers added to the game, and then generate a language key. If the language key does not have a valid translation, then the key will be added to this file. This dump file does not contain any personal information about the user. After all the data has been collected, if any missing keys have been found, the dump file will be uploaded to pastebin, and then sent directly to the mod author. This information is important to the mod author, as it allows them to add better compatibility for Wawla and the mods that you are using. This file will automatically be deleted from pastebin after 7 days. While the data collected by this mod is not sensitive, it is possible to disable the dump file all together in your configuration file. Thank you for your time.", 80, true, message);
        
        paste.appendLine("                 WW      WW   AAA   WW      WW LL        AAA   ");
        paste.appendLine("                 WW      WW  AAAAA  WW      WW LL       AAAAA  ");
        paste.appendLine("                 WW   W  WW AA   AA WW   W  WW LL      AA   AA ");
        paste.appendLine("                  WW WWW WW AAAAAAA  WW WWW WW LL      AAAAAAA ");
        paste.appendLine("                   WW   WW  AA   AA   WW   WW  LLLLLLL AA   AA ");
        paste.appendLine("");
        
        for (String line : message)
            paste.appendLine(line);
    }
    
    /**
     * Creates the enchantment section of this entry. This code loops through all of the
     * enchantments which can appear on enchantment books. The enchantment is then used to
     * generate a language key used by this mod. Will attempt to translate these keys, if no
     * entry is found, it will be recorded and included in the report file.
     * 
     * @param paste: A Paste object, which represents the pastebin entry.
     */
    public void writeMissingEnchantments (Paste paste) {
    
        paste.appendLine("");
        Enchantment[] enchantments = Enchantment.enchantmentsBookList;
        List<String> missings = new ArrayList<String>();
        
        int counter = 0;
        
        for (int pos = 0; pos < enchantments.length; pos++) {
            
            Enchantment ench = enchantments[pos];
            String translation = StatCollector.translateToLocal("description." + ench.getName());
            if (ench != null && translation.startsWith("description.") && !lang.contains(translation)) {
                
                missings.add("description." + ench.getName());
                counter++;
                sendReport = true;
            }
        }
        
        paste.appendLine("#Missing Enchantment Descriptions: " + counter + " found.");
        
        for (String entry : missings)
            paste.appendLine(entry);
    }
    
    /**
     * Creates the villager section of the file. Loops through all registered villagers, and
     * then uses a method to generate a language key using parts of that villagers texture
     * file. The language key is then used to get a translation, if the translation returns a
     * result which is the same as the translation key, the translation had failed, and this
     * key needs to be added in a newer version of Wawla. Data will then be written to the
     * paste file.
     * 
     * @param paste: A Paste object, which represents the pastebin entry.
     */
    public void writeMissingVillagers (Paste paste) {
    
        paste.appendLine("");
        
        VillagerRegistry village = VillagerRegistry.instance();
        List<String> missings = new ArrayList<String>();
        int counter = 0;
        
        for (int id : village.getRegisteredVillagers()) {
            
            String profession = StatCollector.translateToLocal("description.villager.profession." + Utilities.getVillagerName(id));
            
            if (profession.startsWith("description.villager.profession.") && !lang.contains(profession)) {
                
                missings.add(profession);
                counter++;
                sendReport = true;
            }
        }
        
        paste.appendLine("#Missing Villager Professions: " + counter + " found.");
        
        for (String entry : missings)
            paste.appendLine(entry);
    }
    
    /**
     * Gets a time stamp to be used in a file name. The date produced represents the current
     * system time, and is presented in a year-month-hour-minute-second format. Example:
     * 1997-04-26-25-43
     */
    public static String getTimeStamp () {
    
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date now = new Date();
        String timeStamp = sdfDate.format(now);
        return timeStamp;
    }
    
    /**
     * Populates an array containing all language key entries from the Github page. This is
     * used to check if the mod author already knows about the missing language key.
     */
    public void initExistingLangFile () {
    
        try {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/Darkhax-Minecraft/WAWLA/master/src/main/resources/assets/wawla/lang/en_US.lang").openStream()));
            String line;
            
            while ((line = reader.readLine()) != null) {
                
                if (!line.equals("") || !line.startsWith("#")) {
                    
                    String[] langs = line.split("=");
                    lang.add(langs[0]);
                }
            }
            
            reader.close();
        }
        
        catch (MalformedURLException e) {
            
        }
        
        catch (IOException e) {
            
        }
    }
}