package com.qilang.authdemo1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qilang.authdemo1.util.Json;
import com.qilang.authdemo1.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hql
 * @date 2020/6/2 10:29
 */
@Slf4j
@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	@Lazy
	private DefaultTokenServices tokenServices;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		try {
			TokenRequest tokenRequest = new TokenRequest(null, null, null, null);
			// 简化
			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(new BaseClientDetails());
			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
			OAuth2AccessToken oAuth2AccessToken = tokenServices.createAccessToken(oAuth2Authentication);
			log.info("获取token 成功：{}", oAuth2AccessToken.getValue());
			response.setCharacterEncoding("UTF-8");
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			//返回JSON
			String result = Json.toJsonString(JsonResult.builder()
					.code(HttpStatus.OK.value())
					.data(oAuth2AccessToken)
					.build());
			PrintWriter printWriter = response.getWriter();
			printWriter.append(result);
		} catch (IOException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}
	}
}

