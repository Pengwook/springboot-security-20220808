package com.study.security_hyeonwook.service.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.security_hyeonwook.domain.user.User;
import com.study.security_hyeonwook.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = null;
		
		try {
			userEntity = userRepository.findUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username);
		}
		
		if(userEntity == null) {
			throw new UsernameNotFoundException(username + "사용자이름은 사용 할 수 없습니다.");
		}
		

		public boolean addUser() {
			User user = User.builder()
					.user_name("길현욱")
					.user_email("khw7219@gmail.com")
					.user_id("abcd")
					.user_password(new BCryptPasswordEncoder().encode("1234"))
					.user_roles("ROLE_USER, ROLE_MANAGER")
					.build();

			try {
				userRepository.save(user);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}

}

