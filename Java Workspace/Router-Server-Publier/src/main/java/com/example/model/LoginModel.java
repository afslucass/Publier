package com.example.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginModel {

	@NotNull(message = "Value not allowed")
	@Size(max = 50, min = 3, message = "Value not allowed")
	private String nick;
	
	@NotNull(message = "Value not allowed")
	@Size(max = 50, min = 3, message = "Value not allowed")
	private String password;

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
}
