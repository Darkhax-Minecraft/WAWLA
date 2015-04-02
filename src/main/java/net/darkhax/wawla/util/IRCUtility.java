package net.darkhax.wawla.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A simple utility for connecting to an IRC network, posting a message, and then leaving
 * directly after. This is used by Wawla to have data posted into the IRC channel.
 */
public class IRCUtility {
    
    private final String server = "irc.esper.net";
    private final String nick = "WawlaDataDump";
    private final String login = "WawlaDataDump";
    private final String channel = "#wawla";
    
    String nick2 = nick;
    String login2 = login;
    
    int i = 1;
    
    public IRCUtility(String message) {
    
        try {
            
            Socket socket = new Socket(server, 5555);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            while (!connect(writer, reader)) {
                
            }
            
            writer.write("JOIN " + channel + System.getProperty("line.separator"));
            writer.flush();
            writer.write("PRIVMSG " + channel + " :" + message + System.getProperty("line.separator"));
            writer.flush();
            socket.close();
        }
        
        catch (UnknownHostException e) {
            
        }
        
        catch (IOException e) {
            
        }
    }
    
    public boolean connect (BufferedWriter writer, BufferedReader reader) throws IOException {
    
        writer.write("NICK " + nick2 + System.getProperty("line.separator"));
        writer.write("USER " + login2 + " 8 * : Wawla Data Dump" + System.getProperty("line.separator"));
        writer.flush();
        
        String line = null;
        while ((line = reader.readLine()) != null) {
            
            if (line.startsWith("PING ")) {
                
                writer.write("PONG " + line.substring(5) + System.getProperty("line.separator"));
                writer.flush();
            }
            
            if (line.contains("004")) {
                
                return true;
            }
            
            else if (line.contains("433")) {
                
                nick2 = nick + i;
                login2 = login + i;
                i++;
                return false;
            }
        }
        
        return false;
    }
}
