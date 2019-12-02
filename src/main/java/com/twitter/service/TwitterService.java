package com.twitter.service;

import java.util.List;


import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;

public interface TwitterService {

	public String createUser(TwitterEntity twitterEntity) throws Exception;
	public String followingUser(String emailId) throws Exception;
	public List<TwitterEntity> followersList() throws Exception;
	public String postTweet(String post) throws Exception;
	public String likeTweet(int tweetId) throws Exception;
	public List<TweetEntity> getTweets() throws Exception;
}
