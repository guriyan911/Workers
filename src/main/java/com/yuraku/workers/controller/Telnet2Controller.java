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
 * Telnet接続テスト2 Streamを使うので日本語表示できる
 * Reader,Writer,Streamは閉じないとゴミとなってController違いでも影響を与える
 */
@Controller
public class Telnet2Controller {

	@RequestMapping("/telnet2")
	public String telnet(Model model) {
		String server = "genbu";
		String user = "red";
		String password = "yakisoba";
		String prompt = ".*@" + server + ":.*\\$ ";

		// クライアントの生成
		TelnetClient telnet = null;
		InputStream istream = null;
		OutputStream ostream = null;
		Reader reader = null;
		Writer writer = null;

		try {

			telnet = new TelnetClient();
			// サーバに接続
			telnet.connect(server);

			// 通信用の入出力ストリームの生成
			istream = telnet.getInputStream();
			ostream = telnet.getOutputStream();
			reader = new InputStreamReader(istream);
			writer = new OutputStreamWriter(ostream);

			// 認証の実行
			readMessage(reader, ".*login: $");
			writer.write(user + "\n");
			writer.flush();
			readMessage(reader, "Password: $");
			writer.write(password + "\n");
			writer.flush();

			// プロンプト出力待ち
			readMessage(reader, prompt);

			// コマンド実行
			writer.write("\\ls\n");// 頭に\をつけるとその時はLS_COLORSなどの設定が無効になる
			writer.flush();

			// 実行結果取得
			readMessage(reader, prompt);

			// ネットワークの切断
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				ostream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				istream.close();
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

	static String readMessage(Reader reader, String message) throws Exception {

		Pattern pattern = Pattern.compile(message, Pattern.DOTALL);// .のピリオドを\nも対象にする
		StringBuilder sb = new StringBuilder();
		Matcher matcher = null;

		while (true) {
			int c = reader.read();
			if (c < 0)
				break;
			sb.append((char) c);
			// 次のread()が入力をブロックするかも知れない時はmatchチェックする
			if (reader.ready() == false) {
				matcher = pattern.matcher(sb.toString());
				if (matcher.find())
					break;
			}
		}

		if (matcher != null){
			if(matcher.find(0) && matcher.groupCount() >= 1) {
				System.out.print(matcher.group(1));
				return (matcher.group(1));
			}
		}
		System.out.print(sb.toString());
		return sb.toString();
	}

}
