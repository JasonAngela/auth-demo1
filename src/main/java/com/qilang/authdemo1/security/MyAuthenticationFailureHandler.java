package com.qilang.authdemo1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hql
 * @date 2020/6/2 10:31
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(mapper.writeValueAsString(exception.getMessage()));
	}
}