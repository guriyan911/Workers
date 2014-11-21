package com.yuraku.workers.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {

	/**
	 * リソースファイルのテキスト読み込み
	 * （例えばsql文を読み込むのに使います。ex. "sql/01_emp_select.sql"）
	 * @param filename
	 * @return テキスト文字列
	 * @throws IOException
	 */
	public static String sqlString(String filename) throws IOException{
		StringBuilder sb = new StringBuilder();
        InputStream is = ClassLoader.getSystemResourceAsStream(filename);
        BufferedReader reader;
		reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        char[] b = new char[1024];
        int line;
        while (0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
		return sb.toString();
	}
}
