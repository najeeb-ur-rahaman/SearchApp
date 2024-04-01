package com.javatechie.es.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javatechie.es.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
  User 	findByUsername(String username);

}
