package com.example.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.intercepter.QuickUserNotAutenticated;

@Configuration
@EnableWebMvc
public class ViewControllers implements WebMvcConfigurer {

	@Autowired QuickUserNotAutenticated quickUserNotAutenticated;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// Talves nem precise, os controllers estao redirecionando para os templates sem problemas
	    registry.addViewController("/").setViewName("index");
	    registry.addViewController("/register").setViewName("register");
	    registry.addViewController("/editor-page").setViewName("editorPage");
	    registry.addViewController("/home").setViewName("home");
	    registry.addViewController("/show-post-page").setViewName("showPostPage");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addInterceptors(registry);
		
		ArrayList<String> list = new ArrayList<>();
		list.add("/");
		list.add("/register");
		list.add("/login-controller");
		list.add("/register-controller");
		list.add("/css/**");
		list.add("/js/**");
		list.add("/imagens/**");
		list.add("/Roboto/**");
		registry.addInterceptor(quickUserNotAutenticated).addPathPatterns("/**").excludePathPatterns(list);
	}
	
}
