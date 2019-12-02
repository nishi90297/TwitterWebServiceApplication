package com.twitter.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;
import com.twitter.repository.TweetRepository;
import com.twitter.repository.TwitterRepository;

@Repository(value = "twitterDAO")
public class TwitterDAOImpl implements TwitterDAO,UserDetailsService, PasswordEncoder {

	private TwitterEntity currentUser;
	
	@Autowired
	private TwitterRepository twitterRepository;
	
	@Autowired
	private TweetRepository tweetRepository;
	
	@Override
	public String createUser(TwitterEntity twitterEntity) throws Exception {
		
		if(!twitterRepository.findById(twitterEntity.getEmailId()).isPresent()) {
			twitterRepository.save(twitterEntity);
			return "User Successfully Created !";
		} 
		return "Email Id Already Existed.";
	}
	 
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		
		if (twitterRepository.findById(emailId).get().getEmailId().equals(emailId)) {
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); 
			String password=encoder.encode(twitterRepository.findById(emailId).get().getPassword());
			currentUser=twitterRepository.findById(emailId).get();
			return new User(twitterRepository.findById(emailId).get().getEmailId(),password,
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + emailId);
		}
	}
	
	
	@Override
	public String followingUser(String emailId) throws Exception{
		
		if(!twitterRepository.findById(emailId).isPresent()) {
			return "No such user with emailId: " + emailId +" exists";
		}
		else if(currentUser.getEmailId().equals(emailId) ){
			return "can't Follow Yourself";
		}
		else if(!(currentUser.getFollowing().stream().filter
				(
						follower->follower.getEmailId()
						.equals(twitterRepository.findById(emailId).get().getEmailId()))
				)
				.collect(Collectors.toList()).isEmpty()){
			return emailId +" already exists";
		}
		else {
			if(currentUser.getFollowing().isEmpty()) {
				List<TwitterEntity> followingList= new ArrayList<>();
				followingList.add(twitterRepository.findById(emailId).get());
				currentUser.setFollowing(followingList);
				twitterRepository.save(currentUser);
				return emailId +" is set into your followings.";
			}
			currentUser.getFollowing().add(twitterRepository.findById(emailId).get());
			twitterRepository.save(currentUser);
			return emailId +" is set into your followings.";
		}
	}
	
	@Override
	public List<TwitterEntity> followersList() throws Exception{
		
		List<TwitterEntity> followersList=currentUser.getFollowing();
		return followersList;
	}
	
	@Override
	public String postTweet(String post) throws Exception{
		TweetEntity tweetEntity= new TweetEntity();
			tweetEntity.setTweetedText(post);
			tweetRepository.save(tweetEntity);
			if(currentUser.getTweetsList().isEmpty()) {
				List<TweetEntity> tweetsList= new ArrayList<>();
				tweetsList.add(tweetEntity);
				currentUser.setTweetsList(tweetsList);
				twitterRepository.save(currentUser);
				
				return "Post created with Id: "+ tweetEntity.getTweetId() + "successfully !";
			}
			currentUser.getTweetsList().add(tweetEntity);
			twitterRepository.save(currentUser);
			return "Post created with Id: "+ tweetEntity.getTweetId() + "successfully !";
	}
	
	@Override
	public String likeTweet(int tweetId) throws Exception{
		if(!tweetRepository.findById(tweetId).isPresent()) {
			return "Tweet Do not exists!";
		}
		else {
			int likes=tweetRepository.findById(tweetId).get().getLikes();
			likes+=1;
			tweetRepository.findById(tweetId).get().setLikes(likes);
			tweetRepository.save(tweetRepository.findById(tweetId).get());
			return "You have successfully liked this post";
		} 
	} 
	
	@Override
	public List<TweetEntity> getTweets() throws Exception {
		
		List<TweetEntity> top10List= new ArrayList<TweetEntity>();
		List<TweetEntity> tweetList=tweetRepository.findAll();
		
		int noOfTweets=tweetList.size();
		
		if(noOfTweets>10) {
			int x=noOfTweets;
			while(x>=noOfTweets-10) {
				top10List.add(tweetList.get(x-1));
				x--;
			}
		}
		else {
			Collections.reverse(tweetList);
			return tweetList;
		}
		return top10List;
		
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