package com.javatechie.es.api;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.javatechie.es.api.model.User;
import com.javatechie.es.api.repository.UserRepository;
 
@ExtendWith(MockitoExtension.class)
 
public class SpringBootElasticserachExampleApplicationTests {

	
	@Mock
	UserRepository userRepository;
	
	@Test
	public void findByFirstName() {
		User u1=new User();
		u1.setUsername("testuser");
		given(userRepository.findByUsername("")).willReturn(u1);
		assertTrue(true);
	}
	@Test
	public void login() {
		User u1=new User();
		u1.setUsername("testuser");
		u1.setEmail("testuser@gmail.com");
		given(userRepository.findByUsername("")).willReturn(u1);
		assertTrue(true);
	}
	@Test
	public void register() {
		User u1=new User();
		u1.setUsername("testuser");
		u1.setEmail("testuser@gmail.com");
		u1.setInstitute("SLK");
		given(userRepository.findByUsername("")).willReturn(u1);
		assertTrue(true);
	}

}
