package com.example.controller;

import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.PostRequestModel;
import com.example.model.UserRequestModel;
import com.example.services.HandlerUploadFile;

import reactor.core.publisher.Mono;

@Controller
public class PullNewPostsController {

	@PostMapping("/post-new-posts")
	public String postNewPosts(@ModelAttribute PostRequestModel postModel, HttpSession session, Model model) throws IOException {
		
		WebClient client = WebClient.create("http://localhost:3000");
		Mono<Object> entityMono = client.post()
				.uri("/document/post")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.body(fromMultipartData(
						"userNick", ((UserRequestModel) session.getAttribute("user")).getNick() )
						.with("title", postModel.getTitle())
						.with("message", postModel.getMessage())
						.with("image", HandlerUploadFile.createTempFile(postModel.getImage().getBytes())))
				.exchangeToMono((response) -> {
					if(response.statusCode().equals(HttpStatus.OK)) {
						return response.bodyToMono(PostRequestModel.class);
					} else {
						model.addAttribute("status", "erro");
						return response.bodyToMono(PostRequestModel.class);
					}
				});
		
		PostRequestModel postReq = (PostRequestModel) entityMono.block();
		
		if((String) model.getAttribute("status") == "erro") {
			return "redirect:editor-page?status=error";
		}
		
		return "redirect:home?status=success";
	}
	
}
