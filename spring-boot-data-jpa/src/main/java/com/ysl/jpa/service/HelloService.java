package com.ysl.jpa.service;

import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.ysl.jpa.MyResp;
import com.ysl.jpa.entity.News_2018award_detail;

@Service
public class HelloService {
	@Resource
	private MyResp jpaRepo;
	
	public List<News_2018award_detail> findAll() {
		List<News_2018award_detail> list = jpaRepo.findAll();
		return list;
	}
	
	public List<News_2018award_detail> queryByUser(String userId) {
		return jpaRepo.queryByUser(userId);
	}

	/**
	 * 事务回滚测试
	 */
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(int id) {
		jpaRepo.delete(id);
		// 如果异常被catch，将不会回滚
		System.out.println(1/0);
	}
}
