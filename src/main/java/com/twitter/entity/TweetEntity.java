package com.twitter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tweets")
public class TweetEntity {
	
	@Id
	@GeneratedValue
	private Integer tweetId;
	
	@Column(name = "tweet",columnDefinition="VARCHAR(100)")
	private String tweetedText;
	
	@Column(name = "likes",columnDefinition="int")
	private int likes;

	public Integer getTweetId() {
		return tweetId;
	}

	public void setTweetId(Integer tweetId) {
		this.tweetId = tweetId;
	}

	public String getTweetedText() {
		return tweetedText;
	}

	public void setTweetedText(String tweetedText) {
		this.tweetedText = tweetedText;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	
}