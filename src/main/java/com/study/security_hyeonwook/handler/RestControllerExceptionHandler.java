package com.study.security_hyeonwook.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.study.security_hyeonwook.handler.exception.CustomValidationApiException;
import com.study.security_hyeonwook.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class RestControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiExcepiton(CustomValidationApiException e) {
		return ResponseEntity.badRequest().body(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()));
	}
}
