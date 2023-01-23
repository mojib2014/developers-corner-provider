package com.developerscorner.provider.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBroker implements WebSocketMessageBrokerConfigurer {
	
	 @Override
	  public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/queue");
		config.setApplicationDestinationPrefixes("/student-mentor-chat");
//	    config.setUserDestinationPrefix("/user");
	  }

	  @Override
	  public void registerStompEndpoints(StompEndpointRegistry registry) {
	    registry.addEndpoint("/secured-room").setAllowedOrigins("http://localhost:8080").withSockJS();
	  }
	  
	  @Override
	  public boolean configureMessageConverters(List<MessageConverter> messageConverter) {
		  DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
		  resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
		  MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		  converter.setObjectMapper(new ObjectMapper());
		  converter.setContentTypeResolver(resolver);
		  messageConverter.add(converter);
		  return false;
	  }
}
