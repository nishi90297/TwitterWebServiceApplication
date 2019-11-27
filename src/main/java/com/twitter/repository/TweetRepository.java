package com.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.entity.TweetEntity;

public interface TweetRepository extends JpaRepository<TweetEntity,Integer>{

}
