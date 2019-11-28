package com.twitter;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.twitter.dao.TwitterDAOImpl;
import com.twitter.entity.TwitterEntity;
import com.twitter.repository.TwitterRepository;

@RunWith(MockitoJUnitRunner.class)
public class TwitterApplicationTests {

		    private MockMvc mockMvc;

		    @Mock
		    private TwitterDAOImpl twitterDAO;
		    
		    @Mock
		    private TwitterRepository twitterRepository;

		    @InjectMocks
		    private TwitterAPI twitterAPI = new TwitterAPI();

		    @Before
		    public void init() {
		        mockMvc = MockMvcBuilders.standaloneSetup(twitterAPI).build();
		      
		    }
		    
			@SuppressWarnings("deprecation")
			@Test
			public void signUpSuccessfull() throws Exception {
				TwitterEntity twitterEntity = new TwitterEntity();
				twitterEntity.setEmailId("nishi.90297@gmail.com");
				twitterEntity.setUserName("Nishtha");
				twitterEntity.setGender("female");
				twitterEntity.setPassword("password");
				when(twitterRepository.findById(twitterEntity.getEmailId()).isPresent()).thenReturn(false);
				assertEquals(twitterAPI.createUser(twitterEntity),"User Successfully Created !");
			}

}