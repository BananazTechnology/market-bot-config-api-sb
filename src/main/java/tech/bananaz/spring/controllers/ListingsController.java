package tech.bananaz.spring.controllers;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import tech.bananaz.spring.models.*;
import tech.bananaz.spring.services.ListingsService;

@RestController
@RequestMapping(value = "/listings", produces = "application/json")
public class ListingsController {
	
	@Autowired
	ListingsService listingService;
	
	private static final String WITH_LIST_ID = "/{listingsId}";

	@Value("${info.version:unknown}")
	private String appVersion;
	@Value("${info.name:unknown}")
	private String appName;
	private static final String SERVICE_HEADER = "X-SERVICE";
	private static final String SERVICE_VALUE_FORMAT = "%s/%s";

	@PostMapping
	public ResponseEntity<?> createListings(HttpServletRequest request, @RequestBody Listings listing) throws Exception {
		// Process response
		return ResponseEntity
					.created(listingService.createListings(request, listing))
					.header(SERVICE_HEADER, String.format(SERVICE_VALUE_FORMAT, appName, appVersion))
					.build();
	}
	
	@GetMapping
	public ResponseEntity<?> readAllListings(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		// Set defaults
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		// Process response
		return ResponseEntity
					.ok()
					.header(SERVICE_HEADER, String.format(SERVICE_VALUE_FORMAT, appName, appVersion))
					.body(listingService.readAllListings(getPage, withCount, viewAll));
	}
	
	@GetMapping(WITH_LIST_ID)
	public  ResponseEntity<Listings> readListings(@PathVariable long listingsId) {
		// Process response
		return ResponseEntity
					.ok()
					.header(SERVICE_HEADER, String.format(SERVICE_VALUE_FORMAT, appName, appVersion))
					.body(listingService.readListings(listingsId));
	}
	
	@PatchMapping(WITH_LIST_ID)
	public ResponseEntity<?> updateListings(@PathVariable long listingsId, @RequestBody Listings listing) {
		// Save the ID in the body
		listing.setId(listingsId);
		// Update the entity
		listingService.updateListings(listingsId, listing);
		// Process response
		return ResponseEntity
					.noContent()
					.header(SERVICE_HEADER, String.format(SERVICE_VALUE_FORMAT, appName, appVersion))
					.build();
	}
	
	@DeleteMapping(WITH_LIST_ID)
	public ResponseEntity<?>  deleteListings(@PathVariable long listingsId) {
		// Process function
		listingService.deleteListings(listingsId);
		// Process response
		return ResponseEntity
				.noContent()
				.header(SERVICE_HEADER, String.format(SERVICE_VALUE_FORMAT, appName, appVersion))
				.build();
	}
}
