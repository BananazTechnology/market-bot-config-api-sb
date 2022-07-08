package com.aaronrenner.spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.aaronrenner.spring.models.Listings;

public interface ListingsConfigPagingRepository extends PagingAndSortingRepository<Listings, Long> {
	
}
