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
	public ResponseEntity<String> createUser(@RequestBody TwitterEntity twitterEntity) throws Exception {
		try {
			String response=twitterService.createUser(twitterEntity);
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Some error Occured ,Please try again !", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "following", method = RequestMethod.GET)
	public ResponseEntity<String> followUser(@RequestParam String emailId) throws Exception {
		try {
			String response=twitterService.followUser(emailId);
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("failed !", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "followers", method = RequestMethod.GET)
	public ResponseEntity<List<TwitterEntity>> followersList() throws Exception {
		try {
			List<TwitterEntity> twitterEntityList=twitterService.followersList();
			return new ResponseEntity<List<TwitterEntity>>(twitterEntityList, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			List<TwitterEntity> twitterEntityList= new ArrayList<>();
			return new ResponseEntity<List<TwitterEntity>>(twitterEntityList, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "tweet", method = RequestMethod.POST)
	public ResponseEntity<String> postTweet(@RequestParam String post) throws Exception {
		try {
			int response =twitterService.postTweet(post);
			String result="";
			if(response==0) {
				result="User not login !";
			}
			else {
				result="Post created with Id: "+ response + "successfully !";
			}
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("failed", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "likeTweet", method = RequestMethod.POST)
	public ResponseEntity<String> likeTweet(@RequestParam int tweetId) throws Exception {
		try {
			String result=twitterService.likeTweet(tweetId); 
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("failed", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public ResponseEntity<List<TweetEntity>> getTweets() throws Exception {
		try {
			 List<TweetEntity> tweetsList=twitterService.getTweets(); 
			return new ResponseEntity<List<TweetEntity>>(tweetsList, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
			List<TweetEntity> tweetsList=new ArrayList<>();
			return new ResponseEntity<List<TweetEntity>>(tweetsList, HttpStatus.BAD_REQUEST);
		}
	}
	
}