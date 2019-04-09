package com.ysl.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ysl.jpa.entity.News_2018award_detail;

public interface MyResp extends JpaRepository<News_2018award_detail, Integer> {
	
	@Query(value="from News_2018award_detail where userId=:userId")
	public List<News_2018award_detail> queryByUser(@Param("userId") String userId);
}
