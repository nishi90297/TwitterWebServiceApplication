package com.twitter;
import static org.junit.Assert.assertEquals;
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
import com.twitter.entity.TwitterEntity;
import com.twitter.service.TwitterServiceImpl;

import pst.twitter.controller.TwitterRestcontroller;
import pst.twitter.model.Tweet;
import pst.twitter.model.TwitterUser;
import pst.twitter.service.TwitterService;

@RunWith(MockitoJUnitRunner.class)
public class TwitterApplicationTests {

		    private MockMvc mockMvc;

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
			public void testSignUp() throws Exception {
				TwitterEntity user = new TwitterEntity();
				user.setEmailId("varshu@gmail.com");
				user.setUserName("varshu");
				user.setGender("female");
				user.setPassword("password");
				ObjectMapper mapper = new ObjectMapper();
			    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
			    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
			    String requestJson=ow.writeValueAsString(user);
			    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signUp").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andReturn();
			    assertEquals("successfully created", mvcResult.getResponse().getContentAsString());
			}

			@SuppressWarnings("deprecation")
			@Test
			public void testLogin() throws Exception {
				MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
				args.add("email", "varshu@gmail.com");
				args.add("password", "password");
				when(twitterService.checkUserLogin("varshu@gmail.com", "password")).thenReturn(new TwitterUser());
				String random = RandomStringUtils.randomAlphanumeric(10);
				when(twitterService.getToken()).thenReturn(random);
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/login").params(args)).andReturn();
				assertEquals(random.length(), mvcResult.getResponse().getContentAsString().substring(11).length());
				assertEquals(random, mvcResult.getResponse().getContentAsString().substring(11));
			}

			@SuppressWarnings("deprecation")
			@Test
			public void testFollow() throws Exception {
				MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
				args.add("followingId", "varshu@gmail.com");
				when(twitterService.updateFollow("varshu@gmail.com")).thenReturn("followed varshu@gmail.com succesfully");
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/twitter/follow").params(args)).andReturn();
				assertEquals("followed varshu@gmail.com succesfully", mvcResult.getResponse().getContentAsString());
			}
			
			@SuppressWarnings("deprecation")
			@Test
			public void testFollowers() throws Exception {
				
				List<TwitterUser> followerslist = new ArrayList<>();
				TwitterUser userA = new TwitterUser();
				userA.setEmailId("kish@gmail.com");
				userA.setUserName("kish");
				userA.setGender("female");
				followerslist.add(userA);
				
				TwitterUser user = new TwitterUser();
				user.setEmailId("varshu@gmail.com");
				user.setUserName("varshu");
				user.setGender("female");
				user.setPassword("password");
				user.setFollowers(followerslist );
				
				when(twitterService.followers()).thenReturn(followerslist);
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/twitter/followers")).andReturn();
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
			
			@SuppressWarnings("deprecation")
			@Test
			public void testTweet() throws Exception {
				Tweet tweet = new Tweet();
				tweet.setTweetedText("how r u");
				tweet.setLikes(0);
				tweet.setTweetId(1);
				MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
				args.add("tweetedText", "how r u");
				when(twitterService.postTweet("how r u")).thenReturn(tweet);
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/twitter/tweet").params(args)).andReturn();
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
			
			@SuppressWarnings("deprecation")
			@Test
			public void testlike() throws Exception {

				Tweet tweet = new Tweet();
				tweet.setTweetedText("how r u");
				tweet.setLikes(0);
				tweet.setTweetId(1);

				MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
				args.add("tweetId", "1");
				tweet.setLikes(tweet.getLikes()+1);
				when(twitterService.likeTweet(1)).thenReturn(tweet);
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/twitter/like").params(args)).andReturn();
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
			
			@SuppressWarnings("deprecation")
			@Test
			public void testHome() throws Exception {

				Tweet tweet = new Tweet();
				tweet.setTweetedText("how r u");
				tweet.setLikes(0);
				tweet.setTweetId(1);
				
				List<Tweet> tweets = new ArrayList<>();
				tweets.add(tweet);
				when(twitterService.getTweets()).thenReturn(tweets);
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/twitter/home")).andReturn();
				assertNotNull(mvcResult.getResponse().getContentAsString());
			}
}
