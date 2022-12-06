package com.dantn.bookStore.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.UserRoleService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.UserRoleSingleton;


@EnableWebSecurity
public class SpringSecurityConfig implements UserDetailsService{
	private UserService service;
	private UserRoleService roleService;
	private User u;
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
		http.authorizeHttpRequests(authz -> authz.antMatchers("/","/index/**","/smart-book/home","/api/book/suggest","/smart-book/book","/smart-book","/smart-book/login","/api/book/search","/api/user/**","/smart-book/registry","/api/book/**").permitAll()
				.anyRequest().hasRole("USER")
				).rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000).and()
		.formLogin().loginPage("/smart-book/login").loginProcessingUrl("/login").failureUrl("/smart-book/login?error=true").permitAll().defaultSuccessUrl("/#/home").failureHandler(new AuthenticationFailureHandler() {
			
			@Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {
                // TODO Auto-generated method stub
                if(u==null) {
                    request.getSession().setAttribute("message", "Tài khoản hoặc mật khẩu không chính xác");
                    response.sendRedirect("/smart-book/login?error=true");
                }else {
                    if(u.getStatus().getId()==2) {
                        request.getSession().setAttribute("message", "Tài khoản đã bị vô hiệu");
                        response.sendRedirect("/smart-book/login?error=true");
                    }else {
                        request.getSession().setAttribute("message", "Tài khoản hoặc mật khẩu không chính xác");
                        response.sendRedirect("/smart-book/login?error=true");
                    }
                }
            }
		})
		.and().logout().permitAll().and().csrf().disable().cors();
		// @formatter:on
		
		return http.build();
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		u = service.getUserByRoleAndEmail(UserRoleSingleton.getInstance(roleService).get(0),username);
		if (u == null) {
			throw new UsernameNotFoundException(username);
		} else {
			UserDetails user =  org.springframework.security.core.userdetails.User.builder()
					.username(u.getEmail()).password(u.getPassword()).roles(u.getRole().getValue())
					.disabled(u.getStatus().getId() ==1? false : true).build();
			AppConstraint.USER=u;
			return user;
		}
	}
}
