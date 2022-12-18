package com.example.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class TestController {
	@GetMapping("a")
	@ResponseBody
	public void a() {
		System.out.println("testcontroll a()메서드입니다.");
		
		
	}
		
	@GetMapping("h")
	public void h() {
		System.out.println("testcontroll h()메서드입니다. ");
	
	}
}
