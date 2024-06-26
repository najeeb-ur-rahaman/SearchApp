package com.javatechie.es.api.model;

import java.util.Set;

public class Response {
	
	private String input;
	
	private String title;
	
	private String url;
	
	private String 	snippet;
	
	private String summary;
	
	private String favicon;
	
	private String type;
	
	private Set<String> results;
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public Set<String> getResults() {
		return results;
	}
	public void setResults(Set<String> results) {
		this.results = results;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getFavicon() {
		return favicon;
	}
	public void setFavicon(String favicon) {
		this.favicon = favicon;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
	
	
	

}
