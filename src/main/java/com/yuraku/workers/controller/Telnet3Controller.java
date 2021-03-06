package com.yuraku.workers.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuraku.workers.service.TelnetService;

/**
 * Telnet接続テスト3 
 * Serviceクラスを呼び出す
 */
@Controller
public class Telnet3Controller {
	 @Autowired
	 private TelnetService telnetService;

	 // messages_jaやmessages_enを利用する仕組みはApp.javaとIndexController.javaで記載済み
	 // あまりいい方法とは思えないので他に方法があれば知りたい

	@RequestMapping("/telnet3")
	public String telnet(Model model) throws IOException {

		String message = "";
		String datas = "";
		if(telnetService.connect()){
	        message = "サーバに接続しました。";
		}else{
			// 異常時はエラーコードを確認してメッセージを設定する
	        message = "サーバからの応答がありません。";			
	        model.addAttribute("message",message);
			return "telnet3";
		}
		
        model.addAttribute("message",message);
		datas = telnetService.write("\\ls");
        model.addAttribute("datas",datas);
		return "telnet3";
	}
}
