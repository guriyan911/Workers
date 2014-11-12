package com.yuraku.workers.controller;

import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TelnetController {
    private TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;
    private String prompt = "$";
    
    @RequestMapping("/telnet")
    public String telnet(Model model){
    	String server = "genbu";
    	String user = "red";
    	String password = "yakisoba";
    	
      	try {
    		// Connect to the specified server
    		telnet.connect(server, 23);

    		// Get input and output stream references
    		in = telnet.getInputStream();
    		out = new PrintStream(telnet.getOutputStream());

    		// Log the user on
    		readUntil("login: ");
    		write(user);
    		readUntil("Password: ");
    		write(password);

    		// Advance to a prompt
    		readUntil(prompt + " ");
    		
    		sendCommand("ls");
    		disconnect();
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}        return "telnet";
    }
    
    public void su(String password) {
    	try {
    		write("su");
    		readUntil("Password: ");
    		write(password);
    		prompt = "#";
    		readUntil(prompt + " ");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public String readUntil(String pattern) {
    	try {
    		char lastChar = pattern.charAt(pattern.length() - 1);
    		StringBuffer sb = new StringBuffer();
    		char ch = (char) in.read();
    		while (true) {
    			System.out.print(ch);
    			sb.append(ch);
    			if (ch == lastChar) {
    				if (sb.toString().endsWith(pattern)) {
    					return sb.toString();
    				}
    			}
    			ch = (char) in.read();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    public void write(String value) {
    	try {
    		out.println(value);
    		out.flush();
    		System.out.println(value);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public String sendCommand(String command) {
    	try {
    		write(command);
    		return readUntil(prompt + " ");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    public void disconnect() {
    	try {
    		telnet.disconnect();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}

