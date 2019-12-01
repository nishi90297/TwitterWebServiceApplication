package com.twitter;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.twitter.api.TwitterAPI;
import com.twitter.entity.TweetEntity;
import com.twitter.entity.TwitterEntity;
import com.twitter.repository.TwitterRepository;
import com.twitter.service.TwitterServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TwitterApplicationTests {

		    private MockMvc mockMvc;

		    @Autowired
			@Mock
		    private TwitterRepository twitterRepository;
		    
		    @Mock
		    private TwitterServiceImpl twitterService;

		    @InjectMocks
		    private TwitterAPI controller = new TwitterAPI();

		    @Before
		    public void init() {
		        mockMvc = MockMvcBuilders.standaloneSetup(controller).build(); 
		    }
		    
			@SuppressWarnings("deprecation")
			@Test
			public void testSignUpSuccessFull() throws Exception {

				TwitterEntity user = new TwitterEntity();
				user.setEmailId("nishi@gmail.com");
				user.setUserName("Nishtha");
				user.setGender("female");
				user.setPassword("password");
				
				ObjectMapper mapper = new ObjectMapper();
			    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
			    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
			    String requestJson=ow.writeValueAsString(user);
			    when(twitterService.createUser(user)).thenReturn("User Successfully Created !");
				
			    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signUp").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andReturn();
			    
			    assertNotNull(mvcResult.getResponse().getContentAsString());
			}

			@SuppressWarnings("deprecation")
			@Test
			public void testFollowing() throws Exception {
				MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
				args.add("followingId", "nishi@gmail.com");
				when(twitterService.followingUser("nishi@gmail.com")).thenReturn("nishi@gmail.com is set into your followings.");
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/twitter/following").params(args)).andReturn();
				System.out.println(mvcResult.getResponse().getContentAsString());
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
			
			@SuppressWarnings("deprecation")
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
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/twitter/followers")).andReturn();
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
			
			@SuppressWarnings("deprecation")
			@Test
			public void testPostTweet() throws Exception {
				TweetEntity tweetEntity = new TweetEntity();
				tweetEntity.setTweetedText("Welcome to twitter !");
				tweetEntity.setLikes(0);
				tweetEntity.setTweetId(1);
				
				MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
				args.add("tweetedText", "My name is Nishtha Garg !");
				when(twitterService.postTweet("My name is Nishtha Garg !")).thenReturn("Post created with Id: "+ tweetEntity.getTweetId() + "successfully !");
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/twitter/tweet").params(args)).andReturn();
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
			
			@SuppressWarnings("deprecation")
			@Test
			public void testLikeTweet() throws Exception {

				TweetEntity tweetEntity = new TweetEntity();
				tweetEntity.setTweetedText("Welcome to twitter !");
				tweetEntity.setLikes(0);
				tweetEntity.setTweetId(1);

				MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
				args.add("tweetId", "1");
				tweetEntity.setLikes(tweetEntity.getLikes()+1);
				when(twitterService.likeTweet(1)).thenReturn("You have successfully liked this post as "+ 1 +"th user !");
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/twitter/likeTweet").params(args)).andReturn();
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
			
			@SuppressWarnings("deprecation")
			@Test
			public void testHome() throws Exception {

				TweetEntity tweetEntity = new TweetEntity();
				tweetEntity.setTweetedText("Welcome to twitter !");
				tweetEntity.setLikes(0);
				tweetEntity.setTweetId(1);
				
				List<TweetEntity> tweetEntityList = new ArrayList<>();
				tweetEntityList.add(tweetEntity);
				when(twitterService.getTweets()).thenReturn(tweetEntityList);
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/twitter/home")).andReturn();
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
}