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

			
}
