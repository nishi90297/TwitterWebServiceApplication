package com.twitter.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if (twitterRepository.findById(username).get().getEmailId().equals(username)) {
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); 
			String password=encoder.encode(twitterRepository.findById(username).get().getPassword());
			currentUser=twitterRepository.findById(username).get();
			return new User(twitterRepository.findById(username).get().getEmailId(),password,
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	
	@Override
	public String followUser(String emailId) throws Exception{
		
		if(!twitterRepository.findById(emailId).isPresent()) {
			return "No such user with emailId: " + emailId +" exists";
		}
//		else if(currentUser.getFollowers().contains(twitterRepository.findById(emailId).get()) ){
//			return emailId +" already exists";
//		}
//		else if(currentUser.getFollowers().stream().filter(follower->
//		{
//			follower.getEmailId().equals(twitterRepository.findById(emailId).get().getEmailId());
//			
//		})){
//			return emailId +" already exists";
//		}
		else if(currentUser.getEmailId().equals(emailId) ){
			return "can't Follow Yourself";
		}
		else {
			if(currentUser.getFollowers().isEmpty()) {
				List<TwitterEntity> followersList= new ArrayList<>();
				followersList.add(twitterRepository.findById(emailId).get());
				currentUser.setFollowers(followersList);
				twitterRepository.save(currentUser);
				return emailId +" is set into your followers.";
			}
			currentUser.getFollowers().add(twitterRepository.findById(emailId).get());
			twitterRepository.save(currentUser);
			return emailId +" is set into your followers.";
		}
	}
	
	@Override
	public List<TwitterEntity> followersList() throws Exception{
		
		List<TwitterEntity> followersList=currentUser.getFollowers();
		return followersList;
	}
	
	@Override
	public int postTweet(String post) throws Exception{
		if(currentUser!=null) {
			TweetEntity tweetEntity= new TweetEntity();
			tweetEntity.setTweetedText(post);
			tweetRepository.save(tweetEntity);
			if(currentUser.getTweetsList().isEmpty()) {
				List<TweetEntity> tweetsList= new ArrayList<>();
				tweetsList.add(tweetEntity);
				currentUser.setTweetsList(tweetsList);
				twitterRepository.save(currentUser);
				
				return tweetEntity.getTweetId();
			}
			currentUser.getTweetsList().add(tweetEntity);
			twitterRepository.save(currentUser);
			return tweetEntity.getTweetId();
		}
		return 0;
	}
	
	@Override
	public String likeTweet(int tweetId) throws Exception{
		if(currentUser==null) {
			return "User not login !";
		}
		else if(!tweetRepository.findById(tweetId).isPresent()) {
			return "Tweet Do not exists!";
		}
		else {
			int likes=tweetRepository.findById(tweetId).get().getLikes();
			likes+=1;
			tweetRepository.findById(tweetId).get().setLikes(likes);
			tweetRepository.save(tweetRepository.findById(tweetId).get());
			return "You have successfully liked this post as "+ likes +"th user !";
		}
	}
	
	@Override
	public List<TweetEntity> getTweets() throws Exception {
		
//		List<TweetEntity> top10List= new ArrayList<TweetEntity>();
//		List<TweetEntity> tweetList=tweetRepository.findAll();
//		
//		int noOfTweets=tweetList.size();
//		
//		for(int i=noOfTweets-1;i>=noOfTweets-11;i--){
//			top10List.add(tweetList.get(i));
//		}
//		return top10List;
		List<TweetEntity> tweetList = null;
		int noOfTweets= tweetRepository.findAll().size();
		Page<TweetEntity> tweetPage = tweetRepository.findAll(PageRequest.of(noOfTweets, 10,Direction.DESC));
		if (tweetPage.hasContent()) {
			tweetList = tweetPage.getContent();
		}
		return tweetList;
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