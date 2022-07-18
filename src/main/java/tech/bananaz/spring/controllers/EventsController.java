package tech.bananaz.spring.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.bananaz.spring.services.ListingEventsService;
import tech.bananaz.spring.utils.EventSearchCriteria;
import tech.bananaz.spring.utils.EventSpecification;
import tech.bananaz.spring.utils.EventType;
import tech.bananaz.spring.utils.MarketPlace;
import tech.bananaz.spring.utils.Operation;

@RestController
@RequestMapping(value = "/events", produces = "application/json")
public class EventsController {
	
	@Autowired
	ListingEventsService eventsService;

	@GetMapping()
	public  ResponseEntity<?> readAllEvents(
									@RequestParam Optional<Integer>     page, 
									@RequestParam Optional<Integer>     limit,
									@RequestParam Optional<EventType>   eventType,
									@RequestParam Optional<MarketPlace> market,
									@RequestParam Optional<Boolean>     consumed,
									@RequestParam Optional<Integer>     quantity,
									@RequestParam Optional<Integer>     configId) {
		
		// Build default sorting and filtering options
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 50;
		Sort defaultDateSort = Sort.by(Direction.DESC, "createdDate");
		PageRequest pageOptions = PageRequest.of(getPage, withCount, defaultDateSort);
		
		// Filters
		EventSpecification eSpec = new EventSpecification();
		if(eventType.isPresent()) 
			eSpec.addSearchCriteria(new EventSearchCriteria("eventType", eventType.get(), Operation.EQUAL));
		if(market.isPresent()) 
			eSpec.addSearchCriteria(new EventSearchCriteria("market", market.get(), Operation.EQUAL));
		if(consumed.isPresent()) 
			eSpec.addSearchCriteria(new EventSearchCriteria("consumed", consumed.get(), Operation.EQUAL));
		if(quantity.isPresent()) 
			eSpec.addSearchCriteria(new EventSearchCriteria("quantity", quantity.get(), Operation.EQUAL));
		if(configId.isPresent()) 
			eSpec.addSearchCriteria(new EventSearchCriteria("configId", configId.get(), Operation.EQUAL));
		
		return ResponseEntity.ok(eventsService.readAllEvents(pageOptions, eSpec));
	}
	
}
