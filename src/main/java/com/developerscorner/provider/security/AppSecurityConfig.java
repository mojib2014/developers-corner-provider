package com.developerscorner.provider.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	public AppSecurityConfig(JwtAuthenticationFilter jwtFilter, AuthenticationProvider authProvider) {
		this.jwtAuthFilter = jwtFilter;
		this.authenticationProvider = authProvider;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/auth/**", "/h2-console/**", "/ws/**", "/v3/api-docs/**",
						"/actuator/**", "/swagger-ui/**", "/users/logout")
				.permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler())
				.and().sessionManagement()
				.and()
				.logout()
				.logoutUrl("/users/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "jwt")
				.permitAll()
				.clearAuthentication(true)
				.and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		http.cors();
		return http.build();
	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

}