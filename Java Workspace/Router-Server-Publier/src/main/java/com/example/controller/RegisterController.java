package com.example.controller;

import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.RegisterModel;
import com.example.model.UserRequestModel;
import com.example.services.HandlerUploadFile;

import reactor.core.publisher.Mono;

@Controller
public class RegisterController {
	
	@GetMapping("/register")
	public String register(@ModelAttribute RegisterModel register) {
		return "register";
	}
	
	@PostMapping("/register-controller")
	public String registerController(@Valid RegisterModel register, BindingResult bindingResult, Model model, RedirectAttributes redirect, HttpSession session) throws IOException {

		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		WebClient client = WebClient.create("http://localhost:3000");
		Mono<Object> entityMono = client.post()
				.uri("/user/post")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.body(fromMultipartData(
						"nick", register.getNick())
						.with("password", register.getPassword())
						.with("date", register.getDate())
						// Nao temos como passa um objeto MultipartFile para o metodo fromMultipartData, visite:
						// Vamos ter q baixar o conteudo temporariamente para pegar os dados atraves do filesystem
						// https://www.javacodemonk.com/multipart-file-upload-spring-boot-resttemplate-9f837ffe
						.with("image", HandlerUploadFile.createTempFile(register.getImage().getBytes())))
				.exchangeToMono((response) -> {
					if(response.statusCode().equals(HttpStatus.OK)) {
						redirect.addFlashAttribute("nickExists", "");
						return response.bodyToMono(UserRequestModel.class);
					} else if(response.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
						redirect.addFlashAttribute("nickExists", "Internal Server Error");
						return response.bodyToMono(UserRequestModel.class);
					} else {
						redirect.addFlashAttribute("nickExists", "The UserNick Already Exists");
						return response.bodyToMono(UserRequestModel.class);
					}
				});
		//https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client
		
		// A api esta retornando imageid nao a imagem, arrumar a api!!!!!!!
		UserRequestModel user = (UserRequestModel) entityMono.block();
		String flag = (String) redirect.getFlashAttributes().get("nickExists");
		if(flag.equals("")) {
			session.setAttribute("user", user);
			return "redirect:/post-red-get?dest=home";
		}
		model.addAttribute("nickExists", flag);
		return "register";
	}
}
