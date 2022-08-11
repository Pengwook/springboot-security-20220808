package com.study.security_hyeonwook.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.study.security_hyeonwook.handler.aop.annotation.Log;

@Log
@Aspect
@Component
public class TimerAop {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());	
	
	@Pointcut("execution(* com.study.security_hyeonwook.web.controller..*.*(..))") // 만약 *RestController -> 앞에는 상관없지만 뒤가 RestController로 끝나는 메소드명을 가진것들을 around시켜라
	private void pointCut() {}
	
	@Pointcut("@annotation(com.study.security_hyeonwook.handler.aop.annotation.Timer)")
	private void enableTimer() {}
	
	@Around("pointCut() && eanbleTimer()") // 어노테이션이 달려있어야함 execution쪽
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object result = joinPoint.proceed();
		
		stopWatch.stop();
		
		LOGGER.info(">>>>> {}({}) 메소드 실행 시간: {}ms", 
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(),
				stopWatch.getTotalTimeSeconds());
		
		return result;
	}
}
