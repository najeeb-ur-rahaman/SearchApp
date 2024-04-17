package com.javatechie.es.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.connector.Response;

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
	 
	@GetMapping("/search/{keyword}")
	String getUrls(@PathVariable String keyword) throws IOException {
		// String URL="https://google.com/search" +"?q=" +keyword;
		String en="en";
		String key="139a42b86f32f4b9fd67a46b1cb319a94572b2c1d6f20b6d495ea1a41600b4e0";
		
		//String URL = "https://scholar.google.com/scholar?hl={en}&api_key={key}&q={keyword}";
		
		String URL = "https://serpapi.com/search.json?engine=google_scholar&api_key=139a42b86f32f4b9fd67a46b1cb319a94572b2c1d6f20b6d495ea1a41600b4e0"+"&q=" +keyword;
		
		com.javatechie.es.api.model.Response response= new com.javatechie.es.api.model.Response();
		
		final RestTemplate restTemplate = new RestTemplate();
		final String response2 = restTemplate.getForObject(URL, String.class);
		

		
	//	Map jsonJavaRootObject = new Gson().fromJson(response2, Map.class);
     //  Object array=jsonJavaRootObject.get("organic_results");
//       
     // JSONObject json = new JSONObject(response2);   
//       
//       // get locations array from the JSON Object and store it into JSONArray  
//       JSONArray jsonArray = json.getJSONArray("organic_results");  
//
//		
//       for (int i = 0; i < jsonArray.length(); i++) {  
//           
//           // store each object in JSONObject  
//           JSONObject explrObject = jsonArray.getJSONObject(i);  
//             
//           // get field value from JSONObject using get() method  
//           System.out.println(explrObject.get("title"));  
//       }      
//		List<com.javatechie.es.api.model.Response> responseData = new ArrayList();
//		try {
//			Set<String> urls = new HashSet();
//			Set<String> s = new HashSet();
//			Document document1 = Jsoup.connect(URL).get();
//			Document document = Jsoup.connect(URL).get();
//			String html = document.html();
//			Elements links = document.select("cite");
//			
////			System.out.println(document1.attr("title"));
////			
////			
////			System.out.println();
//			
//			String title=null;
//			for (Element l : links) {
//				String text = l.text();
//				title=  l.attr("title");
//				String data=l.tagName();
//				if (text.contains(",")) {
//					text = text.replaceAll(" , ", "/");
//
//				}
//				 //System.out.println("title= "+title);
//					com.javatechie.es.api.model.Response response1 = new com.javatechie.es.api.model.Response();
//				urls.add(text);
//				
//
//				urls.forEach(e -> {
//					if (e.startsWith("https")) {
//						int index = e.indexOf(".com");
//						String text1 = e.substring(0, index + 4);
//						//System.out.println(text1);
//						
//						if(text1.contains(".com")) {
//							String title1=text1.substring(8, index+4);
//							if(!s.contains(text1)){
//							response1.setUrl(text1);							
//							response1.setTitle(title1);
//							responseData.add(response1);
//							}
//						s.add(text1);
//						
//						}
//					}
//				});
//				urls.forEach(e -> {
//					if (e.startsWith("https")) {
//						int index = e.indexOf(".dev");
//						String text1 = e.substring(0, index + 4);
//						//System.out.println(text1);
//						if(text1.contains("dev"))
//						s.add(text1);
//					}
//				});
//				urls.forEach(e -> {
//					if (e.startsWith("https")) {
//						int index = e.indexOf(".org");
//						String text1 = e.substring(0, index + 4);
//						//System.out.println(text1);
//						if(text1.contains(".org"))
//						s.add(text1);
//					}
//				});
//			}
//			response.setResults(s);
//			response.setInput(keyword);
//			
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
		return response2;

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
