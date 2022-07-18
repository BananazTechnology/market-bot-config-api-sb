package tech.bananaz.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bananaz.spring.models.Event;
import tech.bananaz.spring.repositories.EventPagingRepository;

@Service
public class ListingEventsService {
	
	// Assets for Listing Config
	@Autowired
	EventPagingRepository listEventsPagingRepo;
	
	@Transactional
	public Page<Event> readAllEvents(PageRequest pageOptions, Specification<Event> eSpec) {
		return listEventsPagingRepo.findAll(eSpec, pageOptions);
	}
	
}
