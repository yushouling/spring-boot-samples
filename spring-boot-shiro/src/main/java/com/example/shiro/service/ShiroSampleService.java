package com.example.shiro.service;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑处理，带权限拦截
 */
@Service
public class ShiroSampleService {

	/**
	 * 只读权限
	 * @return
	 */
    @RequiresPermissions("read")
    public String read() {
        return "reading...";
    }

	/**
	 * 只写权限
	 * @return
	 */
	@RequiresPermissions("write")
    public String write() {
        return "writting...";
    }

	/**
	 * 读和写权限
	 * @return
	 */
	@RequiresPermissions({"read", "write"})
	public String readAndWrite() {
		return "reading and writting...";
	}
}
