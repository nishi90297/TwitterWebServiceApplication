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
public class followUserServiceTest {
		
		@Autowired
		@Mock
	    private TwitterDAOImpl twitterDAO;
	 

		@Autowired
		@InjectMocks
	    private TwitterServiceImpl twitterService;
		
		@Test
		public void followingUserSuccessfullTest() throws Exception {
			when(twitterDAO.followingUser("sunny@gmail.com")).thenReturn("sunny@gmail.com is set into your followers");
			assertEquals(twitterService.followingUser("sunny@gmail.com"),"sunny@gmail.com is set into your followers");
		}

}
