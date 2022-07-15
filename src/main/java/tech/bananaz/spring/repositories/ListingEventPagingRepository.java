package tech.bananaz.spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tech.bananaz.spring.models.ListingEvent;

@Repository
public interface ListingEventPagingRepository extends PagingAndSortingRepository<ListingEvent, Long> {}
