package com.twitter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.twitter.dao.TwitterDAO;
import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;


@Service(value = "twitterService")
public class TwitterServiceImpl implements TwitterService,UserDetailsService {

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
	
	@Override
	public String likeTweet(int tweetId) throws Exception{
		return twitterDAO.likeTweet(tweetId);
	}
	
	@Override
	public List<TweetEntity> getTweets() throws Exception{
		return twitterDAO.getTweets();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("javainuse".equals(username)) {
			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
