package com.developerscorner.provider.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class AppSecurityConfig {

   private final JwtAuthenticationFilter jwtAuthFilter;
   private final AuthenticationProvider authenticationProvider;
   
   public AppSecurityConfig(JwtAuthenticationFilter jwtFilter, AuthenticationProvider authProvider) {
	   this.jwtAuthFilter = jwtFilter;
	   this.authenticationProvider = authProvider;
   }
   
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
	     .csrf()
	     .disable()
	     .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	     .authorizeRequests()
	     .antMatchers("/auth/**", "/h2-console/**", 
	    		 "/messages", "/secured-room/info?t=*",
	    		 "/v3/api-docs/**",
	    		 "/actuator/**",
	    		 "/swagger-ui/**",
	    		 "/secured-room/**")
	     .permitAll()
	     .anyRequest()
	     .authenticated()
	     .and()
	     .sessionManagement()
	     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	     .and()
	     .authenticationProvider(authenticationProvider)
	     .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

     return http.build();
   }
   
   @Bean
   CorsConfigurationSource corsConfigurationSource() {
   	CorsConfiguration configuration = new CorsConfiguration();
   	configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
   	configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
   	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
   	source.registerCorsConfiguration("/**", configuration);
   	return source;
   }
}