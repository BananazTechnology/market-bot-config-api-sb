package tech.bananaz.spring.controllers;

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
import tech.bananaz.models.Listing;
import tech.bananaz.spring.services.ListingsService;

@RestController
@RequestMapping(value = "/listings", produces = "application/json")
public class ListingsController {
	
	@Autowired
	ListingsService listingService;
	
	private static final String WITH_LIST_ID = "/{listingsId}";

	@PostMapping
	public ResponseEntity<?> createListings(HttpServletRequest request, @RequestBody Listing listing) throws Exception {
		// Process response
		return ResponseEntity
					.created(listingService.createListings(request, listing))
					.build();
	}
	
	@GetMapping
	public ResponseEntity<?> readAllListings(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		// Set defaults
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		// Process response
		return ResponseEntity.ok(listingService.readAllListings(getPage, withCount, viewAll));
	}
	
	@GetMapping(WITH_LIST_ID)
	public  ResponseEntity<Listing> readListings(@PathVariable long listingsId) {
		// Process response
		return ResponseEntity.ok(listingService.readListings(listingsId));
	}
	
	@PatchMapping(WITH_LIST_ID)
	public ResponseEntity<?> updateListings(@PathVariable long listingsId, @RequestBody Listing listing) throws Exception {
		// Save the ID in the body
		listing.setId(listingsId);
		// Update the entity
		listingService.updateListings(listingsId, listing);
		// Process response
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(WITH_LIST_ID)
	public ResponseEntity<?>  deleteListings(@PathVariable long listingsId) {
		// Process function
		listingService.deleteListings(listingsId);
		// Process response
		return ResponseEntity.noContent().build();
	}
}
