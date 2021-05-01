package com.example.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class RegisterModel {
	
	@NotNull(message = "Value not allowed")
	@Size(max = 50, min = 3, message = "Value not allowed")
	private String nick;
	
	@NotNull(message = "Value not allowed")
	@Size(max = 50, min = 3, message = "Value not allowed")
	private String password;
	
	@NotEmpty(message = "Value not allowed")
	private String date;
	
	private MultipartFile image;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
}
