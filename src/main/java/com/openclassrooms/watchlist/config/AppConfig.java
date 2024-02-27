package com.openclassrooms.watchlist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.openclassrooms.watchlist.repository.WatchlistRepository;
import com.openclassrooms.watchlist.service.MovieRatingService;
import com.openclassrooms.watchlist.service.WatchlistService;

@Configuration
public class AppConfig {
	
	@Bean
	public WatchlistRepository watchlistRepository() {
		return new WatchlistRepository();
	};
	
	@Bean
	public MovieRatingService movieRatingService() {
		return new MovieRatingService();
	}
	
	@Bean
	public WatchlistService watchlistService() {
		return new WatchlistService(watchlistRepository(), movieRatingService());
	}
}
