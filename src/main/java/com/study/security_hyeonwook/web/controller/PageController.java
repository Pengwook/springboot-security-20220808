package com.study.security_hyeonwook.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.security_hyeonwook.service.auth.PrincipalDetails;

@Controller
public class PageController {
	
	@GetMapping({"/", "/index"})
	public String loadIndex(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {	// 세션영역에서 principaldatails를 꺼낼수있
		
		model.addAttribute("principal", principalDetails);	// "" -> 키값이라 이름 상관없
		
		return "index";
	}
	
	@GetMapping("/auth/signin")
	public String loadSignin() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String loadSignup() {
		return "auth/signup";
	}
	
	@GetMapping("/mypage")
	public String loadMyPage() {
		return "mypage";
	}
}
