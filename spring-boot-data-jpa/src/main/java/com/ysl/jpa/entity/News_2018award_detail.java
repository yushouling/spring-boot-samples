package com.ysl.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class News_2018award_detail {
	@Id
	public int id;

	public String userId;

	public int score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
