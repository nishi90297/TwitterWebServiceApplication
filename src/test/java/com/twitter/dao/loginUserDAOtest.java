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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.twitter.dao.TwitterDAOImpl;
import com.twitter.entity.TwitterEntity;
import com.twitter.repository.TwitterRepository;

@RunWith(MockitoJUnitRunner.class)
public class loginUserDAOtest {
	
		@Autowired
		@Mock
	    private TwitterRepository twitterRepository;
		
		@Autowired
		@InjectMocks
	    private TwitterDAOImpl twitterDAO;
	 
	    
		@Test
		public void loginSuccessfullTest() throws Exception {
			TwitterEntity twitterEntity = new TwitterEntity();
			twitterEntity.setEmailId("nishi.90297@gmail.com");
			twitterEntity.setUserName("Nishtha");
			twitterEntity.setGender("female");
			twitterEntity.setPassword("password");
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); 
			String password=encoder.encode(twitterRepository.findById(twitterEntity.getEmailId()).get().getPassword());
			
			when(twitterRepository.findById(twitterEntity.getEmailId())).thenReturn(Optional.of(twitterEntity));
			assertEquals(twitterDAO.loadUserByUsername(twitterEntity.getEmailId()),password);
		}
		
//		@Test
//		public void EmailAlreadyExistsTest() throws Exception {
//			TwitterEntity twitterEntity = new TwitterEntity();
//			twitterEntity.setEmailId("nishi.90297@gmail.com");
//			twitterEntity.setUserName("Nishtha");
//			twitterEntity.setGender("female");
//			twitterEntity.setPassword("password");
//			
//			when(twitterRepository.findById(twitterEntity.getEmailId())).thenReturn(Optional.of(twitterEntity));
//			assertEquals(twitterDAO.createUser(twitterEntity),"Email Id Already Existed.");
//		}

}
