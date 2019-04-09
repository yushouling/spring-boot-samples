package com.ysl.springbootmybatis;

import com.ysl.dao.mapper.UserMapper;
import com.ysl.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	public void getUser() {
		User user = userMapper.getUser("2");
		System.out.println(user);
	}

	@Test
	public void getUserList() {
		List<User> user = userMapper.getUserList();
		System.out.println(user);
	}

	/**
	 * 使用XML的sql语句
	 */
	@Test
	public void getUserNameWithXML() {
		String userName = userMapper.getUserName("2");
		System.out.println(userName);
	}

}
