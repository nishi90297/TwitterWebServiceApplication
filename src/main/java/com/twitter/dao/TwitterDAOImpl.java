package com.twitter.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;
import com.twitter.repository.TwitterRepository;

@Repository(value = "twitterDAO")
public class TwitterDAOImpl implements TwitterDAO{

	private TwitterEntity currentUser;
	@Autowired
	private TwitterRepository twitterRepository;
	
	@Override
	public String createUser(TwitterEntity twitterEntity) throws Exception {
		
		if(!twitterRepository.findById(twitterEntity.getEmailId()).isPresent()) {
			currentUser=null;
			twitterRepository.save(twitterEntity);
			return "User Successfully Created !";
		}
		return "Email Id Already Existed.";
	}
	
	@Override
	public String loginUser(String emailId, String password) throws Exception{
		
		if(twitterRepository.findById(emailId).isPresent()) {
			if(twitterRepository.findById(emailId).get().getPassword().equals(password)) {
				currentUser=twitterRepository.findById(emailId).get();
				return "User Successfully Login !";
			}
			return "Password Incorrect";
		}
		else {
			return "Email Id Not present";
		}
	}
	
	@Override
	public String followUser(String emailId) throws Exception{
		
		
		if(currentUser==null) {
			return "User no longer exists ! ";
		}
		else if(!twitterRepository.findById(emailId).isPresent()) {
			return "No such user with " + emailId +" exists";
		}
		else if(currentUser.getFollowers().contains(twitterRepository.findById(emailId)) ){
			return emailId +" already exists";
		}
		else {
			System.out.println(currentUser.getFollowers());
			if(currentUser.getFollowers().isEmpty()) {
				List<TwitterEntity> followersList= new ArrayList<>();
				followersList.add(twitterRepository.findById(emailId).get());
				currentUser.setFollowers(followersList);
				twitterRepository.save(currentUser);
				return emailId +" is set into your followers.";
			}
			currentUser.getFollowing().add(twitterRepository.findById(emailId).get());
			twitterRepository.save(currentUser);
			return "You are now following "+ emailId +" !";
		}
	}
	
	@Override
	public List<TwitterEntity> followersList() throws Exception{
		
		if(currentUser!=null) {
			List<TwitterEntity> followersList=currentUser.getFollowers();
			return followersList;
		}
		else {
			return null ;
		}
	}
	
	@Override
	public int postTweet(String post) throws Exception{
		
		 else if (tweetedUser.isPresent()) {
			tweetedUser.get().getTweetsList().add(new Tweet(tweetedText));
			twitterRepo.save(tweetedUser.get());
		} else
			throw new DataNotFoundException("No user exists with the given id: " + currentUser);
		List<Tweet> tweets = tweetedUser.get().getTweetsList();
		return tweets.get(tweets.size() - 1);
		if(currentUser!=null) {
			TweetEntity tweetEntity= new TweetEntity();
			tweetEntity.setTweetedText(post);
			
			if(currentUser.getTweetsList().isEmpty()) {
				List<TweetEntity> tweetsList= new ArrayList<>();
				tweetsList.add(tweetEntity);
				currentUser.setTweetsList(tweetsList);
				twitterRepository.save(currentUser);
			}
			currentUser.getTweetsList().add(tweetEntity);
			twitterRepository.save(currentUser);
			return tweetEntity.getTweetId();
		}
		return 0;
	}
}