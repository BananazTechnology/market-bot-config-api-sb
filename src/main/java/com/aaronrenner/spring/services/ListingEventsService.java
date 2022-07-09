package com.aaronrenner.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aaronrenner.spring.repositories.ListingEventPagingRepository;
import com.aaronrenner.spring.repositories.ListingEventRepository;

@Service
public class ListingEventsService {
	
	// Assets for Listing Config
	@Autowired
	ListingEventRepository listEventsRepo;
	@Autowired
	ListingEventPagingRepository listEventsPagingRepo;
	
	@Transactional
	public ResponseEntity<?> readAllListingEvents(int page, int limit, Boolean showAll) {
		// If asking for the older way of showing all
		if(showAll) return ResponseEntity.ok(listEventsRepo.findAll());
		// Everything else paging
		Pageable where = PageRequest.of(page, limit);
		return ResponseEntity.ok(listEventsPagingRepo.findAll(where));
	}
	
}
