package com.openclassrooms.watchlist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.watchlist.repository.WatchlistRepository;
import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;

public class WatchlistService {
	
	WatchlistRepository watchlistRepository;
	MovieRatingService movieRatingService;
	
	
	public WatchlistService(WatchlistRepository watchlistRepository, MovieRatingService movieRatingService) {
		super();
		this.watchlistRepository = watchlistRepository;
		this.movieRatingService = movieRatingService;
	}

	public List<WatchlistItem> getWatchlistItems(){
		List<WatchlistItem> watchlistItems = watchlistRepository.getList();
		for (WatchlistItem watchlistItem : watchlistItems) {
			String rating = movieRatingService.getMovieRating(watchlistItem.getTitle());
			if (rating != null) {
				watchlistItem.setRating(rating);
			}
		}
		return watchlistItems;
	}
	
	public int getWatchlistItemsSize() {
		return watchlistRepository.getList().size();
	}
	
	public WatchlistItem findWatchlistItemById(Integer id) {
		return watchlistRepository.findById(id);
	}
	
	public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException {
		WatchlistItem existingItem = findWatchlistItemById(watchlistItem.getId());
		
		//Verify either the watchlistItem exists or not before updating it
		if (existingItem == null) {
			if (watchlistRepository.findByTitle(watchlistItem.getTitle()) != null) {
				throw new DuplicateTitleException();
			}
			watchlistRepository.addItem(watchlistItem);
		} else {
			existingItem.setComment(watchlistItem.getComment());
			existingItem.setPriority(watchlistItem.getPriority());
			existingItem.setRating(watchlistItem.getRating());
			existingItem.setTitle(watchlistItem.getTitle());
		}
		
	}
	
	
}
