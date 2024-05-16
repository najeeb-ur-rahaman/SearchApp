package com.javatechie.es.api;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
 

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
		
		given(userRepository.findByUsername("")).willReturn(new User());
	}
	@Test
	public void login() {
		
		given(userRepository.findByUsername("")).willReturn(new User());
	}

}
