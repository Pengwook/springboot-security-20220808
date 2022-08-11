package com.study.security_hyeonwook.handler.aop.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })	// TYPE -> 클래스, 누구앞에 할지 정하는게 target
public @interface Timer {

}
