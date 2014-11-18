package com.yuraku.workers.constants;

public class ReturnCodeConstants {

	/**
	 * 他からのインスタンス生成不可とする
	 */
	private ReturnCodeConstants(){}
	
	/**
	 * 処理結果：正常
	 */
	public static final String RC_SUCCESS = "0";

	/**
	 * 処理結果：異常
	 */
	public static final String RC_ERROR = "1";
}
