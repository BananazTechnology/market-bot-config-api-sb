package tech.bananaz.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bananaz.spring.repositories.ListingEventPagingRepository;
import tech.bananaz.spring.repositories.ListingEventRepository;

@Service
public class ListingEventsService {
	
	// Assets for Listing Config
	@Autowired
	ListingEventRepository listEventsRepo;
	@Autowired
	ListingEventPagingRepository listEventsPagingRepo;
	
	@Transactional
	public Object readAllListingEvents(int page, int limit, Boolean showAll) {
		// If asking for the older way of showing all
		if(showAll) return listEventsRepo.findAllOrderByCreatedDateDesc();
		// Everything else paging
		Pageable where = PageRequest.of(page, limit).withSort(Direction.DESC, "createdDate");
		return listEventsPagingRepo.findAll(where);
	}
	
}
