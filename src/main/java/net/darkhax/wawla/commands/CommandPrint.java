package net.darkhax.wawla.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.JsonParseException;

import net.darkhax.wawla.handler.TranslationHooks;
import net.darkhax.wawla.util.Reference;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandPrint extends CommandBase {

    @Override
    public String getCommandName() {

        return "print";
    }

    @Override
    public String getCommandUsage(ICommandSender player) {

        return "commands.print.usage";
    }

    @Override
    public void processCommand(ICommandSender player, String[] pars) {

        String fileName = (!(pars.length > 0)) ? "logs/MissingStrings" : pars[0];
        player.addChatMessage(Utilities.generateClickableMessage("command.print.success", print(fileName)));
    }
    
    /**
     * This is a method that when called will print out all the entries stored by the tryTranslateKey
     * method.
     * 
     * @param fileName: This param allows for the location of the file to be changed. By default this
     *        should be logs/missingStrings.txt
     * @return: The file being generated.
     */
    static File print(String fileName) {

        ArrayList<String> lines = TranslationHooks.lines;
        BufferedWriter writer = null;
        try {

            File logFile = new File(fileName + " " + Reference.DATE.format(new Date()).toString() + ".txt");
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