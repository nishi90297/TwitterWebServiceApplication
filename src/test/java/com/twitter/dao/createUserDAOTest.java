package com.twitter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.twitter.dao.TwitterDAOImpl;
import com.twitter.entity.TwitterEntity;
import com.twitter.repository.TwitterRepository;

@RunWith(MockitoJUnitRunner.class)
public class createUserDAOTest {
	
		@Autowired
		@Mock
	    private TwitterRepository twitterRepository;
		
		@Autowired
		@InjectMocks
	    private TwitterDAOImpl twitterDAO;
	 
	    
		@Test
		public void signUpSuccessfullTest() throws Exception {
			TwitterEntity twitterEntity = new TwitterEntity();
			twitterEntity.setEmailId("nishi.90297@gmail.com");
			twitterEntity.setUserName("Nishtha");
			twitterEntity.setGender("female");
			twitterEntity.setPassword("password");
			
			when(twitterRepository.findById(twitterEntity.getEmailId())).thenReturn(Optional.ofNullable(null));
			assertEquals(twitterDAO.createUser(twitterEntity),"User Successfully Created !");
		}
		
		@Test
		public void EmailAlreadyExistsTest() throws Exception {
			TwitterEntity twitterEntity = new TwitterEntity();
			twitterEntity.setEmailId("nishi.90297@gmail.com");
			twitterEntity.setUserName("Nishtha");
			twitterEntity.setGender("female");
			twitterEntity.setPassword("password");
			
			when(twitterRepository.findById(twitterEntity.getEmailId())).thenReturn(Optional.of(twitterEntity));
			assertEquals(twitterDAO.createUser(twitterEntity),"Email Id Already Existed.");
		}

}
