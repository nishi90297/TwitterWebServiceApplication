package com.twitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.dao.TwitterDAO;
import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;


@Service(value = "twitterService")
public class TwitterServiceImpl implements TwitterService{

	@Autowired
	private TwitterDAO twitterDAO;
	
	
	@Override
	public String createUser(TwitterEntity twitterEntity) throws Exception {
		if(twitterEntity.getEmailId()!=null & twitterEntity.getPassword()!=null & twitterEntity.getUserName()!=null & twitterEntity.getGender()!=null) {
			return twitterDAO.createUser(twitterEntity);
		}
		else {
			return "Please Enter All information (EmailId,UserName,Password,Gender)";
		}
	}
	
	@Override
	public String followUser(String emailId) throws Exception{
		return twitterDAO.followUser(emailId);
	}
	
	@Override
	public List<TwitterEntity> followersList() throws Exception{
		return twitterDAO.followersList();
	}
	
	@Override
	public int postTweet(String post) throws Exception{
		return twitterDAO.postTweet(post);
	}
	
	@Override
	public String likeTweet(int tweetId) throws Exception{
		return twitterDAO.likeTweet(tweetId);
	}
	
	@Override
	public List<TweetEntity> getTweets() throws Exception{
		return twitterDAO.getTweets();
	}

}
