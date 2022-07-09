package com.aaronrenner.spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.aaronrenner.spring.models.ListingEvent;

@Repository
public interface ListingEventPagingRepository extends PagingAndSortingRepository<ListingEvent, Long> {}
