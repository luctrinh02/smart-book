package com.dantn.bookStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.UserRoleService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.ultilities.UserRoleSingleton;


@EnableWebSecurity
public class SpringSecurityConfig implements UserDetailsService{
	private UserService service;
	private UserRoleService roleService;
	
	public SpringSecurityConfig(UserService service, UserRoleService roleService) {
		super();
		this.service = service;
		this.roleService = roleService;
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeHttpRequests(authz -> authz.antMatchers("/**").permitAll()
				.antMatchers("/api/admin/**","/admin/**").hasRole("0")
				.anyRequest().hasRole("1")
				).rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000).and().formLogin().permitAll().and()
			.logout().permitAll().and().csrf().disable().cors();
		// @formatter:on
		return http.build();
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = service.getUserByRoleAndEmail(UserRoleSingleton.getInstance(roleService).get(0),username);
		if (u == null) {
			throw new UsernameNotFoundException(username);
		} else {
			UserDetails user =  org.springframework.security.core.userdetails.User.builder()
					.username(u.getEmail()).password(u.getPassword()).roles(u.getRole().getValue())
					.disabled(u.getStatus().getId() ==1? false : true).build();
			return user;
		}
	}
}
