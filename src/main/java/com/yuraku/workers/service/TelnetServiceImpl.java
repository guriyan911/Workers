package com.yuraku.workers.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix="telnet")
public class TelnetServiceImpl implements TelnetService {
	@Getter @Setter private String server;
	@Getter @Setter private String user;
	@Getter @Setter private String password;
	@Getter @Setter private String prompt = ".*@servername:.*\\$ ";// ホスト名の正規表現は環境に合わせて変更する

	private TelnetClient telnet = null;
	private InputStream is = null;
	private OutputStream os = null;
	private Reader reader = null;
	private Writer writer = null;

	/**
	 * サーバ接続＆ログイン処理
	 * サーバ名、ユーザ、パスワードはアプリケーション設定ファイルに記載すること
	 * @param server
	 * @throws SocketException
	 * @throws IOException
	 */
	@Override
	public boolean connect() throws IOException {
	    InetAddress inetAddress = InetAddress.getByName(server);

	    // isReachableメソッドでpingが実現できます。引数はタイムアウト(ミリ秒指定)。
		try {
			if (!inetAddress.isReachable(2000)) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	    // プロンプト正規表現にサーバ名を含める
		this.prompt = prompt.replaceAll("servername", server);

		try {
			telnet = new TelnetClient();
			// サーバに接続
			telnet.connect(server);
			// 通信用の入出力ストリームの生成
			is = telnet.getInputStream();
			os = telnet.getOutputStream();
			reader = new InputStreamReader(is);
			writer = new OutputStreamWriter(os);

			readMessage(reader, ".*login: $");
			writer.write(user +"\n");
			writer.flush();
			readMessage(reader, "Password: $");
			writer.write(password + "\n");
			writer.flush();
			// プロンプト出力待ち
			readMessage(reader, prompt);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return true;
	}

	/**
	 * Telnetで接続先に命令する
	 * @param message
	 * @return String 命令結果
	 * @throws IOException
	 */
	@Override
	public String write(String message) throws IOException {
		// コマンドでlsを実行する時は頭に\\を付けて\\lsで実行する
		// コマンド実行
		writer.write(message + "\n");
		writer.flush();
		// 実行結果取得
		return readMessage(reader, prompt);
	}

	/**
	 * クローズ処理
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		writer.close();
		reader.close();
		os.close();
		is.close();
		if (telnet.isConnected()) {
			telnet.disconnect();
		}
	}

	static String readMessage(Reader reader, String message)
		throws IOException {

		Pattern pattern = Pattern.compile(message, Pattern.DOTALL);// .のピリオドを\nも対象にする
		StringBuilder sb = new StringBuilder();
		Matcher matcher = null;
		
		while (true) {
			int c;
			c = reader.read();
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

		if (matcher != null) {
			if (matcher.find(0) && matcher.groupCount() >= 1) {
				System.out.print(matcher.group(1));
				return (matcher.group(1));
			}
		}
		System.out.print(sb.toString());
		return sb.toString();
	}

}
