package com.example.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.ErrorMessageModel;
import com.example.model.LoginModel;
import com.example.model.UserRequestModel;

import reactor.core.publisher.Mono;
import reactor.util.MultiValueMap;

import static org.springframework.web.reactive.function.BodyInserters.*;

@Controller
public class LoginController {
	
	@GetMapping("/")
	public String index(@ModelAttribute LoginModel login) {
		return "index";
	}
	
	@PostMapping("/login-controller")
	public String loginController(@Valid LoginModel login, BindingResult bindingResult, Model model, HttpSession session, RedirectAttributes redirect) {		
		
		if(bindingResult.hasErrors()) {
			return "index";
		}
		
		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 20)).build();
		
		WebClient client = WebClient.builder().exchangeStrategies(exchangeStrategies).baseUrl("http://localhost:3000").build();
		Mono<Object> entityMono = client.post()
				.uri("/user/login")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.body(fromMultipartData("nick", login.getNick()).with("password", login.getPassword()))
				.exchangeToMono((response) -> {
					if(response.statusCode().equals(HttpStatus.OK)) {
						redirect.addFlashAttribute("nickExists", "");
						return response.bodyToMono(UserRequestModel.class);
					} else if(response.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
						redirect.addFlashAttribute("nickExists", "Internal Server Error");
						return response.bodyToMono(UserRequestModel.class);
					} else {
						return response.bodyToMono(ErrorMessageModel.class);
					}
				});
		
		Object entity = entityMono.block();
		if(entity.getClass().equals(UserRequestModel.class)) {
			String flag = (String) redirect.getFlashAttributes().get("nickExists");
			if(flag.equals("")) {
				session.setAttribute("user", (UserRequestModel) entity);
				return "redirect:/post-red-get?dest=home";
			} else {
				return "index";
			}
		} else if(entity.getClass().equals(ErrorMessageModel.class)) {
			ErrorMessageModel message = (ErrorMessageModel) entity;
			if(message.getMessage().equals("User Not Defined")) {
				model.addAttribute("nickExists", message.getMessage());				
			} else {
				model.addAttribute("passwordValid", message.getMessage());	
			}
		}
		
		return "index";
	}
	
}
