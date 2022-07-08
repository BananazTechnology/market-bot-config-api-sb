package com.aaronrenner.spring.controllers;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aaronrenner.spring.models.*;
import com.aaronrenner.spring.services.ListingsService;

@RestController
public class ListingsController {
	
	@Autowired
	ListingsService listingService;

	// Endpoints
	private final String CONTENT_TYPE = "application/json";
	private final String LISTING_PATH = "/listings";
	private final String WITH_LIST_ID = "/{listingsId}";

	@RequestMapping(
			method = RequestMethod.POST,
			value  = LISTING_PATH,
			consumes = CONTENT_TYPE,
			produces = CONTENT_TYPE)
	public ResponseEntity<Listings> createListings(HttpServletRequest request, @RequestBody Listings listing) throws Exception {
		return listingService.createListings(request, listing);
	}
	
	@GetMapping(path = LISTING_PATH, produces = CONTENT_TYPE)
	public ResponseEntity<?> readAllListings(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		return listingService.readAllListings(getPage, withCount, viewAll);
	}
	
	@GetMapping(path = LISTING_PATH+WITH_LIST_ID, produces = CONTENT_TYPE)
	public  ResponseEntity<Listings> readListings(@PathVariable long listingsId) {
		return listingService.readListings(listingsId);
	}
	
	@PatchMapping(path = LISTING_PATH+WITH_LIST_ID)
	public ResponseEntity<String> updateListings(@PathVariable long listingsId, @RequestBody Listings listing) {
		listing.setId(listingsId);
		return listingService.updateListings(listingsId, listing);
	}
	
	@DeleteMapping(path = LISTING_PATH+WITH_LIST_ID)
	public ResponseEntity<String>  deleteListings(@PathVariable long listingsId) {
		return listingService.deleteListings(listingsId);
	}
}
