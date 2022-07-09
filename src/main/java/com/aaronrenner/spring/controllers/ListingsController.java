package com.aaronrenner.spring.controllers;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aaronrenner.spring.models.*;
import com.aaronrenner.spring.services.ListingsService;

@RestController
@RequestMapping(value = "/listings", produces = "application/json")
public class ListingsController {
	
	@Autowired
	ListingsService listingService;
	
	private static final String WITH_LIST_ID = "/{listingsId}";

	@PostMapping
	public ResponseEntity<Listings> createListings(HttpServletRequest request, @RequestBody Listings listing) throws Exception {
		return listingService.createListings(request, listing);
	}
	
	@GetMapping
	public ResponseEntity<?> readAllListings(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		return listingService.readAllListings(getPage, withCount, viewAll);
	}
	
	@GetMapping(WITH_LIST_ID)
	public  ResponseEntity<Listings> readListings(@PathVariable long listingsId) {
		return listingService.readListings(listingsId);
	}
	
	@PatchMapping(WITH_LIST_ID)
	public ResponseEntity<String> updateListings(@PathVariable long listingsId, @RequestBody Listings listing) {
		listing.setId(listingsId);
		return listingService.updateListings(listingsId, listing);
	}
	
	@DeleteMapping(WITH_LIST_ID)
	public ResponseEntity<String>  deleteListings(@PathVariable long listingsId) {
		return listingService.deleteListings(listingsId);
	}
}
