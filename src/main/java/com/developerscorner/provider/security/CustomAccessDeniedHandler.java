package com.developerscorner.provider.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Custom access denied handler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
    	log.info("custom access denied handler called========================");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.sendRedirect(request.getContextPath() + "/denied");

    }

}
