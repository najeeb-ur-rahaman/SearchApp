package com.javatechie.es.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.connector.Response;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.web.client.RestTemplate;


import com.javatechie.es.api.model.Article;
import com.javatechie.es.api.model.SearchRequest;
import com.javatechie.es.api.model.User;
import com.javatechie.es.api.repository.CustomerRepository;
import com.javatechie.es.api.repository.UserRepository;

import jakarta.json.JsonObject;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class SpringBootElasticserachExampleApplication {
	
	@Autowired
	UserRepository userRepository;
	
//	@Autowired
//	RestTemplate template;
    
	@PostMapping("/saveUser")
	public String saveUser(@RequestBody User user) {
		String message = "";
		try {
			userRepository.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			message = e.getMessage();
			if (message.contains("already exist")) {
				message = user.getUsername() + " " + "already exist";
				return message;
			}
		}

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
	 
	@PostMapping("/search")
	List<com.javatechie.es.api.model.Response> getUrls(@RequestBody SearchRequest request) throws IOException {
		 String URL2="https://google.com/search" +"?q=" +"react";
		String en="en";
		String key="139a42b86f32f4b9fd67a46b1cb319a94572b2c1d6f20b6d495ea1a41600b4e0";
		
		//String URL = "https://scholar.google.com/scholar?hl={en}&api_key={key}&q={keyword}";
		String date="2024";
		String URL=null;
		if(request.getDate1()!=null && !request.getDate1().isEmpty()) {
			System.out.println(" date1 =================================="+request.getDate1());
		
			URL = "https://serpapi.com/search.json?h1=en&engine=google_scholar&api_key=139a42b86f32f4b9fd67a46b1cb319a94572b2c1d6f20b6d495ea1a41600b4e0"+"&q=" +request.getName() + "&as_ylo="+request.getDate1();
				//+"&as_sdt=";
		}else {
			 URL = "https://serpapi.com/search.json?engine=google_scholar&api_key=139a42b86f32f4b9fd67a46b1cb319a94572b2c1d6f20b6d495ea1a41600b4e0"+"&q=" +request.getName();
		}
		
		com.javatechie.es.api.model.Response response= new com.javatechie.es.api.model.Response();
		
		String json1 = Jsoup.connect(URL).ignoreContentType(true).execute().body();
		
		
		
		
		
		
		
		final RestTemplate restTemplate = new RestTemplate();
		final String response2 = restTemplate.getForObject(URL, String.class);
		
		JSONObject sJson = new JSONObject(json1); 
		
		
		

       JSONArray jsonArray = sJson.getJSONArray("organic_results"); 
      // JSONArray jsonArray2 = gJson.getJSONArray("organic_results"); 
//
//		
       
       System.out.println("json1="+json1);
       List<com.javatechie.es.api.model.Response> responseData = new ArrayList();
       for (int i = 0; i < jsonArray.length(); i++) {  
          
//           // store each object in JSONObject  
           JSONObject explrObject = jsonArray.getJSONObject(i); 
           System.out.println("type="+request.getType());
//             
           // get field value from JSONObject using get() method  
       	com.javatechie.es.api.model.Response response1= new com.javatechie.es.api.model.Response();
       	if("B".equalsIgnoreCase(request.getType()) && explrObject.has("type")&& "book".equalsIgnoreCase((String)explrObject.get("type"))) {
       	response1.setTitle((String)explrObject.get("title"));
       	response1.setUrl((String)explrObject.get("link"));
       	response1.setSnippet((String)explrObject.get("snippet"));
       	response1.setType((String)explrObject.get("type"));
       	responseData.add(response1);
       	}else if("A".equalsIgnoreCase(request.getType())){
       		response1.setTitle((String)explrObject.get("title"));
           	response1.setUrl((String)explrObject.get("link"));
           	response1.setSnippet((String)explrObject.get("snippet"));
           	if((String)explrObject.get("type")!=null) {
           	response1.setType((String)explrObject.get("type"));
           	}else
           		response1.setType("");
           	responseData.add(response1);
       	}
   
		
		 
       }   
		if (request.getDate1() == null && request.getDate1().isEmpty()) {

			String URL3 = "https://serpapi.com/search.json?engine=google&api_key=139a42b86f32f4b9fd67a46b1cb319a94572b2c1d6f20b6d495ea1a41600b4e0"
					+ "&q=" + request.getName();
			String json2 = Jsoup.connect(URL3).ignoreContentType(true).execute().body();
			JSONObject gJson = new JSONObject(json2);
			JSONArray jsonArray2 = gJson.getJSONArray("organic_results"); 
			for (int i = 0; i < jsonArray2.length(); i++) {

//         // store each object in JSONObject  
				if (i < 5) {
					JSONObject explrObject = jsonArray2.getJSONObject(i);
//           
					// get field value from JSONObject using get() method
					com.javatechie.es.api.model.Response response1 = new com.javatechie.es.api.model.Response();
					response1.setTitle((String) explrObject.get("title"));
					response1.setUrl((String) explrObject.get("link"));
					response1.setSnippet((String) explrObject.get("snippet"));
//				if(explrObject.get("favicon")!=null) {
//				response1.setFavicon((String) explrObject.get("favicon"));
//				}
					responseData.add(response1);

					System.out.println("jsonArray2=" + response1.getTitle());

				}

			}
		}

		return responseData;

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootElasticserachExampleApplication.class, args);
		
		  
		  
		 // getPageLinks("https://google.com/search?q='angular'",0);
		
//		 final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//
//	        // Initialize the Scholar service
//	        Scholar scholarService = new Scholar.Builder(HTTP_TRANSPORT)
//	            .setApplicationName("YourApplicationName")
//	            .setGoogleClientRequestInitializer(new ScholarRequestInitializer("YOUR_API_KEY"))
//	            .build();
//
//	        // Perform a search
//	        Scholar.ScholarlyArticleList articlesList = scholarService.articles()
//	            .list("your search query")
//	            .setMaxResults(10) // Adjust as needed
//	            .execute();
//
//	        // Print the titles of the search results
//	        List<ScholarlyArticle> articles = articlesList.getResults();
//	        for (ScholarlyArticle article : articles) {
//	            System.out.println(article.getTitle());
//	        }

		   
	}
}
