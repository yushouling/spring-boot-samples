package com.example.shiro.dao;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限配置模拟，实际应从数据库读取
 */
@Repository
public class ShiroSampleDao {

	/**
	 * 根据用户名获取权限
	 * @param username
	 * @return
	 */
	public Set<String> getRolesByUsername(String username) {
		if (username == null) {
			return null;
		}
		Set<String> roles = new HashSet<>();
		switch (username) {
			case "zhangsan":
				roles.add("admin");
				break;
			case "lisi":
				roles.add("guest");
				break;
		}
		return roles;
	}

	/**
	 * 权限配置
	 * @param role
	 * @return
	 */
	public Set<String> getPermissionsByRole(String role) {
		if (role == null) {
			return null;
		}
		Set<String> permissions = new HashSet<>();
		switch (role) {
			case "admin":
				permissions.add("read");
				permissions.add("write");
				break;
			case "guest":
				permissions.add("read");
				break;
		}
		return permissions;
	}

	/**
	 * 密码验证
	 * @param username
	 * @return
	 */
	public String getPasswordByUsername(String username) {
		if (username == null) {
			return null;
		}
		switch (username) {
			case "zhangsan":
				return "zhangsan";
			case "lisi":
				return "lisi";
			default:
				return null;
		}
	}
}
