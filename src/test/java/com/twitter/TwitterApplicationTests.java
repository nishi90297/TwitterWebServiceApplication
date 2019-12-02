package com.twitter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.twitter.api.TwitterAPI;
import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;
import com.twitter.repository.TwitterRepository;
import com.twitter.service.TwitterServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TwitterApplicationTests {

		    @Autowired
			@Mock
		    private TwitterRepository twitterRepository;
		    
		    @Autowired
			@Mock
		    private TwitterServiceImpl twitterService;

		    @InjectMocks
		    private TwitterAPI twitterAPI = new TwitterAPI();

		    
			@Test
			public void testSignUpSuccessFull() throws Exception {

				TwitterEntity user = new TwitterEntity();
				user.setEmailId("nishi@gmail.com");
				user.setUserName("Nishtha");
				user.setGender("female");
				user.setPassword("password");
			
			    when(twitterService.createUser(user)).thenReturn("User Successfully Created !");
			    assertEquals("User Successfully Created !",twitterAPI.createUser(user));
			}

			@Test
			public void testFollowing() throws Exception {
				when(twitterService.followingUser("nishi@gmail.com")).thenReturn("nishi@gmail.com is set into your followings.");
				assertEquals("nishi@gmail.com is set into your followings.",twitterAPI.followingUser("nishi@gmail.com"));
			}
			
			@Test
			public void testFollowers() throws Exception {
				
				List<TwitterEntity> followerslist = new ArrayList<>();
				TwitterEntity userEntity = new TwitterEntity();
				userEntity.setEmailId("nishi@gmail.com");
				userEntity.setUserName("Nishtha");
				userEntity.setGender("female");
				followerslist.add(userEntity);
				
				TwitterEntity twitterEntity = new TwitterEntity();
				twitterEntity.setEmailId("shiv@gmail.com");
				twitterEntity.setUserName("Shivendra");
				twitterEntity.setGender("male");
				twitterEntity.setPassword("password");
				twitterEntity.setFollowers(followerslist);
				
				when(twitterService.followersList()).thenReturn(followerslist);
				
				assertEquals(followerslist, twitterAPI.followersList());
			}
			
			@Test
			public void testPostTweet() throws Exception {
				TweetEntity tweetEntity = new TweetEntity();
				tweetEntity.setTweetedText("Welcome to twitter !");
				tweetEntity.setLikes(0);
				tweetEntity.setTweetId(1);
				
				tweetEntity.setTweetId(tweetEntity.getTweetId()+1);
				
				when(twitterService.postTweet("My name is Nishtha Garg !")).thenReturn("Post created with Id: "+ tweetEntity.getTweetId() + "successfully !");
				assertEquals("Post created with Id: "+ tweetEntity.getTweetId() + "successfully !",twitterAPI.postTweet("My name is Nishtha Garg !"));
			}
			
			@Test
			public void testLikeTweet() throws Exception {

				TweetEntity tweetEntity = new TweetEntity();
				tweetEntity.setTweetedText("Welcome to twitter !");
				tweetEntity.setLikes(0);
				tweetEntity.setTweetId(1);
				
				tweetEntity.setLikes(tweetEntity.getLikes()+1);
				
				when(twitterService.likeTweet(1)).thenReturn("You have successfully liked this post");
				assertEquals("You have successfully liked this post",twitterAPI.likeTweet(tweetEntity.getTweetId()));
			}

			@Test
			public void testHome() throws Exception {

				TweetEntity tweetEntity = new TweetEntity();
				tweetEntity.setTweetedText("Welcome to twitter !");
				tweetEntity.setLikes(0);
				tweetEntity.setTweetId(1);
				
				List<TweetEntity> tweetEntityList = new ArrayList<>();
				tweetEntityList.add(tweetEntity);
				
				when(twitterService.getTweets()).thenReturn(tweetEntityList);
				assertEquals(tweetEntityList,twitterAPI.getTweets());
			}
}