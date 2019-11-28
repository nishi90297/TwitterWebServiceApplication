package com.twitter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twitter.dao.TwitterDAO;
import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;
import com.twitter.repository.TwitterRepository;


@Service(value = "twitterService")
public class TwitterServiceImpl implements TwitterService,UserDetailsService, PasswordEncoder {

	@Autowired
	private TwitterDAO twitterDAO;
	
	@Autowired
	private TwitterRepository twitterRepository;
	
	@Override
	public String createUser(TwitterEntity twitterEntity) throws Exception {
		if(twitterEntity.getEmailId()!=null & twitterEntity.getPassword()!=null & twitterEntity.getUserName()!=null & twitterEntity.getGender()!=null) {
			return twitterDAO.createUser(twitterEntity);
		}
		else {
			return "Please Enter All information (EmailId,UserName,Password,Gender)";
		}
	}
	
//	@Override
//	public String loginUser(String emailId, String password) throws Exception {
//		return twitterDAO.loginUser(emailId, password);
//	}
	
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
		
		
		if (twitterRepository.findById(username).get().getEmailId().equals(username)) {
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); 
			String s=encoder.encode(twitterRepository.findById(username).get().getPassword());
			System.out.println(s);
			return new User(twitterRepository.findById(username).get().getEmailId(),s,
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	@Override
	public String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		return false;
	}
}
