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
	private int likes;
	public TweetEntity() {
		super();
	}
	public TweetEntity(String tweetedText) {
		this.tweetedText = tweetedText;
	}
	public Integer getTweetId() {
		return tweetId;
	}
	
	public void setTweetId(Integer tweetId) {
		this.tweetId = tweetId;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getTweetedText() {
		return tweetedText;
	}
	public void setTweetedText(String tweet) {
		this.tweetedText = tweet;
	}
	
}
