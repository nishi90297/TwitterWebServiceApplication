package com.twitter.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tuser")
@JsonIgnoreProperties(value= {"followers","following"})
public class TwitterEntity {

	@Id
	private String emailId;
	private String userName;
	private String password;
	private String gender;
	
	//whom you follow
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="tfollower",joinColumns=@JoinColumn(name="followingId"),inverseJoinColumns=@JoinColumn(name="followerId"))
	private List<TwitterEntity> followers;
	
	//who following you
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="tfollower",joinColumns=@JoinColumn(name="followerId"),inverseJoinColumns=@JoinColumn(name="followingId"))
	private List<TwitterEntity> following;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="UserTweets",joinColumns=@JoinColumn(name="UserId"),inverseJoinColumns=@JoinColumn(name="TweetId"))
	private List<TweetEntity> tweetsList;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<TwitterEntity> getFollowers() {
		return followers;
	}
	public void setFollowers(List<TwitterEntity> followers) {
		this.followers = followers;
	}
	public List<TwitterEntity> getFollowing() {
		return following;
	}
	public void setFollowing(List<TwitterEntity> following) {
		this.following = following;
	}
	public List<TweetEntity> getTweetsList() {
		return tweetsList;
	}
	public void setTweetsList(List<TweetEntity> tweetsList) {
		this.tweetsList = tweetsList;
	}
	
}
