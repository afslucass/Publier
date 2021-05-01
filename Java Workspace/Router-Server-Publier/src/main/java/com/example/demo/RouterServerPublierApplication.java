package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@ComponentScan({"com.example.config", "com.example.controller", "com.example.intercepter"})
public class RouterServerPublierApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouterServerPublierApplication.class, args);
	}
	
	@Bean
	public WebClient getWebClientBuilder(){
	    return   WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
	            .codecs(configurer -> configurer
	                      .defaultCodecs()
	                      .maxInMemorySize(16 * 1024 * 1024))
	                    .build())
	                  .build();
	}

}
