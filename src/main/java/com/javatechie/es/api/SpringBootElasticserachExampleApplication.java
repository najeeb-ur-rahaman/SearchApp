package com.javatechie.es.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
		if(userObj==null) {
			return "User not Found";
		}
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
	
	 public static void getPageLinks(String URL, int depth) {
		 final int MAX_DEPTH = 2;
		 HashSet<String> links=new HashSet<String>();
	        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
	            System.out.println(">> Depth: " + depth + " [" + URL + "]");
	            try {
	                links.add(URL);

	                Document document = Jsoup.connect(URL).get();
	                Elements linksOnPage = document.select("a[href]");

	                depth++;
	                for (Element page : linksOnPage) {
	                    getPageLinks(page.attr("abs:href"), depth);
	                }
	            } catch (IOException e) {
	                System.err.println("For '" + URL + "': " + e.getMessage());
	            }
	        }
    }
	 
	 @GetMapping("/search/{keyword}")
	 Set<String> getUrls(@PathVariable String keyword){
		  String URL="https://google.com/search" +"?q=" +keyword;
		  Set<String> urls=new HashSet();
		  Set<String> s=new HashSet();
		 try {
			Document document = Jsoup.connect(URL).get();
			 String html=   document.html();
	           Elements links=  document.select("cite");
	           
	           for(Element l:links) {
	        	   String text=l.text();
	        	   if(text.contains(",")) {
	        		   text=text.replaceAll(" , ", "/");
	        		
	        	   }
	        	   //System.out.println(text);
	        	   urls.add(text);
	        	  
	       		urls.forEach(e-> {
	       			if(e.startsWith("https")) {
	       			int index=	e.indexOf("com");
	       			String text1= e.substring(0,index+3);
	       			System.out.println(text1);
	       			s.add(text1);
	       			}
	       		});
	           }
	           
	          
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
		 
	 }

	public static void main(String[] args) {
		SpringApplication.run(SpringBootElasticserachExampleApplication.class, args);
		
		  
		  
		 // getPageLinks("https://google.com/search?q='angular'",0);

		   
	}
}
