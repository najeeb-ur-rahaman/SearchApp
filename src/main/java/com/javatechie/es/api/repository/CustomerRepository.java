package com.javatechie.es.api.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.javatechie.es.api.model.Article;


public interface CustomerRepository extends ElasticsearchRepository<Article, String>{

	List<Article> findByTitle(String title);

}
