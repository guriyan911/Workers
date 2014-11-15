package com.yuraku.workers.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Telnet接続テスト2
 * Streamを使うので日本語表示できるが全ての情報を表示する仕組みになっていない
 */
@Controller
public class Telnet2Controller {

    
    @RequestMapping("/telnet2")
    public String telnet(Model model) throws IOException{
    	String server = "genbu";
    	String user = "red";
    	String password = "yakisoba";
    	String prompt = ".*@" + server + ":.*\\$ ";
    	
        // クライアントの生成
        TelnetClient telnet = new TelnetClient();                
      	try {

            // サーバに接続
            telnet.connect(server);                         

            // 通信用の入出力ストリームの生成
            InputStream istream = telnet.getInputStream();           
            OutputStream ostream = telnet.getOutputStream();         
            Reader reader = new InputStreamReader( istream );        
            Writer writer = new OutputStreamWriter( ostream );       

            // 認証の実行
            readMessage(reader, ".*login: $");                       
            writer.write(user + "\n");                             
            writer.flush();                                          
            readMessage(reader, "Password: $");                      
            writer.write(password + "\n");                         
            writer.flush();                                          

            // プロンプト出力待ち
            String output = readMessage(reader, ".*" + prompt);                      
            System.out.print( output );                              

            // コマンド実行
            writer.write( "\\ls\n");                              
            writer.flush();                                          

            // 実行結果取得
            output = readMessage(reader, "(.*)" + prompt);    

            // 実行結果の出力
            System.out.print( output );                              

            // ネットワークの切断
            telnet.disconnect();                                     
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(telnet.isConnected()){
    			telnet.disconnect();
    		}
    	}
      	return "telnet";
    }
    
	public String readMessage(Reader reader, String message) throws Exception {
		long start = System.currentTimeMillis();

		Pattern pattern = Pattern.compile(message, Pattern.DOTALL);// .のピリオドを\nも対象にする
		StringBuilder sb = new StringBuilder();
		Matcher matcher = null;

		while (true) {
			int c = reader.read();
			if (c < 0)
				break;
			sb.append((char) c);
			if (reader.ready() == false) {
				matcher = pattern.matcher(sb.toString());
				if (matcher.matches())
					break;
			}
		}

		if (matcher != null && matcher.find(0) && matcher.groupCount() >= 1) {
			long stop = System.currentTimeMillis();
		    System.out.println(message + "実行にかかった時間は " + (stop - start) + " ミリ秒です。");
			return (matcher.group(1));
		}
		long stop = System.currentTimeMillis();
	    System.out.println(message + "実行にかかった時間は " + (stop - start) + " ミリ秒です。");
	    return null;
	}

}

