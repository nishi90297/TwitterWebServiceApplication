package com.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.entity.TwitterEntity;

public interface TwitterRepository extends JpaRepository<TwitterEntity, String>{

}
