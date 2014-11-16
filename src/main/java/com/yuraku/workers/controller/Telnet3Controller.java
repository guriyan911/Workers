package com.yuraku.workers.controller;

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

	@RequestMapping("/telnet3")
	public String telnet(Model model) {

		String message = "Telnet接続に成功しました。";
		String datas = "";

		try {
			telnetService.connect();
			datas = telnetService.write("\\ls");
		}catch(Exception e){
			message = "Telnet接続に失敗しました。";
		}
        model.addAttribute("datas",datas);
        model.addAttribute("message",message);
		return "telnet3";
	}
}
