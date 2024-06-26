package com.openclassrooms.watchlist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.watchlist.controller.WatchlistController;

@Service
@ConditionalOnProperty(name="app.environment", havingValue="prod")
public class MovieRatingServiceImpl implements MovieRatingService {
	
	String apiUrl = "https://www.omdbapi.com/?apikey=35a4df96&%20&t="; //Add your OMDB Api Key
	
	private final Logger logger = LoggerFactory.getLogger(MovieRatingServiceImpl.class);

	
	@Override
	public String getMovieRating(String title) {
		
		try {
			RestTemplate template = new RestTemplate();
			
			logger.info("OMDB API called with URL: {}", apiUrl + title);
			
			ResponseEntity<ObjectNode> response = 
					template.getForEntity(apiUrl + title, ObjectNode.class);
			ObjectNode jsonObject = response.getBody();
			
			logger.debug("OMDB API response: {}", jsonObject);
			
			return jsonObject.path("imdbRating").asText();
		} catch (Exception e) {
			logger.warn("Something went wrong while calling OMDB API " + e.getMessage());
			return null;
		}
	}
}
