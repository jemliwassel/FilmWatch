package com.openclassrooms.watchlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.validation.Valid;

@Controller
public class WatchlistController {
	private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();
	private static int index = 1;
	
	@GetMapping("/watchlistItemForm")
	public ModelAndView showWatchlistItemForm(@RequestParam(required = false) Integer id) {
		
		String viewName = "watchlistItemForm";
		Map<String,Object> model = new HashMap<String,Object>();
		
		WatchlistItem watchlistItem = findWatchlistItemById(id);
		if (watchlistItem == null) {
			model.put("watchlistItem", new WatchlistItem());
		} else {
			model.put("watchlistItem", watchlistItem);
		}
		return new ModelAndView(viewName,model);
	}
	
	private WatchlistItem findWatchlistItemById(Integer id) {
		for (WatchlistItem watchlistItem : watchlistItems) {
			if (watchlistItem.getId().equals(id)) {
				return watchlistItem;
			}
		}
		return null;
	}

	@PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchlistItemForm(@Valid WatchlistItem watchlistItem,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return new ModelAndView("watchlistItemForm"); 
		}
		WatchlistItem existingItem = findWatchlistItemById(watchlistItem.getId());
		
		//Verify either the watchlistItem exists or not before updating it
		if (existingItem == null) {
			if (itemAlreadyExists(watchlistItem.getTitle())) {
				bindingResult.rejectValue("title", "", "This title already exists on your watchlist");
				return new ModelAndView("watchlistItemForm"); 
			}
			watchlistItem.setId(index++);
			watchlistItems.add(watchlistItem);
		} else {
			existingItem.setComment(watchlistItem.getComment());
			existingItem.setPriority(watchlistItem.getPriority());
			existingItem.setRating(watchlistItem.getRating());
			existingItem.setTitle(watchlistItem.getTitle());
		}
		
		
		RedirectView redirect = new RedirectView();
		redirect.setUrl("/watchlist");
		
		return new ModelAndView(redirect);
	}
	
	@GetMapping("/watchlist")
	public ModelAndView getWatchList() {
		
		String viewName = "watchlist";
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("watchlistItems", watchlistItems);
		model.put("numberOfMovies", watchlistItems.size());
		
		return new ModelAndView(viewName,model);
	}
	
	private Boolean itemAlreadyExists(String title) {
		for(WatchlistItem watchlistItem: watchlistItems) {
			if (watchlistItem.getTitle().equals(title)) {
				return true;
			}
		}
		return false;
	}
}
