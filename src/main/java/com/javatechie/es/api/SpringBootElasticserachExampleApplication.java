package com.javatechie.es.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ase.service.UserService;
import com.javatechie.es.api.model.Article;
import com.javatechie.es.api.model.User;
import com.javatechie.es.api.repository.CustomerRepository;
import com.javatechie.es.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class SpringBootElasticserachExampleApplication {
	
	@Autowired
	UserRepository userRepository;
    
    @PostMapping("/saveUser")
	public String saveUser(@RequestBody User user) {
		
    	userRepository.save(user);
		
		return "User registered successfully";
	}

	@PostMapping("/user")
	public String loginUser(@RequestBody User user) {

		User userObj = userRepository.findByUsername(user.getUsername());
       System.out.println(userObj.getUsername());
		if (user != null && !userObj.getUsername().equalsIgnoreCase(user.getUsername())) {
			return "User not Found";
		} else if (!userObj.getPassword().equalsIgnoreCase(user.getPassword())) {
			return "Password is incorrect";
		}

		return "success";
	}
    
    @Autowired
	private CustomerRepository repository;
	
	@PostMapping("/saveArticle")
	public String saveCustomer(@RequestBody Article articles) {
		System.out.println(articles.getTitle());
		repository.save(articles);
		return "saved";
	}

	@GetMapping("/findAll")
	public Iterable<Article> findAllArticles() {
		return repository.findAll();
	}

	@GetMapping("/searchByName/{title}")
	public List<Article> findByFirstName(@PathVariable String title) {
		System.out.println("title=" + title);
		List<Article> articles = repository.findByTitle(title);

		System.out.println(articles);
		return articles;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootElasticserachExampleApplication.class, args);
	}
}
