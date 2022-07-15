package tech.bananaz.spring.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.bananaz.spring.services.ListingEventsService;

@RestController
@RequestMapping(value = "/events", produces = "application/json")
public class EventsController {
	
	@Autowired
	ListingEventsService eventsService;
	
	@Value("${info.version:unknown}")
	private String appVersion;
	@Value("${info.name:unknown}")
	private String appName;
	private static final String SERVICE_HEADER = "X-SERVICE"; 
	private static final String SERVICE_VALUE_FORMAT = "%s/%s";

	@GetMapping("/listings")
	public  ResponseEntity<?> readAllListingEvents(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		
		return ResponseEntity
					.ok()
					.header(SERVICE_HEADER, String.format(SERVICE_VALUE_FORMAT, appName, appVersion))
					.body(eventsService.readAllListingEvents(getPage, withCount, viewAll));
	}
	
}
