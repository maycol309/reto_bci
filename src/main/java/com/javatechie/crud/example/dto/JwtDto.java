package com.javatechie.crud.example.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Builder;

public class JwtDto {

	@Builder.Default
	private String bearer = "Bearer";
	
	private String token;

	public String getBearer() {
		return bearer;
	}

	public void setBearer(String bearer) {
		this.bearer = bearer;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
