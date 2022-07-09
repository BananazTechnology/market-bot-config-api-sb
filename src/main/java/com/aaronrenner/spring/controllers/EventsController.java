package com.aaronrenner.spring.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aaronrenner.spring.services.ListingEventsService;

@RestController
@RequestMapping(value = "/events", produces = "application/json")
public class EventsController {
	
	@Autowired
	ListingEventsService eventsService;

	@GetMapping("/listings")
	public  ResponseEntity<?> readAllListingEvents(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		return eventsService.readAllListingEvents(getPage, withCount, viewAll);
	}
	
}