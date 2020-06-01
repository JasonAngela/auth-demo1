package com.qilang.authdemo1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hql
 * @date 2020/6/1 15:43
 */
@RestController
@RequestMapping("/sysUser")
public class SysLoginController {
	/**
	 * 分页获取
	 */
	@PostMapping("/login")
	public ResponseEntity page() {
		/*IPage<Area> sysUserPage = areaService.page(page, new LambdaQueryWrapper<Area>());
		return ResponseEntity.ok(sysUserPage);*/

		return null;
	}
}
