package com.twitter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.twitter.dao.TwitterDAOImpl;
import com.twitter.entity.TwitterEntity;

@RunWith(MockitoJUnitRunner.class)
public class createUserServiceTest {
		
		@Autowired
		@Mock
	    private TwitterDAOImpl twitterDAO;
	 

		@Autowired
		@InjectMocks
	    private TwitterServiceImpl twitterService;
		
		@Test
		public void signUpSuccessfullTest() throws Exception {
			TwitterEntity twitterEntity = new TwitterEntity();
			twitterEntity.setEmailId("nishi.90297@gmail.com");
			twitterEntity.setUserName("Nishtha");
			twitterEntity.setGender("female");
			twitterEntity.setPassword("password");
			
			when(twitterDAO.createUser(twitterEntity)).thenReturn("User Successfully Created !");
			assertEquals(twitterService.createUser(twitterEntity),"User Successfully Created !");
		}
		
		@Test
		public void informationMissingTest() throws Exception {
			TwitterEntity twitterEntity = new TwitterEntity();
			twitterEntity.setEmailId("nishi.90297@gmail.com");
			twitterEntity.setUserName("Nishtha");
			twitterEntity.setPassword("password");
			
			assertEquals(twitterService.createUser(twitterEntity),"Please Enter All information (EmailId,UserName,Password,Gender)");
		}

}
