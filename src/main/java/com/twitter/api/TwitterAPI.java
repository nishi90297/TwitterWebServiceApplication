package com.twitter.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;
import com.twitter.service.TwitterServiceImpl;

@RestController
@RequestMapping("TwitterAPI")
public class TwitterAPI {
	
	@Autowired
	private TwitterServiceImpl twitterService;
	
	@RequestMapping(value = "signUp", method = RequestMethod.POST)
	public String createUser(@RequestBody TwitterEntity twitterEntity) throws Exception {
		try {
			return twitterService.createUser(twitterEntity);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "Some error Occured ,Please try again !";
		}
	}
	
	@RequestMapping(value = "following", method = RequestMethod.GET)
	public String followingUser(@RequestParam String emailId) throws Exception {
		try {
			return twitterService.followingUser(emailId);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "failed !";
		}
	}
	
	@RequestMapping(value = "followers", method = RequestMethod.GET)
	public List<TwitterEntity> followersList() throws Exception {
		try {
			return twitterService.followersList();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TwitterEntity>();
		}
	}
	
	@RequestMapping(value = "tweet", method = RequestMethod.POST)
	public String postTweet(@RequestParam String post) throws Exception {
		try {
			return twitterService.postTweet(post);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}
	
	@RequestMapping(value = "likeTweet", method = RequestMethod.POST)
	public String likeTweet(@RequestParam int tweetId) throws Exception {
		try {
			return twitterService.likeTweet(tweetId); 
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public List<TweetEntity> getTweets() throws Exception {
		try {
			return twitterService.getTweets();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TweetEntity>();
		}
	}
	
}