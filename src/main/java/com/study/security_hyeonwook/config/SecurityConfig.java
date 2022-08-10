package com.study.security_hyeonwook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.study.security_hyeonwook.config.auth.AuthFailureHandler;

@EnableWebSecurity // 기존의 WebSercurityConfigurerAdapter를 비활성 시키고 현재 시큐리티 설정을 따르겠다는 의미 -> 이걸 꼭 해둬야댐!
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {	// 얘를 해줘야 암호화 작동함
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // 이걸 안해두면 문제가 생김
		http.authorizeRequests() // 요청이 들어왔을때 이런 인증을 거쳐라는 의미
			.antMatchers("/", "/index", "/mypage/**") 		// 우리가 지정한 요청, mypage/** -> mypage이후 요청엔 인증을 거쳐라
			.authenticated()					// 인증을 거쳐라
			.anyRequest() 						// 다른 모든 요청은
			.permitAll() 						// 모든 접근 권한을 부여하겠다, 즉 인증을 거칠 필요가 없다, 요기까지 한세트
			.and() // 추가하는거
			.formLogin()						// 로그인 방식은 form로그인을 사용하겠다.
//			.loginPage("/auth/signin") 			// 로그인 페이지는 해당 get요청을 통해 접근한다.
			.loginProcessingUrl("/auth/signin") // 로그인 요청(post요청)
			.failureHandler(new AuthFailureHandler()) // 비밀번호를 틀리면 예외처리를 해줌
			.defaultSuccessUrl("/index"); // 기본적으로 성공했을때 ("/") 인덱스 요청으로 get요청해라
	}
}
