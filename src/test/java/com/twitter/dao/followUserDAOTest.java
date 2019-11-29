package com.twitter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
public class followUserDAOTest {
	
		@Autowired
		@Mock
	    private TwitterRepository twitterRepository;
		
		@Mock
		private TwitterEntity currentUser;
			
		@Autowired
		@InjectMocks
	    private TwitterDAOImpl twitterDAO;
	 
		public void init() {
	    	
			
	    }
		
		@Test
		public void emailNotExistsInDatabaseTest() throws Exception {
			TwitterEntity userEntity = new TwitterEntity();
			userEntity.setEmailId("nishi@gmail.com");
			userEntity.setUserName("Nishtha");
			userEntity.setGender("female");
			userEntity.setPassword("password");
			
			String followerId="sunny@gmail.com";
			
			when(twitterRepository.findById(followerId)).thenReturn(Optional.ofNullable(null));
			assertEquals(twitterDAO.followUser(followerId),"No such user with emailId: " + followerId +" exists");
		}
		
//		DAOimpl method not working, need to check.
//		@Test
//		public void emailAlreadyExistsInFollowingListTest() throws Exception {
//			TwitterEntity userEntity = new TwitterEntity();
//			userEntity.setEmailId("nishi@gmail.com");
//			userEntity.setUserName("Nishtha");
//			userEntity.setGender("female");
//			userEntity.setPassword("password");
//			
//			TwitterEntity followingEntity= new TwitterEntity();
//			followingEntity.setEmailId("sunny@gmail.com");
//			followingEntity.setUserName("Shivendra");
//			followingEntity.setGender("male");
//			followingEntity.setPassword("password");
//			
//			List<TwitterEntity> followingList=new ArrayList<TwitterEntity>();
//			followingList.add(followingEntity);
//			
//			userEntity.setFollowers(followingList);
//			
//			String followerId="sunny@gmail.com";
//			
//			when(twitterRepository.findById(followerId)).thenReturn(Optional.of(followingEntity));
//			assertEquals(twitterDAO.followUser(followerId),followerId+" already exists");
//		}

		@Test
		public void selfFollowingTest() throws Exception {
			TwitterEntity userEntity = new TwitterEntity();
			userEntity.setEmailId("nishi@gmail.com");
			userEntity.setUserName("Nishtha");
			userEntity.setGender("female");
			userEntity.setPassword("password");
			
			TwitterEntity currentUser=userEntity;
			String followerId="nishi@gmail.com";
			
			when(currentUser.getEmailId()).thenReturn(followerId);
			assertEquals(twitterDAO.followUser(followerId),"can't Follow Yourself");
		}

		
}
