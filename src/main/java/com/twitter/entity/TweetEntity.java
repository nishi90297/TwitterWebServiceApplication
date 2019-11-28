package com.twitter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tweets")
@GenericGenerator(name = "pkgen", strategy = "increment")
public class TweetEntity {
	
	@Id
	@GeneratedValue(generator="pkgen")
	private int tweetId;
	
	@Column(name = "tweet",columnDefinition="VARCHAR(100)")
	private String tweetedText;
	
	@Column(name = "likes",columnDefinition="int")
	private int likes;

	public int getTweetId() {
		return tweetId;
	}

	public void setTweetId(int tweetId) {
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