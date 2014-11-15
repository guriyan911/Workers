package com.yuraku.workers.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import lombok.Data;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Telnet接続テスト
 * charで戻り待ちをするので早いが日本語が表示できない
 * 正規表現を使ってプロンプトチェックしていないので誤作動するかも
 */
@Controller
@ConfigurationProperties(prefix="telnet")
@Data
public class TelnetController {
    private InputStream is;
    private PrintStream ps;
    private String prompt = "$";
	private String server;
	private String user;
	private String password;
    
    @RequestMapping("/telnet")
    public String telnet(Model model) throws IOException{
    	
        TelnetClient telnet = null;

        try {
        	telnet = new TelnetClient();
    		// Connect to the specified server
    		telnet.connect(server, 23);

    		// Get input and output stream references
    		is = telnet.getInputStream();
    		ps = new PrintStream(telnet.getOutputStream());

    		// Log the user on
    		readUntil("login: ");
    		write(user);
    		readUntil("Password: ");
    		write(password);

    		// Advance to a prompt
    		readUntil(prompt + " ");
    		
    		//sendCommand("ls");
    		sendCommand("\\ls"); // 頭に\をつけるとその時はLS_COLORSなどの設定が無効になる
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
			ps.close();
			try {
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (telnet.isConnected()) {
				try {
					telnet.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
        return "telnet";
    }
    
    public String readUntil(String pattern) {
    	try {
    		char lastChar = pattern.charAt(pattern.length() - 1);
    		StringBuilder sb = new StringBuilder();
    		char ch = (char) is.read();
    		while (true) {
    			System.out.print(ch);
    			sb.append(ch);
    			if (ch == lastChar) {
    				if (sb.toString().endsWith(pattern)) {
    					return sb.toString();
    				}
    			}
    			ch = (char) is.read();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    public void write(String value) {
    	try {
    		ps.println(value);
    		ps.flush();
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
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}

