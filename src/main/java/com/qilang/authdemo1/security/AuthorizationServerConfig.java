package com.qilang.authdemo1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author hql
 * @date 2020/5/29 10:34
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	/**
	 * 该对象用来支持 password 模式
	 */
	@Autowired
	AuthenticationManager authenticationManager;

	/**
	 * 该对象用来将令牌信息存储到redis中
	 */
	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	/**
	 * 该对象将为刷新token提供支持
	 */
	@Autowired
	UserDetailsService userDetailsService;

	/**
	 * 指定密码的加密方式(应该有本身认证服务确定)
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		// 使用BCrypt强哈希函数加密方案（密钥迭代次数默认为10）
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		//配置令牌的存储（这里存放在redis中）
		endpoints.tokenStore(tokenStore())
				.tokenServices(tokenService())
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
	}

	@Bean
	public TokenStore tokenStore() {
		//使用redis存储token
		RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
		//设置redis token存储中的前缀
		redisTokenStore.setPrefix("auth-token:");
		return redisTokenStore;
	}

	@Bean
	public DefaultTokenServices tokenService() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		//配置token存储
		tokenServices.setTokenStore(tokenStore());
		//开启支持refresh_token，此处如果之前没有配置，启动服务后再配置重启服务，可能会导致不返回token的问题，解决方式：清除redis对应token存储
		//tokenServices.setSupportRefreshToken(true);
		//复用refresh_token
		//tokenServices.setReuseRefreshToken(true);
		//token有效期，设置12小时
		tokenServices.setAccessTokenValiditySeconds(12 * 60 * 60);
		//refresh_token有效期，设置一周
		//tokenServices.setRefreshTokenValiditySeconds(7 * 24 * 60 * 60);
		return tokenServices;
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
				// 开启/oauth/token_key验证端口无权限访问
				.tokenKeyAccess("permitAll()")
				// 开启/oauth/check_token验证端口认证权限访问
				.checkTokenAccess("isAuthenticated()");
	}
}
