package com.study.security_hyeonwook.handler.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class CustomValidationApiException extends RuntimeException {
	private Map<String, String> errorMap;
	
	public CustomValidationApiException() {
		this("error", new HashMap<String, String>());
	}
	
	public CustomValidationApiException(String message) {
		this(message, new HashMap<String, String>());
	}
	
	public CustomValidationApiException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}
}
