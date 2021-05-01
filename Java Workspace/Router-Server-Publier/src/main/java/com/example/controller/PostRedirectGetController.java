package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostRedirectGetController {
	
	@GetMapping("/post-red-get")
	public String postRedGet(@RequestParam("dest") String dest) {
		return dest;
	}
}
