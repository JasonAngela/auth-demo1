package com.qilang.authdemo1.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author hql
 * @date 2020/6/1 15:27
 */
public class SysUser extends User {


	public SysUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
}

