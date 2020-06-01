package com.qilang.authdemo1.security;

import com.qilang.authdemo1.model.SysUser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author hql
 * @date 2020/6/1 14:41
 */
@Slf4j
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(!"admin".equals(username)) {
			throw  new UsernameNotFoundException("除了admin用户，其余不准登录!");
		}

		Collection<? extends GrantedAuthority> authorities =
				AuthorityUtils.createAuthorityList("ROLE_ADMIN");

		return new SysUser(username, "123456", authorities);
	}
}
