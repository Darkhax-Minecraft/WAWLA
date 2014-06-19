package net.darkhax.wawla.handler;

public class TranslationHooks {
    
    public static void tryTranslateKey(String key, String translation) {
        
        System.out.println("Hook Working: Key: " + key + ", Translation: " + translation);
    }
}