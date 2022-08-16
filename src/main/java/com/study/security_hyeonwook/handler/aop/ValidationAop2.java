package com.study.security_hyeonwook.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.study.security_hyeonwook.handler.exception.CustomValidationApiException;

@Aspect
@Component
public class ValidationAop2 {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Pointcut("@annotation(com.study.security_hyeonwook.handler.aop.annotation.ValidCheck2)")
	private void enableValid() {}
	
	@Before("enableValid()")
	public void ValidBefore(JoinPoint joinpoint) {	// before에서는 그냥 joinpoint를 씀
		Object[] args = joinpoint.getArgs();
		LOGGER.info(">>>>>유효성 검사 중...");
		
		for(Object arg : args) {
			if(arg.getClass() == BeanPropertyBindingResult.class) {	//  객체비교방법
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) {	// 에러가 있는지 확인
					Map<String, String> errorMap = new HashMap<String, String>();
					
					bindingResult.getFieldErrors().forEach(error -> {
						errorMap.put(error.getField(), error.getDefaultMessage());
					});
					
					throw new CustomValidationApiException("유효성 검사 실패", errorMap);
				}
			}
		}
	}
	
	@AfterReturning(value = "enableValid()", returning = "returnObj")	// after랑 비슷하지만 afterreturning은 return이 있을때 사용
	public void afterReturn(JoinPoint joinpoint, Object returnObj) {
		LOGGER.info("유효성 검사 완료: {}", returnObj);
	}
}