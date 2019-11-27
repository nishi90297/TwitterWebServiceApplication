package com.twitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.dao.TwitterDAO;
import com.twitter.entity.TwitterEntity;


@Service(value = "twitterService")
public class TwitterServiceImpl implements TwitterService {

	@Autowired
	private TwitterDAO twitterDAO;
	
	@Override
	public String createUser(TwitterEntity twitterEntity) throws Exception {
		 return twitterDAO.createUser(twitterEntity);
	}
	
	@Override
	public String loginUser(String emailId, String password) throws Exception {
		return twitterDAO.loginUser(emailId, password);
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
}
