package com.openclassrooms.watchlist.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;


public class MovieRatingService {
	
	String apiUrl = "https://www.omdbapi.com/?apikey="; //Add your OMDB Api Key
	
	public String getMovieRating(String title) {
		
		try {
			RestTemplate template = new RestTemplate();
			ResponseEntity<ObjectNode> response = 
					template.getForEntity(apiUrl + title, ObjectNode.class);
			ObjectNode jsonObject = response.getBody();
			return jsonObject.path("imdbRating").asText();
		} catch (Exception e) {
			System.out.println("Something went wrong while calling OMDB API " + e.getMessage());
			return null;
		}
	}
}
