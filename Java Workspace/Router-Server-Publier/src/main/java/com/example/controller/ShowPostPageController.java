package com.example.controller;

import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.model.PostRequestFromAPIModel;
import com.example.model.PostRequestModel;
import com.example.model.UserRequestModel;
import com.example.services.HandlerUploadFile;

import reactor.core.publisher.Mono;

@Controller
public class ShowPostPageController {

	@GetMapping("/show-post-controller/{id}")
	public String showPost(@PathVariable("id") String id, Model model) {
		
		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 20)).build();
		
		WebClient client = WebClient.builder().exchangeStrategies(exchangeStrategies).baseUrl("http://localhost:3000").build();
		Mono<Object> entityMono = client.get()
				.uri("/document/get/" + id)
				.exchangeToMono((response) -> {
					if(response.statusCode().equals(HttpStatus.OK)) {
						return response.bodyToMono(PostRequestFromAPIModel.class);
					} else {
						return response.bodyToMono(PostRequestFromAPIModel.class);
					}
				});
		//https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client
		
		PostRequestFromAPIModel document = (PostRequestFromAPIModel) entityMono.block();
		model.addAttribute("document", document);
		return "showPostPage";
	}
}
